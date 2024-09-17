package me.gujibra.modules;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.events.network.EventPacket;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.client.api.utils.objects.PlayerRelation;
import org.rusherhack.core.event.subscribe.Subscribe;
import org.rusherhack.core.setting.BooleanSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.Packet;



import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PopCounter extends ToggleableModule {
    private final BooleanSetting onepopMode = new BooleanSetting("onepop", "onepop", false);
    public PopCounter(){
        super("PopCounter", "conta os pops duhhhhh", ModuleCategory.CHAT);
        this.registerSettings(
                this.onepopMode
        );
    }
    public static final HashMap<String, Integer> totemPop123 = new HashMap<>();
    private final Minecraft minecraft = Minecraft.getInstance();

    @Subscribe
    public void onPacketReceive(EventPacket.Receive event) {
        Packet<?> packet = event.getPacket();
        if (packet instanceof ClientboundEntityEventPacket) {
            handleEntityEventPacket((ClientboundEntityEventPacket) packet);
        }
    }

    private void handleEntityEventPacket(ClientboundEntityEventPacket entityPacket) {
        if (minecraft.level == null) return;

        byte eventId = entityPacket.getEventId();

        if (eventId == 35) {
            totemPopEvent(entityPacket);
        } else if (eventId == 3) {
            PlayerDeathEvent(entityPacket);
        }
    }


    private void totemPopEvent(ClientboundEntityEventPacket entityPacket) {
        Entity entity = entityPacket.getEntity(minecraft.level);
        if (!(entity instanceof Player)) return;
        String name = entity.getName().getString();
        int popCount = 1;
        if(totemPop123.containsKey(name)){
            popCount = totemPop123.get(name);
            totemPop123.put(name, ++popCount);
        }else{
            totemPop123.put(name, popCount);
        }


        String text;
        if(Arrays.stream(getFriends()).anyMatch(friend -> friend.equalsIgnoreCase(name))){
            text = "§6Wurst + 2 §7> §c§lTotemPop  §7> §rdude, §a§l" + name + " §rhas popped §l" + popCount + "§r totems. you should go help them";
        }else{
            text = "§6Wurst + 2 §7> §c§lTotemPop  §7> §rdude, §c§l" + name + " §rhas popped §l" + popCount + "§r totems. what a loser";
        }
        if(onepopMode.getValue()){
            if(Arrays.stream(getFriends()).anyMatch(friend -> friend.equalsIgnoreCase(name))){
                text = "§b1pop §7> §c§lTotemPop  §7> §a" + name + " §rpopou §l" + popCount + "§r totems. vai ajudar elekkk";
            }else{
                text = "§b1pop §7> §c§lTotemPop  §7> §c" + name + " §rpopou §l" + popCount + "§r totems. cara fracassadokkkkkk";
            }
        }
        this.minecraft.gui.getChat().addMessage(Component.literal(text));
    }

    private void PlayerDeathEvent(ClientboundEntityEventPacket entityPacket) {
        Entity entity = entityPacket.getEntity(minecraft.level);
        if (!(entity instanceof Player)) return;

        String name = entity.getName().getString();
        int popCount = totemPop123.get(name);
        String text;
        if(Arrays.stream(getFriends()).anyMatch(friend -> friend.equalsIgnoreCase(name))){
            text = "§6Wurst + 2 §7> §c§lTotemPop  §7> §rdude, §a§l" + name + " §rjust fucking DIED after popping §l" + popCount + "§r totems. totems. RIP :pray:";
        }else{
            text = "§6Wurst + 2 §7> §c§lTotemPop  §7> §rdude, §c§l" + name + " §rjust fucking DIED after popping §l" + popCount + "§r totems." ;
        }
        if(onepopMode.getValue()){
            if(Arrays.stream(getFriends()).anyMatch(friend -> friend.equalsIgnoreCase(name))){
                text = "§b1pop §7> §c§lTotemPop  §7> §a" + name + " §rmorreu depois de popar §l" + popCount + "§r totems. :(((( culpa do pedroperry";
            }else{
                text = "§b1pop §7> §c§lTotemPop  §7> §c" + name + " §rmorreu depois de popar §l" + popCount + "§r totems fracassadokkkkkk";
            }
        }
        this.minecraft.gui.getChat().addMessage(Component.literal(text));
        totemPop123.remove(name);

    }

    public String[] getFriends(){
        List<PlayerRelation> friends = RusherHackAPI.getRelationManager().getFriends();
        return friends.stream()
                .map(PlayerRelation::username)
                .toArray(String[]::new);
    }
}
