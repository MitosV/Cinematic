package com.mitosv.cinematic.server;

import com.mitosv.cinematic.Cinematic;
import com.mitosv.cinematic.network.PacketHandler;
import com.mitosv.cinematic.util.FileManager;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.mixin.gametest.MinecraftServerMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;

import java.io.File;

@Environment(EnvType.SERVER)
public class ServerHandler implements DedicatedServerModInitializer {

    static MinecraftServer server;

    static boolean isLoaded = false;

    @Override
    public void onInitializeServer() {
        ServerTickEvents.START_SERVER_TICK.register(new ServerTick());
    }

    public static void onlyServer(){
        Cinematic.fileManager = new FileManager(new File(server.getRunDirectory(),"video"));
        PacketHandler.registerServer();
    }

}
