package com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ip;

import java.util.Iterator;
import java.util.function.Supplier;

import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.TieredLubricationHandler;
import com.mojang.blaze3d.matrix.MatrixStack;

import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;
import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockTileEntity.MultiblockProcess;
import blusunrize.immersiveengineering.common.blocks.metal.ArcFurnaceTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.BucketWheelTileEntity;
import flaxbeard.immersivepetroleum.ImmersivePetroleum;
import flaxbeard.immersivepetroleum.client.model.IPModel;
import flaxbeard.immersivepetroleum.client.model.IPModels;
import flaxbeard.immersivepetroleum.client.model.ModelLubricantPipes;
import flaxbeard.immersivepetroleum.common.blocks.tileentities.AutoLubricatorTileEntity;
import flaxbeard.immersivepetroleum.common.blocks.tileentities.PumpjackTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
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
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PumpjackLubricationHandlerEL extends TieredLubricationHandler<AutoLubricatorTileEntity, PumpjackTileEntity, MultiblockRecipe>{
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
		
		if(te instanceof PumpjackTileEntity){
			PumpjackTileEntity master = ((PumpjackTileEntity) te).master();
			if(master != null){
				Direction f = master.getIsMirrored() ? facing : facing.getOpposite();
				if(master.getFacing().getClockWise() == f){
					return master;
				}
			}
		}
		
		return null;
	}
	
	
	@Override
	public boolean process(Iterator<MultiblockProcess<MultiblockRecipe>> processIterator, MultiblockProcess<MultiblockRecipe> process, PumpjackTileEntity mbte, int ticks, World world) {
		if(!world.isClientSide){
			mbte.tick();
		}else{
			mbte.activeTicks += 1F / 4F;
		}
		return true;
	}
	
			
	@Override
	public void spawnLubricantParticles(ClientWorld world, AutoLubricatorTileEntity lubricator, Direction facing, PumpjackTileEntity mbte){
		Direction f = mbte.getIsMirrored() ? facing : facing.getOpposite();
		float location = world.random.nextFloat();
		
		boolean flip = f.getAxis() == Axis.Z ^ facing.getAxisDirection() == AxisDirection.POSITIVE ^ !mbte.getIsMirrored();
		float xO = 2.5F;
		float zO = -.15F;
		float yO = 2.25F;
		
		if(location > .5F){
			xO = 1.7F;
			yO = 2.9F;
			zO = -1.5F;
			
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
			//BlockState n = Fluids.lubricant.block.getDefaultState();
			//world.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, n), x, y, z, r1 * 0.04F, r3 * 0.0125F, r2 * 0.025F);
			
			// Because making your own particles is so convoluted and confusing
			world.addParticle(ParticleTypes.DRIPPING_HONEY, x, y, z, r1 * 0.04F, r3 * 0.0125F, r2 * 0.025F);
		}
	}
	
	@Override
	public Tuple<BlockPos, Direction> getGhostBlockPosition(World world, PumpjackTileEntity mbte){
		if(!mbte.isDummy()){
			Direction mbFacing = mbte.getFacing().getOpposite();
			BlockPos pos = mbte.getBlockPos()
					.relative(Direction.UP)
					.relative(mbFacing, 4)
					.relative(mbte.getIsMirrored() ? mbFacing.getClockWise() : mbFacing.getCounterClockWise(), 2);
			
			Direction f = (mbte.getIsMirrored() ? mbte.getFacing().getOpposite() : mbte.getFacing()).getCounterClockWise();
			return new Tuple<BlockPos, Direction>(pos, f);
		}
		return null;
	}
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(ImmersivePetroleum.MODID, "textures/models/lube_pipe.png");
	
	@OnlyIn(Dist.CLIENT)
	private static Supplier<IPModel> pipes_normal;
	
	@OnlyIn(Dist.CLIENT)
	private static Supplier<IPModel> pipes_mirrored;
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void renderPipes(AutoLubricatorTileEntity lubricator, PumpjackTileEntity mbte, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay){
//		matrix.translate(0, -1, 0);
//		Vector3i offset = mbte.getBlockPos().subtract(lubricator.getBlockPos());
//		matrix.translate(offset.getX(), offset.getY(), offset.getZ());
//		
//		Direction rotation = mbte.getFacing();
//		switch(rotation){
//			case NORTH:{
////				matrix.rotate(new Quaternion(0, 90F, 0, true));
//				matrix.translate(-6, 1, -1);
//				break;
//			}
//			case SOUTH:{
////				matrix.rotate(new Quaternion(0, 270F, 0, true));
//				matrix.translate(-5, 1, -2);
//				break;
//			}
//			case EAST:{
//				matrix.translate(-5, 1, -1);
//				break;
//			}
//			case WEST:{
////				matrix.rotate(new Quaternion(0, 180F, 0, true));
//				matrix.translate(-6, 1, -2);
//				break;
//			}
//			default:
//				break;
//		}
//		
//		IPModel model;
//		if(mbte.getIsMirrored()){
//			if(pipes_mirrored == null)
//				pipes_mirrored = IPModels.getSupplier(ModelLubricantPipes.Pumpjack.ID_MIRRORED);
//			
//			model = pipes_mirrored.get();
//		}else{
//			if(pipes_normal == null)
//				pipes_normal = IPModels.getSupplier(ModelLubricantPipes.Pumpjack.ID_NORMAL);
//			
//			model = pipes_normal.get();
//		}
//		
//		if(model != null){
//			model.renderToBuffer(matrix, buffer.getBuffer(model.renderType(TEXTURE)), combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
//		}
	}
	

	@Override
	public boolean isMachineEnabled(World world, PumpjackTileEntity mbte){
		 return mbte.wasActive;
	}

}