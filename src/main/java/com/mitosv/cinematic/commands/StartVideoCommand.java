package com.mitosv.cinematic.commands;

import com.mitosv.cinematic.Cinematic;
import com.mitosv.cinematic.networking.PacketHandler;
import com.mitosv.cinematic.networking.message.SendVideoPlayer;
import com.mitosv.cinematic.util.FileManager;
import com.mitosv.cinematic.util.Video;
import com.mitosv.cinematic.networking.PacketHandler;
import com.mitosv.cinematic.networking.message.SendVideoPlayer;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.checkerframework.checker.units.qual.C;

import java.util.Collection;

public class StartVideoCommand {

    private static final SuggestionProvider<CommandSourceStack> DIR = (a,b) ->
            SharedSuggestionProvider.suggest(FileManager.getInstance().getFilesNames(), b);

    private static final SuggestionProvider<CommandSourceStack> INT = (a,b) ->
            SharedSuggestionProvider.suggest(new String[]{"50","100","150","200"}, b);

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){

        dispatcher.register(Commands.literal("cinematic")
                .requires((command)->command.hasPermission(2))
                .then(Commands.argument("target", EntityArgument.players())
                        .then(Commands.argument("volume",IntegerArgumentType.integer())
                                .suggests(INT)
                                .then(Commands.argument("archive",StringArgumentType.greedyString())
                                        .suggests(DIR).executes(StartVideoCommand::execute)))));
    }

    private static int execute(CommandContext<CommandSourceStack> command){
        Collection<ServerPlayer> players;
        try {
            players = EntityArgument.getPlayers(command, "target");
        } catch (CommandSyntaxException e) {
            command.getSource().sendFailure(new TextComponent("Error with target parameter."));
            return Command.SINGLE_SUCCESS;
        }

        String video = StringArgumentType.getString(command,"archive");

        int volume = IntegerArgumentType.getInteger(command,"volume");

        if (video == null){
            command.getSource().sendFailure(new TextComponent("Error with file not exist"));
            return Command.SINGLE_SUCCESS;
        }

        for (ServerPlayer player : players) {
            PacketHandler.sendTo(new SendVideoPlayer(video,volume), player);
        }


        return Command.SINGLE_SUCCESS;
    }
}
