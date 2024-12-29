package com.dobber0.television;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Television.MODID, version = Tags.VERSION, name = "Television", acceptedMinecraftVersions = "[1.7.10]")
public class Television {

    public static final String MODID = "tv";
    public static final Logger LOG = LogManager.getLogger(MODID);

    public static Block blockRadio;

    @SidedProxy(clientSide = "com.dobber0.television.ClientProxy", serverSide = "com.dobber0.television.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        this.blockRadio = new BlockRadio(Material.cloth).setBlockName("Radio").setBlockTextureName("tv:blockradio");
        GameRegisty.registerBlock(this.blockRadio, this.blockRadio.getUnlocalizedName().substring(5));
    }

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
