package me.icoding.pvpengine.commands;

import me.icoding.pvpengine.gui.EnchantGUI;
import me.icoding.pvpengine.utils.ConfigUtil;
import me.icoding.pvpengine.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnchantCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.getInventory().getItemInHand().getType().equals(Material.AIR)) {
                player.sendMessage(ConfigUtil.getFormattedString("messages.no-item-in-hand"));
                return false;
            }

            for (Material material : ItemUtil.ENCHANTABLE_ITEMS) {
                if (player.getInventory().getItemInHand().getType() != material)
                    continue;
                else {
                    new EnchantGUI().open(player);
                    return true;
                }
            }

            player.sendMessage(ConfigUtil.getFormattedString("messages.unenchantable"));
        } else {
            sender.sendMessage(ConfigUtil.getFormattedString("messages.executed-by-console"));
        }

        return false;
    }
}
