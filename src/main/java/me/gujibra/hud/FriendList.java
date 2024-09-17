package me.gujibra.hud;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.hud.ResizeableHudElement;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.client.api.setting.ColorSetting;
import org.rusherhack.client.api.utils.objects.PlayerRelation;
import org.rusherhack.core.setting.BooleanSetting;
import org.rusherhack.core.setting.NumberSetting;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;

import java.awt.*;
import java.util.Collection;
import java.util.List;


public class FriendList extends ResizeableHudElement {
    private final ColorSetting textColor = new ColorSetting("Color", "The color of the text.", new Color(150, 150, 150, 255)).setAlphaAllowed(true).setRainbow(true);
    private final BooleanSetting onepopMode = new BooleanSetting("Onepop", "one pop", false);
    private final NumberSetting space = new NumberSetting("space", "espaço", 10, 5, 20);
    private final BooleanSetting onlyOnline = new BooleanSetting("Only online", "so mostra amigos onlibne", true);
    private final int receba = (int) space.getValue();
    private int y;
    private final ClientPacketListener connection = mc.getConnection();
    private final Collection<PlayerInfo> playerList;

    public FriendList(){
        super("W+2 FriendList");
        this.registerSettings(
                this.onlyOnline,
                this.onepopMode,
                this.space,
                this.textColor
        );

    }

    {
        assert connection != null;
        playerList = connection.getOnlinePlayers();
    }


    @Override
    public void renderContent(RenderContext context, double mouseX, double mouseY) {
        String text = "§Lthe_fellas:§r";
        if(onepopMode.getValue()){
            text = "gamers:§r";}
        y = 0;

        this.getFontRenderer().drawString(text, 0, y, textColor.getValueRGB());
        y += receba;

        for (String username : getFriends()) {
            if(!(onlyOnline.getValue())){
                this.getFontRenderer().drawString(username, 0, y, textColor.getValueRGB());
                y += receba;
            }
            verifyOnlineFriends(username);
        }
    }

    public String[] getFriends(){
        List<PlayerRelation> friends = RusherHackAPI.getRelationManager().getFriends();
        return friends.stream()
                .map(PlayerRelation::username)
                .toArray(String[]::new);
    }

    public void verifyOnlineFriends(String name){
        for(PlayerInfo playerInfo : playerList){
            if(name.equalsIgnoreCase(playerInfo.getProfile().getName())){
                this.getFontRenderer().drawString(name, 0, y, textColor.getValueRGB());
                y += receba;
            }
        }
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
