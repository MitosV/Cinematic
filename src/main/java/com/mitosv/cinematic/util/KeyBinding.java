package com.mitosv.cinematic.util;

import com.mitosv.cinematic.Cinematic;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY = "key.category."+ Cinematic.MOD_ID+".cinematic";
    public static final String KEY_EXIT = "key."+ Cinematic.MOD_ID+".exit";

    public static final KeyMapping EXIT_KEY = new KeyMapping(KEY_EXIT, KeyConflictContext.GUI,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, KEY_CATEGORY);

}