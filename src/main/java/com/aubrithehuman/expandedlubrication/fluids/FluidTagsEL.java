package com.aubrithehuman.expandedlubrication.fluids;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.aubrithehuman.expandedlubrication.ExpandedLubrication;
import com.aubrithehuman.expandedlubrication.config.ELConfig;

import flaxbeard.immersivepetroleum.api.crafting.LubricantHandler;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITag.INamedTag;

public class FluidTagsEL {

	public static final INamedTag<Fluid> LUBE_TIER_1 = FluidTags.bind("lubetier1");
	public static final INamedTag<Fluid> LUBE_TIER_2 = FluidTags.bind("lubetier2");
	public static final INamedTag<Fluid> LUBE_TIER_3 = FluidTags.bind("lubetier3");
	public static final INamedTag<Fluid> LUBE_TIER_4 = FluidTags.bind("lubetier4");
	
	
	public static void init() {
		ExpandedLubrication.LOGGER.info("Registering New lubes.");
		try {
			Field lubricants = LubricantHandler.class.getDeclaredField("lubricants");
			lubricants.setAccessible(true);
			Set<Pair<ITag<Fluid>, Integer>> newLubricants = new HashSet<>();
			lubricants.set(lubricants, newLubricants);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LubricantHandler.register(LUBE_TIER_1, ELConfig.tier_1_cons.get());
		LubricantHandler.register(LUBE_TIER_2, ELConfig.tier_2_cons.get());
		LubricantHandler.register(LUBE_TIER_3, ELConfig.tier_3_cons.get());
		LubricantHandler.register(LUBE_TIER_4, ELConfig.tier_4_cons.get());
		
		ExpandedLubrication.LOGGER.info("Registered New lubes.");
	}
}
