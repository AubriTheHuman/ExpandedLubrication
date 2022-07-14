package com.aubrithehuman.expandedlubrication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aubrithehuman.expandedlubrication.config.ELConfig;
import com.aubrithehuman.expandedlubrication.fluids.FluidTagsEL;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ie.AlloySmelterLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ie.ArcFurnaceLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ie.AutoWorkbenchLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ie.BlastFurnaceAdvancedLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ie.CokeOvenLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ie.CrudeBlastFurnaceLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ie.CrusherLubricationHandlerEL;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ie.ExcavatorLubricationHandlerEL;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ie.FermenterLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ie.MixerLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ie.PressLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ie.RefineryLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ie.SqueezerLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ig.ChemicalVatLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ig.CrystallizerLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ig.GravitySeparatorLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ig.ReverberationLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ig.RotaryKilnLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ii.CrucibleLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ii.ElectrolyzerLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ip.CokerLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ip.DistillationTowerLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ip.HydroTreaterLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ip.PumpjackLubricationHandlerEL;
import com.igteam.immersive_geology.common.block.tileentity.ChemicalVatTileEntity;
import com.igteam.immersive_geology.common.block.tileentity.CrystallizerTileEntity;
import com.igteam.immersive_geology.common.block.tileentity.GravitySeparatorTileEntity;
import com.igteam.immersive_geology.common.block.tileentity.ReverberationFurnaceTileEntity;
import com.igteam.immersive_geology.common.block.tileentity.RotaryKilnTileEntity;
import com.teammoeg.immersiveindustry.content.crucible.CrucibleTileEntity;
import com.teammoeg.immersiveindustry.content.electrolyzer.IndustrialElectrolyzerTileEntity;

import blusunrize.immersiveengineering.common.blocks.metal.ArcFurnaceTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.AutoWorkbenchTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.CrusherTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.ExcavatorTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.FermenterTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.MetalPressTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.MixerTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.RefineryTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.SqueezerTileEntity;
import blusunrize.immersiveengineering.common.blocks.stone.AlloySmelterTileEntity;
import blusunrize.immersiveengineering.common.blocks.stone.BlastFurnaceAdvancedTileEntity;
import blusunrize.immersiveengineering.common.blocks.stone.BlastFurnaceTileEntity;
import blusunrize.immersiveengineering.common.blocks.stone.CokeOvenTileEntity;
import flaxbeard.immersivepetroleum.api.crafting.LubricatedHandler;
import flaxbeard.immersivepetroleum.common.blocks.tileentities.CokerUnitTileEntity;
import flaxbeard.immersivepetroleum.common.blocks.tileentities.DistillationTowerTileEntity;
import flaxbeard.immersivepetroleum.common.blocks.tileentities.HydrotreaterTileEntity;
import flaxbeard.immersivepetroleum.common.blocks.tileentities.PumpjackTileEntity;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("expandedlubrication")
public class ExpandedLubrication
{
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "expandedlubrication";
	
//	public static void register() {
//		ORE_TAB = new OreTab();
//		METAL_TAB = new MetalTab();
//		MACHINE_TAB = new MachineTab();
//		FLUID_TAB = new FluidTab();
//		MATERIAL_TAB = new MaterialTab();
//		BIO_TAB = new BioTab();
//	}
	
	

