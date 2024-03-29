package com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ig;

import java.util.Iterator;
import java.util.function.Supplier;

import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.TieredLubricationHandler;
import com.mojang.blaze3d.matrix.MatrixStack;

import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockTileEntity.MultiblockProcess;
import flaxbeard.immersivepetroleum.ImmersivePetroleum;
import flaxbeard.immersivepetroleum.client.model.IPModel;
import flaxbeard.immersivepetroleum.common.IPContent.Fluids;
import flaxbeard.immersivepetroleum.common.blocks.tileentities.AutoLubricatorTileEntity;
import igteam.api.processing.recipe.SeparatorRecipe;
import igteam.immersive_geology.common.block.tileentity.GravitySeparatorTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.world.ClientWorld;
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

public class GravitySeparatorLubricationHandler extends TieredLubricationHandler<AutoLubricatorTileEntity, GravitySeparatorTileEntity, SeparatorRecipe>{
//	private static final float factor = AMIConfig.tier_1_factor.get();
	private static Vector3i size = new Vector3i(5, 5, 5);
	
	@Override
	public Vector3i getStructureDimensions(){
		return size; 
	}
	
	@Override
	public TileEntity isPlacedCorrectly(World world, AutoLubricatorTileEntity lubricator, Direction facing){
		BlockPos target = lubricator.getBlockPos().relative(facing);
		TileEntity te = world.getBlockEntity(target);
		
		if(te instanceof GravitySeparatorTileEntity){
			GravitySeparatorTileEntity master = ((GravitySeparatorTileEntity) te).master();
			
			if(master != null && master.getFacing().getCounterClockWise() == facing){
				return master;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean process(Iterator<MultiblockProcess<SeparatorRecipe>> processIterator, MultiblockProcess<SeparatorRecipe> process, GravitySeparatorTileEntity mbte, int ticks, World world) {
		mbte.tick();
//		int consume = mbte.energyStorage.extractEnergy(process.energyPerTick, true);
////		System.out.println("attempt");
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
	public void spawnLubricantParticles(ClientWorld world, AutoLubricatorTileEntity lubricator, Direction facing, GravitySeparatorTileEntity mbte){
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
	public Tuple<BlockPos, Direction> getGhostBlockPosition(World world, GravitySeparatorTileEntity mbte){
//		System.out.println("checkghost");
		if(!mbte.isDummy()){
			BlockPos pos = mbte.getBlockPos().relative(mbte.getFacing().getClockWise(), 2).relative(Direction.UP);
			Direction f = mbte.getFacing().getCounterClockWise();
			return new Tuple<BlockPos, Direction>(pos, f);
		}
		return null;
	}
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(ImmersivePetroleum.MODID, "textures/models/lube_pipe.png");
	private static Supplier<IPModel> pipes;
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void renderPipes(AutoLubricatorTileEntity lubricator, GravitySeparatorTileEntity mbte, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay){
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

	


	

}