package com.mitosv.cinematic;

import com.mitosv.cinematic.client.ClientHandler;
import com.mitosv.cinematic.commands.RegisterCommands;
import com.mitosv.cinematic.networking.PacketHandler;
import com.mitosv.cinematic.util.FileManager;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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



    static DynamicResourceLocation resourceLocation;

    private static boolean isServer;

    private static boolean inCinematic;

    private static FileManager fileManager;

    public Cinematic() {


        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(RegisterCommands.class);

    }

    private void onlyClient(){
        LOGGER.info("This is client version");
        MinecraftForge.EVENT_BUS.register(ClientHandler.class);
        inCinematic = false;

        final File VIDEO_DIR = new File(Minecraft.getInstance().gameDirectory,"video");
        ClientHandler.registerKey();
        try {
            FancyVideoEventBus.getInstance().registerEvent(this);
        } catch(EventException.EventRegistryException | EventException.UnauthorizedRegistryException e) {
            LOGGER.warn("A fatal API error occurred!");
        }
        fileManager = new FileManager(VIDEO_DIR);
    }

    private void onlyServer(MinecraftServer MCserver){
        LOGGER.info("This is Server Version");
        fileManager = new FileManager(new File(MCserver.getServerDirectory(),"video"));
    }

    private void commonSetup(FMLCommonSetupEvent e){
        PacketHandler.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        isServer = false;
        onlyClient();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("cinematic", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }



    public static FileManager getFileManager() {
        return fileManager;
    }

    public static boolean isInCinematic(){return inCinematic;}

    public static void setCinematic(boolean inCinematic){Cinematic.inCinematic = inCinematic;}

    public static boolean isServer(){return isServer;}

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        isServer = true;
        onlyServer(event.getServer());
    }

    @FancyVideoEvent
    @SuppressWarnings("unused")
    public void init(PlayerRegistryEvent.AddPlayerEvent event) {
        resourceLocation = new DynamicResourceLocation(Cinematic.MOD_ID, "video");
        event.handler().registerPlayerOnFreeResLoc(resourceLocation, SimpleMediaPlayer.class);
        if (event.handler().getMediaPlayer(resourceLocation).providesAPI()) {
            LOGGER.info("Correctly setup");
        } else {
            LOGGER.warn("Running in NO_LIBRARY_MODE");
        }
    }

    public static DynamicResourceLocation getResourceLocation() {
        return resourceLocation;
    }

}
