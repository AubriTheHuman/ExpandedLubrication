package com.aubrithehuman.expandedlubrication.config;


import com.aubrithehuman.expandedlubrication.ExpandedLubrication;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ELConfig {

	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;
	
	public static final ForgeConfigSpec.ConfigValue<Double> tier_1_factor; 
	public static final ForgeConfigSpec.ConfigValue<Double> tier_2_factor; 
	public static final ForgeConfigSpec.ConfigValue<Double> tier_3_factor; 
	public static final ForgeConfigSpec.ConfigValue<Double> tier_4_factor; 
	
	public static final ForgeConfigSpec.ConfigValue<Integer> tier_1_cons; 
	public static final ForgeConfigSpec.ConfigValue<Integer> tier_2_cons; 
	public static final ForgeConfigSpec.ConfigValue<Integer> tier_3_cons; 
	public static final ForgeConfigSpec.ConfigValue<Integer> tier_4_cons; 
	
	public static final ForgeConfigSpec.ConfigValue<Boolean> doArcFurnace; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doAutoEngineerWorkbench; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doCoker; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doCrusherReplace; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doDistillationTower; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doExcavatorReplace; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doFermenter; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doSulfurRecovery; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doMixer; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doPress; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doPumpjackReplace; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doRefinery; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doSqueezer; 

	
	static {
		BUILDER.push("Lubricant speed factors");
		
		tier_1_factor = BUILDER.comment("Number of extra ticks tier 1 lubricants do. Default: 0.25 (1.25x speed)").define("Tier 1 factor", 0.25D);
		tier_2_factor = BUILDER.comment("Number of extra ticks tier 2 lubricants do. Default: 0.5 (1.5x speed)").define("Tier 2 factor", 0.5D);
		tier_3_factor = BUILDER.comment("Number of extra ticks tier 3 lubricants do. Default: 1 (2x speed)").define("Tier 3 factor", 1.0D);
		tier_4_factor = BUILDER.comment("Number of extra ticks tier 4 lubricants do. Default: 3 (4x speed)").define("Tier 4 factor", 3.0D);
		BUILDER.pop();

		BUILDER.push("Lubricant consumption rates");
		tier_1_cons = BUILDER.comment("Consumption speed of tier 1 lubricants, calculated as n per every 4 ticks. Default: 1 (5/s)").define("Tier 1 Consumption", 1);
		tier_2_cons = BUILDER.comment("Consumption speed of tier 2 lubricants, calculated as n per every 4 ticks. Default: 2 (10/s)").define("Tier 2 Consumption", 2);
		tier_3_cons = BUILDER.comment("Consumption speed of tier 3 lubricants, calculated as n per every 4 ticks. Default: 3 (15/s)").define("Tier 3 Consumption", 3);
		tier_4_cons = BUILDER.comment("Consumption speed of tier 4 lubricants, calculated as n per every 4 ticks. Default: 4 (20/s)").define("Tier 4 Consumption", 4);
		BUILDER.pop();
		
		BUILDER.push("Machine Toggles");
		doArcFurnace = BUILDER.comment("Add functionality to Arc Furnace. Default: true").define("doArcFurnace", true);
		doAutoEngineerWorkbench = BUILDER.comment("Add functionality to Automated Engineers Workbench. Default: true").define("doAutoEngineerWorkbench", true);
		doCoker = BUILDER.comment("Add functionality to Coker. Default: true").define("doCoker", true);
		doCrusherReplace = BUILDER.comment("Add functionality to Crusher, replacing default behavior. Default: true").define("doCrusher", true);
		doDistillationTower = BUILDER.comment("Add functionality to Distillation Tower. Default: true").define("doDistillationTower", true);
		doExcavatorReplace = BUILDER.comment("Add functionality to Excavator, replacing default behavior. Default: true").define("doExcavatorReplace", true);
		doFermenter = BUILDER.comment("Add functionality to Fermenter. Default: true").define("doFermenter", true);
		doSulfurRecovery = BUILDER.comment("Add functionality to Sulfur Recovery Unit/Hyrdrotreater. Default: true").define("doSulfurRecovery", true);
		doMixer = BUILDER.comment("Add functionality to Mixer. Default: true").define("doMixer", true);
		doPress = BUILDER.comment("Add functionality to Metal Press. Default: true").define("doMetalPress", true);
		doPumpjackReplace = BUILDER.comment("Add functionality to Pumpjack, replacing default behavior. Default: true").define("doPumpjackReplace", true);
		doRefinery = BUILDER.comment("Add functionality to Refinery. Default: true").define("doRefinery", true);
		doSqueezer = BUILDER.comment("Add functionality to Squeezer. Default: true").define("doSqueezer", true);
		BUILDER.pop();
		
		SPEC = BUILDER.build();

		ExpandedLubrication.LOGGER.info("ExpandedLubrication Config Loaded.");
	}
	
}
