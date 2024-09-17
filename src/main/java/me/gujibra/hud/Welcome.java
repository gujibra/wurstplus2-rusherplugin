package me.gujibra.hud;

import org.rusherhack.client.api.feature.hud.ResizeableHudElement;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.client.api.setting.ColorSetting;
import org.rusherhack.core.setting.BooleanSetting;

import java.awt.*;
import java.time.LocalTime;



public class Welcome extends ResizeableHudElement {
    String welcomeText;
    private final BooleanSetting onepopMode = new BooleanSetting("onepop", "onepop", false);
    private final ColorSetting textColor = new ColorSetting("Color", "The color of the text.", new Color(150, 150, 150, 255)).setAlphaAllowed(true).setRainbow(true);
    public Welcome(){
        super("W+2 Welcomer");
        this.registerSettings(
                this.onepopMode,
                this.textColor
        );


    }

    @Override
    public void renderContent(RenderContext context, double mouseX, double mouseY) {
        LocalTime localTime = LocalTime.now();
        int hora = localTime.getHour();

        assert mc.player != null;
        String name = "§6" + mc.player.getName().getString() + "§r";
        if(onepopMode.getValue()) name = "§b" + mc.player.getName().getString() + "§r";

        if(hora >= 0 && hora < 12){
            welcomeText = "Morning, " + name + " you smell good today :)";
            if(onepopMode.getValue()) welcomeText = "Bom dia, " + name + " ja tomou cafe da manha? :)";
        } else if (hora >= 12 && hora < 16){
            welcomeText = "Afternoon, " + name + " you're looking good today :)";
            if(onepopMode.getValue()) welcomeText = "Boa tarde, " + name + " seu token ja ta no meu discord :)";
        } else if(hora >=16 && hora <24){
            welcomeText = "Evening, " + name + " you smell good today :)";
            if(onepopMode.getValue()) welcomeText = "Boa noite, " + name + " espero que não seja o pedroperry :)";
        }else{
             welcomeText = "Welcome, " + name + " you're looking fine today :)";
        }



        this.getFontRenderer().drawString(welcomeText, 0, 0, textColor.getValueRGB());
    }
    @Override
    public double getWidth() {
        return 220;
    }

    @Override
    public double getHeight() {
        return 10;
    }
}
