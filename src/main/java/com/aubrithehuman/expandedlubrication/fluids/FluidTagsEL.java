package com.aubrithehuman.expandedlubrication.fluids;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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

	public static final INamedTag<Fluid> LUBE_TIER_1 = FluidTags.bind("tier_1_lubricant");
	public static final INamedTag<Fluid> LUBE_TIER_2 = FluidTags.bind("tier_2_lubricant");
	public static final INamedTag<Fluid> LUBE_TIER_3 = FluidTags.bind("tier_3_lubricant");
	public static final INamedTag<Fluid> LUBE_TIER_4 = FluidTags.bind("tier_4_lubricant");
	
	
	public static void init() {
		ExpandedLubrication.LOGGER.info("Loading fluid tags.");
		ExpandedLubrication.LOGGER.info("Registering New lubes.");
//		try {
//			Field lubricants = LubricantHandler.class.getDeclaredField("lubricants");
//			lubricants.setAccessible(true);
//			Set<Pair<ITag<Fluid>, Integer>> newLubricants = new HashSet<>();
//			lubricants.set(lubricants, newLubricants);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
		LubricantHandler.register(LUBE_TIER_1, ELConfig.tier_1_cons.get());
		LubricantHandler.register(LUBE_TIER_2, ELConfig.tier_2_cons.get());
		LubricantHandler.register(LUBE_TIER_3, ELConfig.tier_3_cons.get());
		LubricantHandler.register(LUBE_TIER_4, ELConfig.tier_4_cons.get());
		
		
	}
	
	public static void overwrite() {
		try {

//			System.out.println("Try Override!");
//			
//			Field lubricants = LubricantHandler.class.getDeclaredField("lubricants");
//			
//			Field modifiersField = Field.class.getDeclaredField("modifiers");
//		    modifiersField.setAccessible(true);
//		    modifiersField.setInt(lubricants, lubricants.getModifiers() & ~Modifier.FINAL);
//
//			Set<Pair<ITag<Fluid>, Integer>> newLubricants = new HashSet<>();
//			
//			
//			lubricants.set(null, newLubricants);
////			System.out.println("Success!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LubricantHandler.register(LUBE_TIER_1, ELConfig.tier_1_cons.get());
		LubricantHandler.register(LUBE_TIER_2, ELConfig.tier_2_cons.get());
		LubricantHandler.register(LUBE_TIER_3, ELConfig.tier_3_cons.get());
		LubricantHandler.register(LUBE_TIER_4, ELConfig.tier_4_cons.get());
		
	}
}
