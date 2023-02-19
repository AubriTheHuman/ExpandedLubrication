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
	
	//IE machines
	public static final ForgeConfigSpec.ConfigValue<Boolean> doArcFurnace; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doAutoEngineerWorkbench; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doCrusherReplace; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doExcavatorReplace; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doFermenter; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doMixer; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doPress; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doRefinery; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doSqueezer; 
	
	public static final ForgeConfigSpec.ConfigValue<Boolean> doCokeOven; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doCrudeBlastFurnace; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doBlastFurnace; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doAlloySmelter; 

	//IP machines
	public static final ForgeConfigSpec.ConfigValue<Boolean> doCoker; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doDistillationTower; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doPumpjackReplace; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doSulfurRecovery; 
	
	//IG machines
//	public static final ForgeConfigSpec.ConfigValue<Boolean> doReverberation; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doRotaryKilnIG; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doGravitySeparator; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doCrystallizer; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doChemicalVat; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doHydroCutter; 
	
	//II machines
	public static final ForgeConfigSpec.ConfigValue<Boolean> doCrucible; 
	public static final ForgeConfigSpec.ConfigValue<Boolean> doElectrolyzer;
	public static final ForgeConfigSpec.ConfigValue<Boolean> doCarKiln;  
	public static final ForgeConfigSpec.ConfigValue<Boolean> doRotaryKilnII; 
	
	
	static {
		BUILDER.push("Lubricant speed factors");
		
		tier_1_factor = BUILDER.comment("Number of extra ticks tier 1 lubricants do. Must be > 0. Default: 0.25 (1.25x speed)").define("Tier 1 factor", 0.25D);
		tier_2_factor = BUILDER.comment("Number of extra ticks tier 2 lubricants do. Must be > 0. Default: 0.5 (1.5x speed)").define("Tier 2 factor", 0.5D);
		tier_3_factor = BUILDER.comment("Number of extra ticks tier 3 lubricants do. Must be > 0. Default: 1 (2x speed)").define("Tier 3 factor", 1.0D);
		tier_4_factor = BUILDER.comment("Number of extra ticks tier 4 lubricants do. Must be > 0. Default: 3 (4x speed)").define("Tier 4 factor", 3.0D);
		BUILDER.pop();

		BUILDER.push("Lubricant consumption rates");
		tier_1_cons = BUILDER.comment("Consumption speed of tier 1 lubricants, calculated as n per every 4 ticks. Must be an integer 1 or greater. Default: 1 (5/s)").define("Tier 1 Consumption", 1);
		tier_2_cons = BUILDER.comment("Consumption speed of tier 2 lubricants, calculated as n per every 4 ticks. Must be an integer 1 or greater. Default: 2 (10/s)").define("Tier 2 Consumption", 2);
		tier_3_cons = BUILDER.comment("Consumption speed of tier 3 lubricants, calculated as n per every 4 ticks. Must be an integer 1 or greater. Default: 3 (15/s)").define("Tier 3 Consumption", 3);
		tier_4_cons = BUILDER.comment("Consumption speed of tier 4 lubricants, calculated as n per every 4 ticks. Must be an integer 1 or greater. Default: 4 (20/s)").define("Tier 4 Consumption", 4);
		BUILDER.pop();
		
		BUILDER.push("Immersive Engineering Machine Toggles");
		doArcFurnace = BUILDER.comment("Add tiered functionality to Arc Furnace. Default: true").define("doArcFurnace", true);
		doAutoEngineerWorkbench = BUILDER.comment("Add tiered functionality to Automated Engineers Workbench. Default: true").define("doAutoEngineerWorkbench", true);
		doCrusherReplace = BUILDER.comment("Add tiered functionality to Crusher, replacing default behavior. Default: true").define("doCrusher", true);
		doExcavatorReplace = BUILDER.comment("Add tiered functionality to Excavator, replacing default behavior. Default: true").define("doExcavatorReplace", true);
		doFermenter = BUILDER.comment("Add tiered functionality to Fermenter. Default: true").define("doFermenter", true);
		doMixer = BUILDER.comment("Add tiered functionality to Mixer. Default: true").define("doMixer", true);
		doPress = BUILDER.comment("Add tiered functionality to Metal Press. Default: true").define("doMetalPress", true);
		doRefinery = BUILDER.comment("Add tiered functionality to Refinery. Default: true").define("doRefinery", true);
		doSqueezer = BUILDER.comment("Add tiered functionality to Squeezer. Default: true").define("doSqueezer", true);
		BUILDER.pop();
		
		BUILDER.push("Immersive Engineering Non-Electric Machine Toggles");
		doCokeOven = BUILDER.comment("Add tiered functionality to Coke Oven. Default: true").define("doCokeOven", true);
		doCrudeBlastFurnace = BUILDER.comment("Add tiered functionality to Crude Blast Furnace. Default: true").define("doCrudeBlastFurnace", true);
		doBlastFurnace = BUILDER.comment("Add tiered functionality to Blast Furnace. Default: true").define("doBlastFurnace", true);
		doAlloySmelter = BUILDER.comment("Add tiered functionality to Alloy Smelter. Default: true").define("doAlloySmelter", true);
		BUILDER.pop();

		BUILDER.push("Immersive Petroleum Machine Toggles");
		doCoker = BUILDER.comment("Add tiered functionality to Coker. Default: true").define("doCoker", true);
		doDistillationTower = BUILDER.comment("Add tiered functionality to Distillation Tower. Default: true").define("doDistillationTower", true);
		doSulfurRecovery = BUILDER.comment("Add tiered functionality to Sulfur Recovery Unit/Hyrdrotreater. Default: true").define("doSulfurRecovery", true);
		doPumpjackReplace = BUILDER.comment("Add tiered functionality to Pumpjack, replacing default behavior. Default: true").define("doPumpjackReplace", true);
		BUILDER.pop();
		
		BUILDER.push("Immersive Geology Machine Toggles");
//		doReverberation = BUILDER.comment("Add tiered functionality to Reverberation Furnace. Default: true").define("doReverberation", true);
		doRotaryKilnIG = BUILDER.comment("Add tiered functionality to the IG Rotary Kiln. Default: true").define("doRotaryKilnIG", true);
		doGravitySeparator = BUILDER.comment("Add tiered functionality to Gravity Separator. Default: true").define("doGravitySeparator", true);
		doCrystallizer = BUILDER.comment("Add tiered functionality to Crystallizer. Default: true").define("doCrystallizer", true);
		doChemicalVat = BUILDER.comment("Add tiered functionality to Chemical Vat. Default: true").define("doChemicalVat", true);
		doHydroCutter = BUILDER.comment("Add tiered functionality to Hydrocutter. Default: true").define("doHydrocutter", true);
		BUILDER.pop();
		

		BUILDER.push("Immersive Industry Machine Toggles");
		doCrucible = BUILDER.comment("Add tiered functionality to Crucible. Default: true").define("doCrucible", true);
		doElectrolyzer = BUILDER.comment("Add tiered functionality to Electrolyzer. Default: true").define("doElectrolyzer", true);
		doRotaryKilnII = BUILDER.comment("Add tiered functionality to the II Rotary Kiln. Default: true").define("doRotaryKilnII", true);
		doCarKiln = BUILDER.comment("Add tiered functionality to Car Kiln. Default: true").define("doCarKiln", true);
		BUILDER.pop();
		
		SPEC = BUILDER.build();

		ExpandedLubrication.LOGGER.info("ExpandedLubrication Config Loaded.");
	}
	
}
