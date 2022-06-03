package com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator;

import java.util.Iterator;
import java.util.function.Supplier;

import com.mojang.blaze3d.matrix.MatrixStack;

import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockTileEntity.MultiblockProcess;
import flaxbeard.immersivepetroleum.ImmersivePetroleum;
import flaxbeard.immersivepetroleum.api.crafting.DistillationRecipe;
import flaxbeard.immersivepetroleum.client.model.IPModel;
import flaxbeard.immersivepetroleum.client.model.IPModels;
import flaxbeard.immersivepetroleum.client.model.ModelLubricantPipes;
import flaxbeard.immersivepetroleum.common.IPContent.Fluids;
import flaxbeard.immersivepetroleum.common.blocks.tileentities.AutoLubricatorTileEntity;
import flaxbeard.immersivepetroleum.common.blocks.tileentities.CokerUnitTileEntity.CokingState;
import flaxbeard.immersivepetroleum.common.blocks.tileentities.DistillationTowerTileEntity;
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

public class DistillationTowerLubricationHandler extends TieredLubricationHandler<AutoLubricatorTileEntity, DistillationTowerTileEntity, DistillationRecipe>{
//	private static final float factor = AMIConfig.tier_1_factor.get();
	private static Vector3i size = new Vector3i(4, 16, 4);
	
	@Override
	public Vector3i getStructureDimensions(){
		return size; 
	}
	
	@Override
	public TileEntity isPlacedCorrectly(World world, AutoLubricatorTileEntity lubricator, Direction facing){
		BlockPos target = lubricator.getBlockPos().relative(facing);
		TileEntity te = world.getBlockEntity(target);
//		System.out.println("placed correctly check");
		if(te instanceof DistillationTowerTileEntity){
			DistillationTowerTileEntity master = ((DistillationTowerTileEntity) te).master();
			
			if(master != null && master.getFacing().getCounterClockWise() == facing){
				return master;
			}
		}
		
		return null;
	}
	
	@Override
	public AutoLubricatorTileEntity getLubricatorTE(World world, DistillationTowerTileEntity mbte) {
		if(!world.isClientSide){
			
			TileEntity lubricator = null;
//			System.out.println(lubricator);
			if(!mbte.isDummy()){
				BlockPos posLube = mbte.getBlockPos().relative(mbte.getFacing().getClockWise(), 2).relative(Direction.UP, 1).below();
//				System.out.println(posLube);
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
	public void lubricateWithFluid(World world, int ticks, Fluid fluid, DistillationTowerTileEntity mbte) {
//		AMICore.LOGGER.info(factor);
		if(!world.isClientSide){	
//			Iterator<MultiblockProcess<DistillationRecipe>> processIterator = mbte.processQueue.iterator();
//			System.out.println("success");
//			if (!processIterator.hasNext()) return;
//			System.out.println("success2");
//			MultiblockProcess<DistillationRecipe> process = processIterator.next();
			if(fluid != null)
			tierProcess(fluid, null, null, mbte, ticks, world);
		}else{
//			mbte.animation_agitator += 18f / 4f;
//			mbte.animation_agitator %= 360f;
		}
		
	}
	
	@Override
	public boolean process(Iterator<MultiblockProcess<DistillationRecipe>> processIterator, MultiblockProcess<DistillationRecipe> process, DistillationTowerTileEntity mbte, int ticks, World world) {
		mbte.tick();
//		int consume = mbte.energyStorage.extractEnergy(process.energyPerTick, true);
//		System.out.println("attempt " + mbte.toString());
//		if(consume >= process.energyPerTick){
//			mbte.energyStorage.extractEnergy(process.energyPerTick, false);
//			
//			if(process.processTick < process.maxTicks) process.processTick++;
//			
//			if(process.processTick >= process.maxTicks && mbte.processQueue.size() > 1){
//				process = processIterator.next();
//				
//				if(process.processTick < process.maxTicks) process.processTick++;
//			}
//		}
		return true;
	}
		
	@Override
	public void spawnLubricantParticles(World world, AutoLubricatorTileEntity lubricator, Direction facing, DistillationTowerTileEntity mbte){
		Direction f = mbte.getIsMirrored() ? facing : facing.getOpposite();
		
		float location = world.random.nextFloat();
		
		boolean flip = f.getAxis() == Axis.Z ^ facing.getAxisDirection() == AxisDirection.POSITIVE ^ !mbte.getIsMirrored();
		float xO = 2.5F;
		float zO = -0.1F;
		float yO = 1.3F;
		
		if(location > .5F){
			xO = 1.0F;
			yO = 3.0F;
			zO = 1.65F;
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
	public Tuple<BlockPos, Direction> getGhostBlockPosition(World world, DistillationTowerTileEntity mbte){
//		System.out.println("checkghost");
		if(!mbte.isDummy()){
			mbte.getFacing();
			BlockPos pos = mbte.getBlockPos().relative(mbte.getFacing().getClockWise(), 2).relative(Direction.UP, 1);
			Direction f = mbte.getFacing().getCounterClockWise();
			return new Tuple<BlockPos, Direction>(pos, f);
		}
		return null;
	}
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(ImmersivePetroleum.MODID, "textures/models/lube_pipe.png");
	private static Supplier<IPModel> pipes;
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void renderPipes(AutoLubricatorTileEntity lubricator, DistillationTowerTileEntity mbte, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay){
//		matrix.translate(0, -1, 0);
//		Vector3i offset = mbte.getBlockPos().subtract(lubricator.getBlockPos());
//		matrix.translate(offset.getX(), offset.getY(), offset.getZ());
//		
//		Direction rotation = mbte.getFacing();
//		switch(rotation){
//			case NORTH:{
////				matrix.rotate(new Quaternion(0, 90F, 0, true));
//				matrix.translate(-1, 0, 0);
//				break;
//			}
//			case SOUTH:{
////				matrix.rotate(new Quaternion(0, 270F, 0, true));
//				matrix.translate(0, 0, -1);
//				break;
//			}
//			case EAST:{
//				matrix.translate(0, 0, 0);
//				break;
//			}
//			case WEST:{
////				matrix.rotate(new Quaternion(0, 180F, 0, true));
//				matrix.translate(-1, 0, -1);
//				break;
//			}
//			default:
//				break;
//		}
//		
//		if(pipes == null)
//			pipes = IPModels.getSupplier(ModelLubricantPipes.Crusher.ID);
//		
//		IPModel model;
//		if((model = pipes.get()) != null){
//			model.renderToBuffer(matrix, buffer.getBuffer(model.renderType(TEXTURE)), combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
//		}
	}
	
	@Override
	public boolean isMachineEnabled(World world, DistillationTowerTileEntity mbte){
		return mbte.shouldRenderAsActiveImpl();
	}

}