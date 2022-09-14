package com.mitosv.cinematic.commands;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RegisterCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event){
        StartVideoCommand.register(event.getDispatcher());
    }
}

