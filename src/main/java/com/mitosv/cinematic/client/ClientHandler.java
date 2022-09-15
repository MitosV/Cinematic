package com.mitosv.cinematic.client;

import com.mitosv.cinematic.Cinematic;
import com.mitosv.cinematic.client.render.VideoScreen;
import com.mitosv.cinematic.client.render.VideoScreenHandler;
import com.mitosv.cinematic.mixin.MinecraftClientAccessor;
import com.mitosv.cinematic.network.PacketHandler;
import com.mitosv.cinematic.util.FancyEvents;
import com.mitosv.cinematic.util.FileManager;
import com.mitosv.cinematic.util.KeyBinding;
import com.mitosv.cinematic.util.Video;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

import java.io.File;

@Environment(EnvType.CLIENT)
public class ClientHandler implements ClientModInitializer {

    public static ScreenHandlerType<VideoScreenHandler> VIDEOSCREENHANDLER;


    @Override
    public void onInitializeClient() {
        Cinematic.LOGGER.info("Client side loaded");

        Cinematic.fileManager =
                new FileManager(new File(MinecraftClient.getInstance().runDirectory,"video"));

        KeyBinding.register();

        FancyEvents fancyEvents = new FancyEvents();
        fancyEvents.register();

        VIDEOSCREENHANDLER = new ScreenHandlerType<VideoScreenHandler>(VideoScreenHandler::new);

        PacketHandler.registerClient();

    }

    public static void openVideo(MinecraftClient client,Video video, int volume){
        MinecraftClient.getInstance().setScreen(new VideoScreen(video,volume));

    }





}
