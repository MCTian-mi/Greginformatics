package greginformatics;

import mezz.jei.Internal;
import mezz.jei.gui.GuiEventHandler;
import mezz.jei.gui.GuiScreenHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MODID,
        version = Tags.VERSION,
        name = Tags.MODNAME,
        clientSideOnly = true,
        acceptedMinecraftVersions = "[1.12.2]",
        dependencies = "required-after:modularui@[2.4.3,);required-after:jei@[4.15.0,);required-after:gregtech")
public class Greginformatics {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MODID);

    public static GuiScreenHelper guiScreenHelper;

    @EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc. (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        // register to the event bus so that we can listen to events
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Hello from " + Tags.MODNAME + " + at version " + Tags.VERSION);
    }

    @SubscribeEvent
    // Register recipes here (Remove if not needed)
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {

    }

    @SubscribeEvent
    // Register items here (Remove if not needed)
    public void registerItems(RegistryEvent.Register<Item> event) {

    }

    @SubscribeEvent
    // Register blocks here (Remove if not needed)
    public void registerBlocks(RegistryEvent.Register<Block> event) {

    }

    @EventHandler
    // load "Do your mod setup. Build whatever data structures you care about." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
    }

    @EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        DepictGenerator.generate();
        GuiEventHandler guiEventHandler = ObfuscationReflectionHelper.getPrivateValue(Internal.class, null, "guiEventHandler");
        guiScreenHelper = ObfuscationReflectionHelper.getPrivateValue(GuiEventHandler.class, guiEventHandler, "guiScreenHelper");
    }

    @EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
    }
}
