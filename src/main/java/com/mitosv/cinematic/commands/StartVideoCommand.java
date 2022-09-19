package com.mitosv.cinematic.commands;

import com.mitosv.cinematic.commands.suggest.VideoSuggest;
import com.mitosv.cinematic.commands.suggest.VolumeSuggest;
import com.mitosv.cinematic.network.PacketHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;

public class StartVideoCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated){

        dispatcher.register(CommandManager.literal("cinematic")
                .requires((command)-> command.hasPermissionLevel(2))
                .then(CommandManager.argument("target", EntityArgumentType.players())
                        .then(CommandManager.argument("volume",IntegerArgumentType.integer())
                                .suggests(new VolumeSuggest())
                                .then(CommandManager.argument("archive",StringArgumentType.greedyString())
                                        .suggests(new VideoSuggest())
                                        .executes(StartVideoCommand::execute)))));

    }


    private static int execute(CommandContext<ServerCommandSource> command){
        Collection<ServerPlayerEntity> players;
        try {
            players = EntityArgumentType.getPlayers(command, "target");
        } catch (CommandSyntaxException e) {
            command.getSource().sendError(Text.of("Error with target parameter."));
            return Command.SINGLE_SUCCESS;
        }

        String video = StringArgumentType.getString(command,"archive");

        int volume = IntegerArgumentType.getInteger(command,"volume");

        if (video == null){
            command.getSource().sendError(Text.of("Error with file not exist"));
            return Command.SINGLE_SUCCESS;
        }

        for (ServerPlayerEntity player : players) {
            PacketHandler.sendTo(player,video,volume);
        }

        return Command.SINGLE_SUCCESS;
    }

}
