package com.mitosv.cinematic.util;

import com.mitosv.cinematic.Cinematic;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY = "key.category."+ Cinematic.MOD_ID+".cinematic";
    public static final String KEY_EXIT = "key."+ Cinematic.MOD_ID+".exit";

    public static net.minecraft.client.option.KeyBinding EXIT_KEY;

    public static void register(){
        EXIT_KEY = KeyBindingHelper.registerKeyBinding(
                new net.minecraft.client.option.KeyBinding(
                        KEY_EXIT, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K,KEY_CATEGORY));
    }

}
