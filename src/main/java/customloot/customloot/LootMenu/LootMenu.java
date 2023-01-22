package customloot.customloot.LootMenu;

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

import static customloot.customloot.Utils.ChatUtils.format;

public class LootMenu implements Listener {
    public static Inventory categorySelectionInv;
    public static Inventory lootItemListInv;
    public static Inventory talismanItemListInv;
    public static Inventory weaponItemListInv;
    public static Inventory potionListInv;

    public LootMenu() {
        // Create a new inventory
        categorySelectionInv = Bukkit.createInventory(null, 27, format("&eCategories"));

        lootItemListInv = Bukkit.createInventory(null, 27, format("&eLoot Items"));
        talismanItemListInv = Bukkit.createInventory(null, 27, format("&aTalismans"));
        weaponItemListInv = Bukkit.createInventory(null, 27, format("&cWeapons"));
        potionListInv = Bukkit.createInventory(null, 27, format("&bPotions"));

        // Put the items into the inventories
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        //main menu
        ArrayList<String> lootCategoryLore = new ArrayList<>();
        lootCategoryLore.add(format("&7Create, Remove and Manage all the Loot Items"));
        ItemStack lootCategoryItem = createGuiItem("&eLoot Items", lootCategoryLore, Material.COD);

        ArrayList<String> weaponCategoryLore = new ArrayList<>();
        weaponCategoryLore.add(format("&7Create, Remove and Manage all the Weapons"));
        ItemStack weaponCategoryItem = createGuiItem("&cWeapons", weaponCategoryLore, Material.IRON_SWORD);

        ArrayList<String> talismanCategoryLore = new ArrayList<>();
        talismanCategoryLore.add(format("&7Create, Remove and Manage all the Talismans"));
        ItemStack talismanCategoryItem = createGuiItem("&aTalismans", talismanCategoryLore, Material.EMERALD);

        ArrayList<String> potionCategoryLore = new ArrayList<>();
        potionCategoryLore.add(format("&7Create, Remove and Manage all the Potions"));
        ItemStack potionCategoryItem = createGuiItem("&bPotions", potionCategoryLore, Material.GLASS_BOTTLE);

        categorySelectionInv.setItem(10, lootCategoryItem);
        categorySelectionInv.setItem(12, weaponCategoryItem);
        categorySelectionInv.setItem(14, talismanCategoryItem);
        categorySelectionInv.setItem(16, potionCategoryItem);

        //loot items menu


    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(String name, ArrayList<String> lore, Material itemType) {
        final ItemStack item = new ItemStack(itemType, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(format(name));
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    protected ItemStack createLootItem(Tuple4<String, ArrayList<String>, ArrayList<String>, Material> itemInfo) {
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
        ent.openInventory(categorySelectionInv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        Inventory currentInv = e.getInventory();
        if (currentInv.equals(categorySelectionInv) ||
            currentInv.equals(lootItemListInv) ||
            currentInv.equals(weaponItemListInv) ||
            currentInv.equals(talismanItemListInv) ||
            currentInv.equals(potionListInv)) {

            e.setCancelled(true);

            final ItemStack clickedItem = e.getCurrentItem();

            // verify current item is not null
            if (clickedItem == null || clickedItem.getType().isAir()) return;

            final Player p = (Player) e.getWhoClicked();

            // Using slots click is a best option for your inventory click's
            p.sendMessage("You clicked at slot " + e.getRawSlot());
        }
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        Inventory currentInv = e.getInventory();
        if (currentInv.equals(categorySelectionInv) ||
            currentInv.equals(lootItemListInv) ||
            currentInv.equals(weaponItemListInv) ||
            currentInv.equals(talismanItemListInv) ||
            currentInv.equals(potionListInv)) {
            e.setCancelled(true);
        }
    }
}