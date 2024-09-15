package me.gujibra.hud;


import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.hud.ResizeableHudElement;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.client.api.setting.ColorSetting;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.client.api.utils.objects.PlayerRelation;
import org.rusherhack.core.setting.BooleanSetting;
import org.rusherhack.core.setting.NumberSetting;


import java.awt.*;

import java.util.List;
import java.util.stream.Collectors;

public class FriendList extends ResizeableHudElement {
    private final ColorSetting textColor = new ColorSetting("Color", "The color of the text.", new Color(150, 150, 150, 255)).setAlphaAllowed(true).setRainbow(true);
    private final BooleanSetting onepopMode = new BooleanSetting("Onepop", "one pop", false);
    private final NumberSetting space = new NumberSetting("space", "espaço", 10, 5, 20);
    private String text;
    private int y;
    private int receba;

    public FriendList(){
        super("Wurst+2 FriendList");
        this.registerSettings(
                this.textColor,
                this.onepopMode,
                this.space
        );

    }

    @Override
    public void renderContent(RenderContext context, double mouseX, double mouseY) {
        this.text = "§Lthe_fellas:§r";
        if(onepopMode.getValue()){this.text = "gamers:§r";}
        this.y = 0;
        this.receba = (int)space.getValue();
        this.getFontRenderer().drawString(text , 0, this.y, textColor.getValueRGB());
        this.y += receba;
        for (String username : getFriends()) {
            //ChatUtils.print("Oiiiiiii2"); vai toma no cu porque nao renderiza
            this.getFontRenderer().drawString(username, 0, this.y, textColor.getValueRGB());
            this.y += receba;
        }
    }

    public String[] getFriends(){
        List<PlayerRelation> friends = RusherHackAPI.getRelationManager().getFriends();
        String[] usernames = friends.stream()
                .map(PlayerRelation::username)
                .toArray(String[]::new);
        return usernames;
    }

    @Override
    public double getWidth() {
        return 70;
    }

    @Override
    public double getHeight() {
        return (getFriends().length + 1) * 15;
    }
}
