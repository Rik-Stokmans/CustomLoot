package customloot.customloot.LootMenu;

import customloot.customloot.CustomLoot;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static customloot.customloot.LootMenu.LootMenu.categorySelectionInv;

public class OpenLootMenu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Bukkit.getOnlinePlayers();
            Player player = (Player) sender;
            CustomLoot.lootMenu.openInventory(player, categorySelectionInv);
            return true;
        } else {
            return false;
        }
    }
}
