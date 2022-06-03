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

	
	static {
		BUILDER.push("Lubricant speed factors");
		
		tier_1_factor = BUILDER.comment("Number of extra ticks tier 1 lubricants do. Default: 0.25 (1.25x speed)").define("Tier 1 factor", 0.25D);
		tier_2_factor = BUILDER.comment("Number of extra ticks tier 2 lubricants do. Default: 0.5 (1.5x speed)").define("Tier 2 factor", 0.5D);
		tier_3_factor = BUILDER.comment("Number of extra ticks tier 3 lubricants do. Default: 1 (2x speed)").define("Tier 3 factor", 1.0D);
		tier_4_factor = BUILDER.comment("Number of extra ticks tier 4 lubricants do. Default: 3 (4x speed)").define("Tier 4 factor", 3.0D);
				

		BUILDER.push("Lubricant consumption rates");
		tier_1_cons = BUILDER.comment("Consumption speed of tier 1 lubricants, calculated as n per every 4 ticks. Default: 1 (4/s)").define("Tier 1 Consumption", 1);
		tier_2_cons = BUILDER.comment("Consumption speed of tier 2 lubricants, calculated as n per every 4 ticks. Default: 2 (8/s)").define("Tier 2 Consumption", 2);
		tier_3_cons = BUILDER.comment("Consumption speed of tier 3 lubricants, calculated as n per every 4 ticks. Default: 3 (12/s)").define("Tier 3 Consumption", 3);
		tier_4_cons = BUILDER.comment("Consumption speed of tier 4 lubricants, calculated as n per every 4 ticks. Default: 3 (16/s)").define("Tier 4 Consumption", 4);
		
		BUILDER.pop();
		SPEC = BUILDER.build();

		ExpandedLubrication.LOGGER.info("ExpandedLubrication Config Loaded.");
	}
	
}
