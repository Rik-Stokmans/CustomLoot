package customloot.customloot.LootMenu;

import customloot.customloot.CustomLoot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static customloot.customloot.LootMenu.LootMenu.*;
import static customloot.customloot.Utils.ChatUtils.format;

public class OpenLootMenu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.equals(lootInvEditor)) {
                CustomLoot.lootMenu.openInventory(player, lootItemListInv);
                if (!expectedInputTypeLoot.equals("NONE")) {
                    player.sendMessage(format("&7your edit was canceled, type your edit in chat before opening the menu"));
                    expectedInputTypeLoot = "NONE";
                }
                return true;
            }
            else if (player.equals(weaponInvEditor)) {
                CustomLoot.lootMenu.openInventory(player, weaponItemListInv);
                if (!expectedInputTypeWeapon.equals("NONE")) {
                    player.sendMessage(format("&7your edit was canceled, type your edit in chat before opening the menu"));
                    expectedInputTypeWeapon = "NONE";
                }
                return true;
            }
            else if (player.equals(talismanInvEditor)) {
                CustomLoot.lootMenu.openInventory(player, talismanItemListInv);
                if (!expectedInputTypeTalisman.equals("NONE")) {
                    player.sendMessage(format("&7your edit was canceled, type your edit in chat before opening the menu"));
                    expectedInputTypeTalisman = "NONE";
                }
                return true;
            }
            else if (player.equals(potionInvEditor)) {
                CustomLoot.lootMenu.openInventory(player, potionItemListInv);
                if (!expectedInputTypePotion.equals("NONE")) {
                    player.sendMessage(format("&7your edit was canceled, type your edit in chat before opening the menu"));
                    expectedInputTypePotion = "NONE";
                }
                return true;
            } else {
                CustomLoot.lootMenu.openInventory(player, categorySelectionInv);
                return true;
            }
        } else {
            return false;
        }
    }
}
