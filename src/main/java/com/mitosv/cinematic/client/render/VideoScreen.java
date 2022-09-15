package com.mitosv.cinematic.client.render;

import com.mitosv.cinematic.Cinematic;
import com.mitosv.cinematic.util.FancyEvents;
import com.mitosv.cinematic.util.KeyBinding;
import com.mitosv.cinematic.util.Video;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import nick1st.fancyvideo.api.DynamicResourceLocation;
import nick1st.fancyvideo.api.MediaPlayerHandler;
import nick1st.fancyvideo.api.mediaPlayer.MediaPlayerBase;
import uk.co.caprica.vlcj.media.Media;
import uk.co.caprica.vlcj.media.MediaEventAdapter;
import uk.co.caprica.vlcj.media.MediaRef;
import uk.co.caprica.vlcj.media.TrackType;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.base.TitleDescription;

public class VideoScreen extends HandledScreen<VideoScreenHandler> {

    boolean init = false;
    boolean stopped = true;
    Video video;
    int volume;
    MediaPlayerBase mediaPlayer;

    public VideoScreen(Video video, int volume) {
        super(new VideoScreenHandler(), MinecraftClient.getInstance().player != null ?
                MinecraftClient.getInstance().player.getInventory() : null, Text.of(Cinematic.MOD_ID));
        this.video = video;
        this.volume = volume;
    }


    @Override
    protected void init(){
        super.init();
        if (MediaPlayerHandler.getInstance().getMediaPlayer(FancyEvents.getResourceLocation()).providesAPI()) {
            MinecraftClient.getInstance().getSoundManager().pauseAll();
            MediaPlayerHandler.getInstance().getMediaPlayer(FancyEvents.
                    getResourceLocation()).api().media().prepare(video.getUrl());
            MediaPlayerHandler.getInstance().getMediaPlayer(FancyEvents.getResourceLocation()).api()
                    .events().addMediaEventListener(new MediaEventAdapter() {
                        @Override
                        public void mediaSubItemAdded(Media media, MediaRef newChild) {
                            MediaList mediaList = MediaPlayerHandler.getInstance().
                                    getMediaPlayer(FancyEvents.getResourceLocation()).api().media().subitems().newMediaList();
                            mediaList.release();
                        }

                        @Override
                        public void mediaSubItemTreeAdded(Media media, MediaRef item) {
                            MediaList mediaList = MediaPlayerHandler.getInstance()
                                    .getMediaPlayer(FancyEvents.getResourceLocation()).api().media().subitems().newMediaList();

                            mediaList.release();
                        }
                    });
            MediaPlayerHandler.getInstance().getMediaPlayer(FancyEvents.getResourceLocation())
                    .api().events().addMediaPlayerEventListener(new MediaPlayerEventListener() {
                        @Override
                        public void mediaChanged(MediaPlayer mediaPlayer, MediaRef mediaRef) {}
                        @Override
                        public void opening(MediaPlayer mediaPlayer) {}
                        @Override
                        public void buffering(MediaPlayer mediaPlayer, float v) {}
                        @Override
                        public void playing(MediaPlayer mediaPlayer) {}
                        @Override
                        public void paused(MediaPlayer mediaPlayer) {}
                        @Override
                        public void stopped(MediaPlayer mediaPlayer) {
                            if (!stopped){
                                close();
                            }
                        }
                        @Override
                        public void forward(MediaPlayer mediaPlayer) {}
                        @Override
                        public void backward(MediaPlayer mediaPlayer) {}
                        @Override
                        public void stopping(MediaPlayer mediaPlayer) {}
                        @Override
                        public void finished(MediaPlayer mediaPlayer) {}
                        @Override
                        public void timeChanged(MediaPlayer mediaPlayer, long l) {}
                        @Override
                        public void positionChanged(MediaPlayer mediaPlayer, float v) {}
                        @Override
                        public void seekableChanged(MediaPlayer mediaPlayer, int i) {}
                        @Override
                        public void pausableChanged(MediaPlayer mediaPlayer, int i) {}
                        @Override
                        public void titleListChanged(MediaPlayer mediaPlayer) {}
                        @Override
                        public void titleSelectionChanged(MediaPlayer mediaPlayer, TitleDescription titleDescription, int i) {}
                        @Override
                        public void snapshotTaken(MediaPlayer mediaPlayer, String s) {}
                        @Override
                        public void lengthChanged(MediaPlayer mediaPlayer, long l) {}
                        @Override
                        public void videoOutput(MediaPlayer mediaPlayer, int i) {}
                        @Override
                        public void elementaryStreamAdded(MediaPlayer mediaPlayer, TrackType trackType, int i, String s) {}
                        @Override
                        public void elementaryStreamDeleted(MediaPlayer mediaPlayer, TrackType trackType, int i, String s) {}
                        @Override
                        public void elementaryStreamUpdated(MediaPlayer mediaPlayer, TrackType trackType, int i, String s) {}
                        @Override
                        public void elementaryStreamSelected(MediaPlayer mediaPlayer, TrackType trackType, String s, String s1) {}
                        @Override
                        public void corked(MediaPlayer mediaPlayer, boolean b) {}
                        @Override
                        public void muted(MediaPlayer mediaPlayer, boolean b) {}
                        @Override
                        public void volumeChanged(MediaPlayer mediaPlayer, float v) {}
                        @Override
                        public void audioDeviceChanged(MediaPlayer mediaPlayer, String s) {}
                        @Override
                        public void chapterChanged(MediaPlayer mediaPlayer, int i) {}
                        @Override
                        public void programAdded(MediaPlayer mediaPlayer, int i) {}
                        @Override
                        public void programDeleted(MediaPlayer mediaPlayer, int i) {}
                        @Override
                        public void programUpdated(MediaPlayer mediaPlayer, int i) {}
                        @Override
                        public void programSelected(MediaPlayer mediaPlayer, int i, int i1) {}
                        @Override
                        public void error(MediaPlayer mediaPlayer) {}
                        @Override
                        public void mediaPlayerReady(MediaPlayer mediaPlayer) {}
                    });
            MediaPlayerHandler.getInstance().getMediaPlayer(FancyEvents.getResourceLocation()).api().audio().setVolume(volume);

        }
    }


