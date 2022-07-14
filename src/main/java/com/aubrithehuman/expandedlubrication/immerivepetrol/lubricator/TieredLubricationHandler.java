package com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator;

import java.util.Iterator;

import com.aubrithehuman.expandedlubrication.config.ELConfig;
import com.aubrithehuman.expandedlubrication.fluids.FluidTagsEL;

import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;
import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe;
import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockTileEntity;
import blusunrize.immersiveengineering.common.blocks.generic.PoweredMultiblockTileEntity.MultiblockProcess;
import blusunrize.immersiveengineering.common.blocks.metal.ArcFurnaceTileEntity;
import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler.ILubricationHandler;
import flaxbeard.immersivepetroleum.common.blocks.tileentities.AutoLubricatorTileEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fluids.FluidStack;

/**
 * T is the lubricator type (MUST PASS AutoLubricatorTileEntity
 * L is the IE multiblock type to handle
 * R is recipe for ticking
 * 
 * DOnt forget to register to LubricatedHandler.registerLubricatedTile()
 */
public abstract class TieredLubricationHandler<T extends TileEntity, L extends PoweredMultiblockTileEntity<L, R>, R extends MultiblockRecipe> implements ILubricationHandler<L>{

	@Override
	public void lubricate(World arg0, int arg1, L arg2) { }

	@Override
	public void lubricate(World arg0, Fluid arg1, int arg2, L arg3) { }
	
	@Override
	public void lubricateClient(ClientWorld arg0, Fluid arg1, int arg2, L arg3) {
		
	}

	@Override
	public void lubricateServer(ServerWorld world, Fluid lubricant, int ticks, L mbte) {
		Iterator<MultiblockProcess<R>> processIterator = mbte.processQueue.iterator();
		MultiblockProcess<R> process = null;
		if(processIterator.hasNext()) process = processIterator.next();
		
		tierProcess(lubricant, processIterator, process, mbte, ticks, world);		
	}
	
	public void tierProcess(Fluid fluid, Iterator<MultiblockProcess<R>> processIterator, MultiblockProcess<R> process, L mbte, int ticks, World world) {
		double times = 0.0D;
		if(fluid.is(FluidTagsEL.LUBE_TIER_1)) {
			times = ELConfig.tier_1_factor.get();
		} else if(fluid.is(FluidTagsEL.LUBE_TIER_2)) {
			times = ELConfig.tier_2_factor.get();
		} else if(fluid.is(FluidTagsEL.LUBE_TIER_3)) {
			times = ELConfig.tier_3_factor.get();
		} else if(fluid.is(FluidTagsEL.LUBE_TIER_4)) {
			times = ELConfig.tier_4_factor.get();
		}
		
//		System.out.println("doing " + times);
		
		int i;
		for (i = 0; i < Math.floor(times); i++) {
			process(processIterator, process, mbte, ticks, world);			
		}	
		
		if((times - (double) i) == 0) return;
		
		if (ticks % (1 / (times - (double) i)) == 0) {
			process(processIterator, process, mbte, ticks, world);	
		}
	}
	
	public boolean process(Iterator<MultiblockProcess<R>> processIterator, MultiblockProcess<R> process, L mbte, int ticks, World world) {
		int consume = mbte.energyStorage.extractEnergy(process.energyPerTick, true);
		if(consume >= process.energyPerTick){
			mbte.energyStorage.extractEnergy(process.energyPerTick, false);
			
			if(process.processTick < process.maxTicks) process.processTick++;
			
			if(process.processTick >= process.maxTicks && mbte.processQueue.size() > 1){
				process = processIterator.next();
				
				if(process.processTick < process.maxTicks) process.processTick++;
			}
		}
		return true;
	}
	
	@Override
	public boolean isMachineEnabled(World world, L mbte){
		return mbte.shouldRenderAsActive();
	}
	
}
