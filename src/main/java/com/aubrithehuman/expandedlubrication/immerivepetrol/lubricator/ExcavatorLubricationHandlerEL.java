package com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator;

import java.util.Iterator;
import java.util.function.Supplier;

import com.mojang.blaze3d.matrix.MatrixStack;

import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockTileEntity.MultiblockProcess;
import blusunrize.immersiveengineering.common.blocks.metal.BucketWheelTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.ExcavatorTileEntity;
import blusunrize.immersiveengineering.common.config.IEServerConfig;
import flaxbeard.immersivepetroleum.ImmersivePetroleum;
import flaxbeard.immersivepetroleum.client.model.IPModel;
import flaxbeard.immersivepetroleum.client.model.IPModels;
import flaxbeard.immersivepetroleum.client.model.ModelLubricantPipes;
import flaxbeard.immersivepetroleum.common.IPContent.Fluids;
import flaxbeard.immersivepetroleum.common.blocks.tileentities.AutoLubricatorTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.fluid.Fluid;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExcavatorLubricationHandlerEL extends TieredLubricationHandler<AutoLubricatorTileEntity, ExcavatorTileEntity, MultiblockRecipe>{
//	private static final float factor = AMIConfig.tier_1_factor.get();
	private static Vector3i size = new Vector3i(3, 6, 3);
	
	@Override
	public Vector3i getStructureDimensions(){
		return size; 
	}
	
	@Override
	public TileEntity isPlacedCorrectly(World world, AutoLubricatorTileEntity lubricator, Direction facing){
		BlockPos target = lubricator.getBlockPos().relative(facing);
		TileEntity te = world.getBlockEntity(target);
		
		if(te instanceof ExcavatorTileEntity){
			ExcavatorTileEntity master = ((ExcavatorTileEntity) te).master();
			
			if(master != null){
				Direction dir = master.getIsMirrored() ? master.getFacing().getClockWise() : master.getFacing().getCounterClockWise();
				if(dir == facing){
					return master;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public AutoLubricatorTileEntity getLubricatorTE(World world, ExcavatorTileEntity mbte) {
		if(!world.isClientSide){
			
			TileEntity lubricator = null;
			
			if(!mbte.isDummy()){
				BlockPos posLube = mbte.getBlockPos().relative(mbte.getFacing(), 4)
						.relative(mbte.getIsMirrored() ? mbte.getFacing().getCounterClockWise() : mbte.getFacing().getClockWise(), 2).below();
				lubricator = world.getBlockEntity(posLube);
				if(lubricator != null) {
					if(lubricator instanceof AutoLubricatorTileEntity) {
						return (AutoLubricatorTileEntity) lubricator;
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean process(Iterator<MultiblockProcess<MultiblockRecipe>> processIterator, MultiblockProcess<MultiblockRecipe> process, ExcavatorTileEntity mbte, int ticks, World world) {
		BlockPos wheelPos = mbte.getWheelCenterPos();
		TileEntity center = world.getBlockEntity(wheelPos);
		
		if(center instanceof BucketWheelTileEntity){
			BucketWheelTileEntity wheel = (BucketWheelTileEntity) center;
			if(!wheel.offsetToMaster.equals(BlockPos.ZERO)){
				// Just to make absolutely sure it's the master
				wheel = wheel.master();
			}
			
			if(!world.isClientSide && ticks % 4 == 0){
				int consumed = IEServerConfig.MACHINES.excavator_consumption.get();
				int extracted = mbte.energyStorage.extractEnergy(consumed, true);
				if(extracted >= consumed){
					mbte.energyStorage.extractEnergy(extracted, false);
					wheel.rotation += IEServerConfig.MACHINES.excavator_speed.get() / 4F;
				}
			}else{
				wheel.rotation += IEServerConfig.MACHINES.excavator_speed.get() / 4F;
			}
		}
		return true;
	}
	
	@Override
	public void lubricateWithFluid(World world, int ticks, Fluid fluid, ExcavatorTileEntity mbte) {
//		AMICore.LOGGER.info(factor);
		if(!world.isClientSide){	
//			Iterator<MultiblockProcess<MultiblockRecipe>> processIterator = mbte.processQueue.iterator();
//			MultiblockProcess<MultiblockRecipe> process = processIterator.next();
			tierProcess(fluid, null, null, mbte, ticks, world);
		}else{
//			mbte.animation_barrelRotation += 18f / 4f;
//			mbte.animation_barrelRotation %= 360f;
		}
		
	}
	@Override
	public void spawnLubricantParticles(World world, AutoLubricatorTileEntity lubricator, Direction facing, ExcavatorTileEntity mbte){
		Direction f = mbte.getIsMirrored() ? facing : facing.getOpposite();
		
		float location = world.random.nextFloat();
		
		boolean flip = f.getAxis() == Axis.Z ^ facing.getAxisDirection() == AxisDirection.POSITIVE ^ !mbte.getIsMirrored();
		float xO = 1.2F;
		float zO = -.5F;
		float yO = .5F;
		
		if(location > .5F){
			xO = 0.9F;
			yO = 0.8F;
			zO = 1.75F;
		}
		
		if(facing.getAxisDirection() == AxisDirection.NEGATIVE)
			xO = -xO + 1;
		if(!flip)
			zO = -zO + 1;
		
		float x = lubricator.getBlockPos().getX() + (f.getAxis() == Axis.X ? xO : zO);
		float y = lubricator.getBlockPos().getY() + yO;
		float z = lubricator.getBlockPos().getZ() + (f.getAxis() == Axis.X ? zO : xO);
		
		for(int i = 0;i < 3;i++){
			float r1 = (world.random.nextFloat() - .5F) * 2F;
			float r2 = (world.random.nextFloat() - .5F) * 2F;
			float r3 = world.random.nextFloat();
			BlockState n = Fluids.lubricant.block.defaultBlockState();
			world.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, n), x, y, z, r1 * 0.04F, r3 * 0.0125F, r2 * 0.025F);
		}
	}
	
	@Override
	public Tuple<BlockPos, Direction> getGhostBlockPosition(World world, ExcavatorTileEntity mbte){
		if(!mbte.isDummy()){
			BlockPos pos = mbte.getBlockPos()
					.relative(mbte.getFacing(), 4)
					.relative(mbte.getIsMirrored() ? mbte.getFacing().getCounterClockWise() : mbte.getFacing().getClockWise(), 2);
			Direction f = mbte.getIsMirrored() ? mbte.getFacing().getClockWise() : mbte.getFacing().getCounterClockWise();
			return new Tuple<BlockPos, Direction>(pos, f);
		}
		return null;
	}
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(ImmersivePetroleum.MODID, "textures/models/lube_pipe.png");
	private static Supplier<IPModel> pipes_normal;
	private static Supplier<IPModel> pipes_mirrored;
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void renderPipes(AutoLubricatorTileEntity lubricator, ExcavatorTileEntity mbte, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay){
//		matrix.translate(0, -1, 0);
//		Vector3i offset = mbte.getBlockPos().subtract(lubricator.getBlockPos());
//		matrix.translate(offset.getX(), offset.getY(), offset.getZ());
//		
//		Direction rotation = mbte.getFacing();
//		switch(rotation){
//			case NORTH:{
////				matrix.rotate(new Quaternion(0, 90F, 0, true));
//				matrix.translate(-1, 0, -1);
//				break;
//			}
//			case SOUTH:{
////				matrix.rotate(new Quaternion(0, 270F, 0, true));
//				matrix.translate(0, 0, -2);
//				break;
//			}
//			case EAST:{
//				matrix.translate(0, 0, -1);
//				break;
//			}
//			case WEST:{
////				matrix.rotate(new Quaternion(0, 180F, 0, true));
//				matrix.translate(-1, 0, -2);
//				break;
//			}
//			default:
//				break;
//		}
//		
//		IPModel model = null;
//		if(mbte.getIsMirrored()){
//			if(pipes_mirrored == null)
//				pipes_mirrored = IPModels.getSupplier(ModelLubricantPipes.Excavator.ID_MIRRORED);
//			
//			model = pipes_mirrored.get();
//		}else{
//			if(pipes_normal == null)
//				pipes_normal = IPModels.getSupplier(ModelLubricantPipes.Excavator.ID_NORMAL);
//			
//			model = pipes_normal.get();
//		}
//		
//		if(model != null){
//			model.renderToBuffer(matrix, buffer.getBuffer(model.renderType(TEXTURE)), combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
//		}
	}
	

	@Override
	public boolean isMachineEnabled(World world, ExcavatorTileEntity mbte){
		BlockPos wheelPos = mbte.getWheelCenterPos();
		TileEntity center = world.getBlockEntity(wheelPos);
		
		if(center instanceof BucketWheelTileEntity){
			BucketWheelTileEntity wheel = (BucketWheelTileEntity) center;
			if(!wheel.offsetToMaster.equals(BlockPos.ZERO)){
				// Just to make absolutely sure it's the master
				wheel = wheel.master();
			}
			
			return wheel.active;
		}
		return false;
	}

}