    @Override
    public void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        mediaPlayer = (MediaPlayerBase) MediaPlayerHandler.getInstance().getMediaPlayer(FancyEvents.getResourceLocation());
        if (MediaPlayerHandler.getInstance().getMediaPlayer(FancyEvents.getResourceLocation()).providesAPI()) {
            if (!init) {
                mediaPlayer.api().controls().play();
                init = true;
                stopped = false;
            }

            int width = this.width;
            int height = this.height;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, mediaPlayer.renderToResourceLocation());

            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            DrawableHelper.drawTexture(matrices, 0, 0, 0.0F, 0.0F, width, height, width, height);
        } else {

            int width = this.width;
            int height = this.height;

            int width2;

            if (width <= height) {
                width2 = width / 3;
            } else {
                width2 = height / 2;
            }

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            DynamicResourceLocation dr = new DynamicResourceLocation(Cinematic.MOD_ID, "fallback");
            Cinematic.LOGGER.info("else: " + dr);
            RenderSystem.setShaderTexture(0, dr);

            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            DrawableHelper.drawTexture(matrices, 0, 0, 0.0F, 0.0F, width, height, width2, width2);
        }
        RenderSystem.disableBlend();
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == 67){
            this.close();
            return true;
        }else return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void close() {
        if (!stopped) {
            stopped = true;
            MinecraftClient.getInstance().getSoundManager().resumeAll();
            mediaPlayer.api().controls().stop();
        }
        super.close();
    }
}
