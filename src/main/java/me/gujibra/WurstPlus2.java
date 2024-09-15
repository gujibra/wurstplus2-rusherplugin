package me.gujibra;

import me.gujibra.hud.*;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;
import me.gujibra.modules.*;

/**
 * Example rusherhack plugin
 *
 * @author John200410
 */
public class WurstPlus2 extends Plugin {
    public static final String name = "Wurst+ 2";
    public static final String version = "v0.1.0";

    @Override
    public void onLoad() {
        this.getLogger().info("Receba");


        final CAMessage caMesagge = new CAMessage();
        RusherHackAPI.getModuleManager().registerFeature(caMesagge);



        final Welcome welcome = new Welcome();
        RusherHackAPI.getHudManager().registerFeature(welcome);
        final WurstWatermark wurstWatermark = new WurstWatermark();
        RusherHackAPI.getHudManager().registerFeature(wurstWatermark);
        final FriendList friendList = new FriendList();
        RusherHackAPI.getHudManager().registerFeature(friendList);

    }

    @Override
    public void onUnload() {
        this.getLogger().info("valeu!");
    }

}