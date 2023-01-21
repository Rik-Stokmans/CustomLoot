package customloot.customloot.variables;

import de.tr7zw.nbtapi.NBTItem;
import javaslang.Tuple4;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static customloot.customloot.Utils.ChatUtils.format;

public class LootMenu implements Listener {
    public static Inventory inv;

    public LootMenu() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 9, "Example");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        for (Map.Entry<String, Tuple4<String, ArrayList<String>, ArrayList<String>, Material>> value : VariableHandler.itemsTable.entrySet()) {
            inv.addItem(createGuiItem(value.getValue()));
        }
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(Tuple4<String, ArrayList<String>, ArrayList<String>, Material> itemInfo) {
        final ItemStack item = new ItemStack(itemInfo._4, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(format(itemInfo._1));

        NBTItem nbti = new NBTItem(item);

        for (String nbt : itemInfo._3) {
            String[] splitNbt = nbt.split(":");
            nbti.setDouble(splitNbt[0], Double.valueOf(splitNbt[1]));
        }

        ArrayList<String> statsLore = new ArrayList<>();
        if (nbti.hasKey("Damage")) statsLore.add("&cDamage&7: &f" + nbti.getDouble("Damage"));
        if (nbti.hasKey("Penetration")) statsLore.add("&dPenetration&7: &f" + nbti.getDouble("Penetration"));
        if (nbti.hasKey("CriticalHitChance")) statsLore.add("&eCriticalHit&7: &f" + nbti.getDouble("CriticalHitChance") + "&7%");

        nbti.applyNBT(item);

        // Set the lore of the item
        ArrayList<String> lores = new ArrayList<>();
        // Combining the 2 lores
        for (String lore : statsLore) {
            lores.add(format(lore));
        }
        lores.add(" ");
        for (String lore : itemInfo._2) {
            lores.add(format(lore));
        }

        meta.setLore(lores);

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        // Using slots click is a best option for your inventory click's
        p.sendMessage("You clicked at slot " + e.getRawSlot());
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }
}