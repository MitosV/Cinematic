package com.mitosv.cinematic.util;

import com.mitosv.cinematic.Cinematic;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import nick1st.fancyvideo.api.DynamicResourceLocation;
import nick1st.fancyvideo.api.eventbus.EventException;
import nick1st.fancyvideo.api.eventbus.FancyVideoEvent;
import nick1st.fancyvideo.api.eventbus.FancyVideoEventBus;
import nick1st.fancyvideo.api.eventbus.event.PlayerRegistryEvent;
import nick1st.fancyvideo.api.mediaPlayer.SimpleMediaPlayer;

public class FancyEvents {

    private static DynamicResourceLocation resourceLocation;

    public void register(){
        try {
            FancyVideoEventBus.getInstance().registerEvent(this);
        } catch(EventException.EventRegistryException | EventException.UnauthorizedRegistryException e) {
            Cinematic.LOGGER.warn("A fatal API error occurred!");
        }
    }

    @FancyVideoEvent
    @SuppressWarnings("unused")
    public void init(PlayerRegistryEvent.AddPlayerEvent event) {
        resourceLocation = new DynamicResourceLocation(Cinematic.MOD_ID, "video");
        event.handler().registerPlayerOnFreeResLoc(resourceLocation, SimpleMediaPlayer.class);
        if (event.handler().getMediaPlayer(resourceLocation).providesAPI()) {
            Cinematic.LOGGER.info("Correctly setup");
        } else {
            Cinematic.LOGGER.warn("Running in NO_LIBRARY_MODE");
        }
    }

    public static DynamicResourceLocation getResourceLocation() {
        return resourceLocation;
    }


}