    public ExpandedLubrication()
    {
    	
    	
    	IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	    	
    	init();
    	FluidTagsEL.init();
    	
    	ModLoadingContext.get().registerConfig(Type.COMMON, ELConfig.SPEC, "expandedlubrication-common.toml");
    	
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC); 
        // Register the loadComplete method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    	LOGGER.info("ExpandedLubrication Loaded");
    }
    
    public static void init() {

		
		
//		try {
//			Field lubricationHandlers = LubricatedHandler.class.getDeclaredField("lubricationHandlers");
//			lubricationHandlers.setAccessible(true);
//			System.out.println(lubricationHandlers.get(null));
//			Map<Class<? extends TileEntity>, ILubricationHandler<? extends TileEntity>> newLubricantedHandler = new HashMap<>();
//			lubricationHandlers.set(null, newLubricantedHandler);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
    	LOGGER.info("ExpandedLubrication Init");
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
    	
    }
    
    public void loadComplete(FMLLoadCompleteEvent event){
    	if(ModList.get().isLoaded("immersiveengineering")) {
    		int i = 0;
    		//IE Machines
        	if(ELConfig.doArcFurnace.get()) { LubricatedHandler.registerLubricatedTile(ArcFurnaceTileEntity.class, ArcFurnaceLubricationHandler::new); i++; }
        	if(ELConfig.doAutoEngineerWorkbench.get()) { LubricatedHandler.registerLubricatedTile(AutoWorkbenchTileEntity.class, AutoWorkbenchLubricationHandler::new); i++; }
        	if(ELConfig.doFermenter.get()) { LubricatedHandler.registerLubricatedTile(FermenterTileEntity.class, FermenterLubricationHandler::new); i++; }
        	if(ELConfig.doMixer.get()) { LubricatedHandler.registerLubricatedTile(MixerTileEntity.class, MixerLubricationHandler::new); i++; }
        	if(ELConfig.doPress.get()) { LubricatedHandler.registerLubricatedTile(MetalPressTileEntity.class, PressLubricationHandler::new); i++; }
        	if(ELConfig.doRefinery.get()) { LubricatedHandler.registerLubricatedTile(RefineryTileEntity.class, RefineryLubricationHandler::new); i++; }
        	if(ELConfig.doSqueezer.get()) { LubricatedHandler.registerLubricatedTile(SqueezerTileEntity.class, SqueezerLubricationHandler::new); i++; }
        	if(ELConfig.doCrusherReplace.get()) { LubricatedHandler.registerLubricatedTile(CrusherTileEntity.class, CrusherLubricationHandlerEL::new); i++; }
        	if(ELConfig.doExcavatorReplace.get()) { LubricatedHandler.registerLubricatedTile(ExcavatorTileEntity.class, ExcavatorLubricationHandlerEL::new); i++; }
        	LOGGER.info("Loaded " + i + " Immersive Engineering tiered lubrication handlers.");
        	
        	i = 0;
        	//IE non-electric
        	if(ELConfig.doCokeOven.get()) { LubricatedHandler.registerLubricatedTile(CokeOvenTileEntity.class, CokeOvenLubricationHandler::new); i++; }
        	if(ELConfig.doCrudeBlastFurnace.get()) { LubricatedHandler.registerLubricatedTile(BlastFurnaceTileEntity.class, CrudeBlastFurnaceLubricationHandler::new); i++; }
        	if(ELConfig.doBlastFurnace.get()) { LubricatedHandler.registerLubricatedTile(BlastFurnaceAdvancedTileEntity.class, BlastFurnaceAdvancedLubricationHandler::new); i++; }
        	if(ELConfig.doAlloySmelter.get()) { LubricatedHandler.registerLubricatedTile(AlloySmelterTileEntity.class, AlloySmelterLubricationHandler::new); i++; }
        	LOGGER.info("Loaded " + i + " Non-Electric Immersive Engineering tiered lubrication handlers.");
    	}

    	if(ModList.get().isLoaded("immersivepetroleum")) {
    		int i = 0;
    		//IP machines
	    	if(ELConfig.doCoker.get()) { LubricatedHandler.registerLubricatedTile(CokerUnitTileEntity.class, CokerLubricationHandler::new); i++; }
	    	if(ELConfig.doDistillationTower.get()) { LubricatedHandler.registerLubricatedTile(DistillationTowerTileEntity.class, DistillationTowerLubricationHandler::new); i++; }
	    	if(ELConfig.doSulfurRecovery.get()) { LubricatedHandler.registerLubricatedTile(HydrotreaterTileEntity.class, HydroTreaterLubricationHandler::new); i++; }
	    	if(ELConfig.doPumpjackReplace.get()) { LubricatedHandler.registerLubricatedTile(PumpjackTileEntity.class, PumpjackLubricationHandlerEL::new); i++; }
	    	LOGGER.info("Loaded " + i + " Immersive Petroleum tiered lubrication handlers.");
    	}

    	if(ModList.get().isLoaded("immersive_geology")) {
    		int i = 0;
    		//IG machines
	    	if(ELConfig.doReverberation.get()) { LubricatedHandler.registerLubricatedTile(ReverberationFurnaceTileEntity.class, ReverberationLubricationHandler::new); i++; }
	    	if(ELConfig.doRotaryKiln.get()) { LubricatedHandler.registerLubricatedTile(RotaryKilnTileEntity.class, RotaryKilnLubricationHandler::new); i++; }
	    	if(ELConfig.doGravitySeparator.get()) { LubricatedHandler.registerLubricatedTile(GravitySeparatorTileEntity.class, GravitySeparatorLubricationHandler::new); i++; }
	    	if(ELConfig.doCrystallizer.get()) { LubricatedHandler.registerLubricatedTile(CrystallizerTileEntity.class, CrystallizerLubricationHandler::new); i++; }
	    	if(ELConfig.doChemicalVat.get()) { LubricatedHandler.registerLubricatedTile(ChemicalVatTileEntity.class, ChemicalVatLubricationHandler::new); i++; }
	    	LOGGER.info("Loaded " + i + " Immersive Geology tiered lubrication handlers.");
    	}
    	

    	if(ModList.get().isLoaded("immersiveindustry")) {
    		int i = 0;
    		//II machines
	    	if(ELConfig.doCrucible.get()) { LubricatedHandler.registerLubricatedTile(CrucibleTileEntity.class, CrucibleLubricationHandler::new); i++; }
	    	if(ELConfig.doElectrolyzer.get()) { LubricatedHandler.registerLubricatedTile(IndustrialElectrolyzerTileEntity.class, ElectrolyzerLubricationHandler::new); i++; }
	    	LOGGER.info("Loaded " + i + " Immersive Industry tiered lubrication handlers.");
    	}
    	
    	FluidTagsEL.overwrite();
	}

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
//        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
//        LOGGER.info("Got IMC {}", event.getIMCStream().
//                map(m->m.messageSupplier().get()).
//                collect(Collectors.toList()));
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            // Register a new block here
//            LOGGER.info("HELLO from Register Block");
        	
        }
        
        
           
    }
}
