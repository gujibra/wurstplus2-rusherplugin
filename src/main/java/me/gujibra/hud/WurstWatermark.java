package me.gujibra.hud;

import me.gujibra.WurstPlus2;
import org.rusherhack.client.api.feature.hud.ResizeableHudElement;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.client.api.setting.ColorSetting;
import org.rusherhack.core.setting.BooleanSetting;


import java.awt.*;
import java.time.LocalTime;

public class WurstWatermark extends ResizeableHudElement {
    private final BooleanSetting onepopMode = new BooleanSetting("onepop", "onepop", false);
    private final ColorSetting textColor = new ColorSetting("Color", "The color of the text.", new Color(150, 150, 150, 255)).setAlphaAllowed(true).setRainbow(true);
    public WurstWatermark(){
        super("Wurst+2 Watermark");
        this.registerSettings(this.textColor);
        this.registerSettings(this.onepopMode);
    }

    @Override
    public void renderContent(RenderContext context, double mouseX, double mouseY) {
        String name = WurstPlus2.name;
        String version = WurstPlus2. version;
        if(onepopMode.getValue()){
            name = "1pop";
            version = "v0.0.5";
        }

        this.getFontRenderer().drawString(name + " §7" + version, 0, 0, textColor.getValueRGB());
    }

    @Override
    public double getWidth() {
        return 80;
    }

    @Override
    public double getHeight() {
        return 10;
    }
}
