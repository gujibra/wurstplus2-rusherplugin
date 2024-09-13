package me.gujibra.modules;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.core.setting.BooleanSetting;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;





public class CAMessage extends ToggleableModule {
    private final BooleanSetting onepopMode = new BooleanSetting("onepop", "onepop", false);
    private final Minecraft minecraft = Minecraft.getInstance();
    private String text;



    public CAMessage(){
        super("CA message", "mensagem de crystal aura", ModuleCategory.CHAT);

        this.registerSettings(
                this.onepopMode
        );
    }

    @Override
    public void onEnable(){
        if(mc.level != null) {
            text = "§6Wurst + 2 §7> we §agaming";
            if(onepopMode.getValue()) text = "§1pop §7> oi §1" + mc.player.getName().getString();
            this.minecraft.gui.getChat().addMessage(Component.literal(text));


        }
    }

    @Override
    public void onDisable() {
        if (mc.level != null) {
            text = "§6Wurst + 2 §7> §7we aint §cgaming §7no more";
            if(onepopMode.getValue()) text = "§1pop §7> tchau §4" + mc.player.getName().getString();
            this.minecraft.gui.getChat().addMessage(Component.literal(text));
        }
    }

}
