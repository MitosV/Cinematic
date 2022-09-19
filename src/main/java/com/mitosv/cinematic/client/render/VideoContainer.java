package com.mitosv.cinematic.client.render;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class VideoContainer extends AbstractContainerMenu {
    protected VideoContainer() {
        super(null, 0);
    }

    @Override
    public ItemStack quickMoveStack(Player p, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p) {
        return true;
    }
}