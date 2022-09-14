package com.mitosv.cinematic;

import com.mitosv.cinematic.client.ClientHandler;
import com.mitosv.cinematic.commands.RegisterCommands;
import com.mitosv.cinematic.commands.StartVideoCommand;
import com.mitosv.cinematic.networking.PacketHandler;
import com.mitosv.cinematic.util.FancyEvents;
import com.mitosv.cinematic.util.FileManager;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import nick1st.fancyvideo.api.DynamicResourceLocation;
import nick1st.fancyvideo.api.eventbus.EventException;
import nick1st.fancyvideo.api.eventbus.FancyVideoEvent;
import nick1st.fancyvideo.api.eventbus.FancyVideoEventBus;
import nick1st.fancyvideo.api.eventbus.event.PlayerRegistryEvent;
import nick1st.fancyvideo.api.mediaPlayer.SimpleMediaPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(Cinematic.MOD_ID)
public final class Cinematic {

    public static final String MOD_ID = "cinematic";

    public static final Logger LOGGER = LogManager.getLogger();

    private static boolean isServer;

    private static FileManager fileManager;



    public Cinematic() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);

    }

    private void onlyClient(){
        LOGGER.info("Cinematic Mod is loaded in client");
        MinecraftForge.EVENT_BUS.register(ClientHandler.class);

        FancyEvents fancyEvents = new FancyEvents();
        fancyEvents.register();

        final File VIDEO_DIR = new File(Minecraft.getInstance().gameDirectory,"video");
        fileManager = new FileManager(VIDEO_DIR);

        ClientHandler.register();
    }

    private void onlyServer(MinecraftServer MCserver){
        LOGGER.info("Cinematic Mod is loaded in server");
        final File VIDEO_DIR = new File(MCserver.getServerDirectory(),"video");
        fileManager = new FileManager(VIDEO_DIR);
    }

    private void commonSetup(FMLCommonSetupEvent e){
        PacketHandler.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        isServer = false;

        onlyClient();
    }


    public static FileManager getFileManager() {
        return fileManager;
    }

    public static boolean isServer(){return isServer;}


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        isServer = true;
        onlyServer(event.getServer());
        StartVideoCommand.register(event.getServer().getCommands().getDispatcher());
    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent e){
        StartVideoCommand.register(e.getDispatcher());
    }


}
