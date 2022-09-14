package com.mitosv.cinematic.networking.message;

import com.mitosv.cinematic.Cinematic;
import com.mitosv.cinematic.client.ClientHandler;
import com.mitosv.cinematic.util.FileManager;
import com.mitosv.cinematic.util.Video;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class SendVideoPlayer implements IMessage<SendVideoPlayer>{

    public String name;

    public int volume;

    public SendVideoPlayer(){}

    public SendVideoPlayer(String name, int volume){
        this.name = name;
        this.volume = volume;
    }

    @Override
    public void encode(SendVideoPlayer mess, FriendlyByteBuf buf) {
        buf.writeUtf(mess.name);
        buf.writeInt(mess.volume);
        //Cinematic.LOGGER.info("Encode Video");
    }

    @Override
    public SendVideoPlayer decode(FriendlyByteBuf buf) {
        String name = buf.readUtf();
        int volume = buf.readInt();
        //Cinematic.LOGGER.info("Decode Video");
        return new SendVideoPlayer(name,volume);
    }

    /**/

    @Override
    public void handle(SendVideoPlayer mess, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->ClientHandler.handlePacket(mess,ctx));
        });
        ctx.get().setPacketHandled(true);
        //Cinematic.LOGGER.info("Handle");
    }
}
