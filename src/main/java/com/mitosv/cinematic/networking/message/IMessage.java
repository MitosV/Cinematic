package com.mitosv.cinematic.networking.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface IMessage <T> {

    void encode(T mess, FriendlyByteBuf buf);

    T decode(FriendlyByteBuf buf);

    void handle(T mess, Supplier<NetworkEvent.Context> supplier);

}