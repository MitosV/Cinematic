package com.mitosv.cinematic.network.messages;

import com.mitosv.cinematic.Cinematic;
import com.mitosv.cinematic.client.ClientHandler;
import com.mitosv.cinematic.util.FileManager;
import com.mitosv.cinematic.util.Video;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class SendVideoPlayer {



    public static void receive(MinecraftServer s, ServerPlayerEntity player,
                               ServerPlayNetworkHandler handler, PacketByteBuf buf,
                               PacketSender sender){
        String name = buf.readString();
        int volume = buf.readInt();
        Video video = FileManager.getInstance().getVideoFromName(name);
        Cinematic.LOGGER.info("Name: "+name);
        Cinematic.LOGGER.info("Volume: "+volume);
        Cinematic.LOGGER.info("Dir: "+s.getRunDirectory().getAbsolutePath());
        Cinematic.LOGGER.info("This is server");
    }

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        String name = buf.readString();
        int volume = buf.readInt();
        Video video = FileManager.getInstance().getVideoFromName(name);
        Cinematic.LOGGER.info("Name: "+name);
        Cinematic.LOGGER.info("Volume: "+volume);
        Cinematic.LOGGER.info("Dir: "+client.runDirectory.getAbsolutePath());
        Cinematic.LOGGER.info("This is client");
        ClientHandler.openVideo(client,video,volume);
    }

}
