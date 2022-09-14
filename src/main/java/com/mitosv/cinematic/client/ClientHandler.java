package com.mitosv.cinematic.client;

import com.mitosv.cinematic.Cinematic;
import com.mitosv.cinematic.client.render.VideoScreen;
import com.mitosv.cinematic.networking.message.SendVideoPlayer;
import com.mitosv.cinematic.util.FileManager;
import com.mitosv.cinematic.util.KeyBinding;
import com.mitosv.cinematic.util.Video;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {

    public static void handlePacket(SendVideoPlayer msg, Supplier<NetworkEvent.Context> ctx){
        Video video = FileManager.getInstance().getVideoFromName(msg.name);
        LocalPlayer player = Minecraft.getInstance().player;
        if (video == null){
            player.sendMessage(new TextComponent("Dont have "+video.getName()+" file"),player.getUUID());
            return;
        }
        openVideo(video, msg.volume);
    }

    public static void openVideo(Video video, int volume) {
        Minecraft.getInstance().setScreen(new VideoScreen(video,volume));
    }

    public static void register(){
        ClientRegistry.registerKeyBinding(KeyBinding.EXIT_KEY);
    }


}