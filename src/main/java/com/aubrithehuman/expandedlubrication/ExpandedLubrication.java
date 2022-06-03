package com.aubrithehuman.expandedlubrication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aubrithehuman.expandedlubrication.config.ELConfig;
import com.aubrithehuman.expandedlubrication.fluids.FluidTagsEL;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ArcFurnaceLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.AutoWorkbenchLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.CokerLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.CrusherLubricationHandlerEL;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.DistillationTowerLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.ExcavatorLubricationHandlerEL;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.FermenterLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.HydroTreaterLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.MixerLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.PressLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.PumpjackLubricationHandlerEL;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.RefineryLubricationHandler;
import com.aubrithehuman.expandedlubrication.immerivepetrol.lubricator.SqueezerLubricationHandler;

import blusunrize.immersiveengineering.common.blocks.metal.ArcFurnaceTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.AutoWorkbenchTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.CrusherTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.ExcavatorTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.FermenterTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.MetalPressTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.MixerTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.RefineryTileEntity;
import blusunrize.immersiveengineering.common.blocks.metal.SqueezerTileEntity;
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
    	LubricatedHandler.registerLubricatedTile(MixerTileEntity.class, MixerLubricationHandler::new);
		LubricatedHandler.registerLubricatedTile(ArcFurnaceTileEntity.class, ArcFurnaceLubricationHandler::new);
		LubricatedHandler.registerLubricatedTile(AutoWorkbenchTileEntity.class, AutoWorkbenchLubricationHandler::new);
		LubricatedHandler.registerLubricatedTile(CokerUnitTileEntity.class, CokerLubricationHandler::new);
		LubricatedHandler.registerLubricatedTile(DistillationTowerTileEntity.class, DistillationTowerLubricationHandler::new);
		LubricatedHandler.registerLubricatedTile(FermenterTileEntity.class, FermenterLubricationHandler::new);
		LubricatedHandler.registerLubricatedTile(MetalPressTileEntity.class, PressLubricationHandler::new);
		LubricatedHandler.registerLubricatedTile(RefineryTileEntity.class, RefineryLubricationHandler::new);
		LubricatedHandler.registerLubricatedTile(SqueezerTileEntity.class, SqueezerLubricationHandler::new);
		LubricatedHandler.registerLubricatedTile(HydrotreaterTileEntity.class, HydroTreaterLubricationHandler::new);
		LubricatedHandler.registerLubricatedTile(CrusherTileEntity.class, CrusherLubricationHandlerEL::new);
		LubricatedHandler.registerLubricatedTile(ExcavatorTileEntity.class, ExcavatorLubricationHandlerEL::new);
		LubricatedHandler.registerLubricatedTile(PumpjackTileEntity.class, PumpjackLubricationHandlerEL::new);
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
