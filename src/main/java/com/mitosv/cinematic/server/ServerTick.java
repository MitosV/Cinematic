package com.mitosv.cinematic.server;

import com.mitosv.cinematic.Cinematic;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class ServerTick implements ServerTickEvents.StartTick{
    @Override
    public void onStartTick(MinecraftServer minecraftServer){
        if (!ServerHandler.isLoaded){
            ServerHandler.server = minecraftServer;
            ServerHandler.onlyServer();
            ServerHandler.isLoaded = true;
            Cinematic.LOGGER.info("Server side is loaded");
        }

    }
}
