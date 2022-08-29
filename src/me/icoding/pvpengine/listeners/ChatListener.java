package me.icoding.pvpengine.listeners;

import me.icoding.pvpengine.utils.ColorUtil;
import me.icoding.pvpengine.utils.ConfigUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        event.setCancelled(true);

        if (message.startsWith("#") && player.hasPermission("pvpengine.staffchat")) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (pl.hasPermission("pvpengine.staffchat")) {
                    pl.sendMessage(ConfigUtil.getFormattedString("chat-format.staff-chat-format", player, message).replace("# ", "").replace("#", ""));
                }
            }

            return;
        }

        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (player.getUniqueId().toString().equals(UUID.fromString("1d0e1f81-9783-4882-8085-4cd8e7701a07").toString())) {
                ComponentBuilder builder = new ComponentBuilder(ColorUtil.c("&8[&6✦&8] "))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ColorUtil.c(
                                "&7Hello! My name is &9ICoding &8(icxd)&7 and I'm the\n" +
                                        "&7developer of &9PvpEngine&7. Don't worry, this little\n" +
                                        "&7thing does nothing except give credit to the\n" +
                                        "&7original creator of the plugin, aka me :)\n" +
                                        "&7\n" +
                                        "&7Best regards, &9icxd"
                        )).create()))
                        .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://adventuremc.net/vid.mp4"))
                        .append(ConfigUtil.getFormattedString("chat-format.format", player, message));

                pl.spigot().sendMessage(builder.create());
            } else {
                pl.sendMessage(ConfigUtil.getFormattedString("chat-format.format", player, message));
            }
        }
    }

}
