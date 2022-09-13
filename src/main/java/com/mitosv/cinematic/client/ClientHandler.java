package com.mitosv.cinematic.client;

import com.mitosv.cinematic.Cinematic;
import com.mitosv.cinematic.client.render.VideoScreen;
import com.mitosv.cinematic.util.KeyBinding;
import com.mitosv.cinematic.util.Video;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {
    public static void openVideo(Video video, int volume) {
        Minecraft.getInstance().setScreen(new VideoScreen(video,volume));
        Cinematic.setCinematic(true);
    }

    public static void registerKey(){
        ClientRegistry.registerKeyBinding(KeyBinding.EXIT_KEY);
    }

    @SubscribeEvent
    public static void onOpenGui(ScreenOpenEvent e){
        if (Cinematic.isInCinematic()){
            e.setCanceled(true);
        }
    }

}