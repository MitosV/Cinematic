package com.mitosv.cinematic.networking.message;

import com.mitosv.cinematic.Cinematic;
import com.mitosv.cinematic.client.ClientHandler;
import com.mitosv.cinematic.util.FileManager;
import com.mitosv.cinematic.util.Video;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.network.NetworkEvent;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class SendVideoPlayer implements IMessage<SendVideoPlayer>{

    String name;

    int volume;

    public SendVideoPlayer(){}

    public SendVideoPlayer(String name, int volume){
        this.name = name;
        this.volume = volume;
    }

    @Override
    public void encode(SendVideoPlayer mess, FriendlyByteBuf buf) {
        buf.writeInt(mess.name.length());
        buf.writeCharSequence(mess.name, StandardCharsets.UTF_8);
        buf.writeByte(mess.volume);
        Cinematic.LOGGER.info("Encode Video");
    }

    @Override
    public SendVideoPlayer decode(FriendlyByteBuf buf) {
        int l = buf.readInt();
        String url = String.valueOf(buf.readCharSequence(l, StandardCharsets.UTF_8));
        int volume = buf.readByte();
        Cinematic.LOGGER.info("Decode Video");
        return new SendVideoPlayer(url,volume);
    }

    /**/

    @Override
    public void handle(SendVideoPlayer mess, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(()->ClientHandler.openVideo(
                FileManager.getInstance().getVideoFromName(mess.name), mess.volume));
        supplier.get().setPacketHandled(true);
        Cinematic.LOGGER.info("Handle");
    }
}
