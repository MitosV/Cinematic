package com.mitosv.cinematic.client;

import com.mitosv.cinematic.Cinematic;
import com.mitosv.cinematic.client.render.VideoScreen;
import com.mitosv.cinematic.networking.message.SendVideoPlayer;
import com.mitosv.cinematic.util.FileManager;
import com.mitosv.cinematic.util.KeyBinding;
import com.mitosv.cinematic.util.Video;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {

    public static void handlePacket(SendVideoPlayer msg, Supplier<NetworkEvent.Context> ctx){
        Video video = FileManager.getInstance().getVideoFromName(msg.name) == null ?
                new Video(msg.name) : FileManager.getInstance().getVideoFromName(msg.name);

        openVideo(video, msg.volume);
    }

    public static void openVideo(Video video, int volume) {
        Minecraft.getInstance().execute(()->{
            Minecraft.getInstance().setScreen(new VideoScreen(video,volume));
        });
    }

    @Mod.EventBusSubscriber(modid = Cinematic.MOD_ID, value = Dist.CLIENT,
            bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientEvents{
        @SubscribeEvent
        public static void registerKey(RegisterKeyMappingsEvent e){
            e.register(KeyBinding.EXIT_KEY);
        }
    }


}