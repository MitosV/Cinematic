package com.mitosv.cinematic.client.render;

import com.mitosv.cinematic.client.ClientHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

public class VideoScreenHandler extends ScreenHandler {

    public VideoScreenHandler() {
        super(ClientHandler.VIDEOSCREENHANDLER,0);
    }

    public VideoScreenHandler(int i, PlayerInventory playerInventory) {
        super(ClientHandler.VIDEOSCREENHANDLER, 0);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
