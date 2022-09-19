package com.mitosv.cinematic.network;

import com.mitosv.cinematic.Cinematic;
import com.mitosv.cinematic.network.messages.SendVideoPlayer;
import com.mitosv.cinematic.server.ServerHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class PacketHandler {

    public static final Identifier NET_ID =
            new Identifier(Cinematic.MOD_ID,"networking");

    public static void registerClient(){
        ClientPlayNetworking.registerGlobalReceiver(NET_ID, SendVideoPlayer::receive);
    }

    public static void sendTo(ServerPlayerEntity player, String name, int volume){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(name);
        buf.writeInt(volume);
        ServerPlayNetworking.send(player,NET_ID,buf);
    }



}
