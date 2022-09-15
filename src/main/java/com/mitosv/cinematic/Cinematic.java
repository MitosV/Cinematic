package com.mitosv.cinematic;

import com.mitosv.cinematic.commands.StartVideoCommand;
import com.mitosv.cinematic.network.PacketHandler;
import com.mitosv.cinematic.util.FileManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Cinematic implements ModInitializer{

    public static final String MOD_ID = "cinematic";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static FileManager fileManager;

    @Override
    public void onInitialize() {
        LOGGER.info("Cinematic Mod is initialized");
        CommandRegistrationCallback.EVENT.register(StartVideoCommand::register);
    }

}
