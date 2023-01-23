package customloot.customloot.LootMenu;

import customloot.customloot.Loot.*;
import de.tr7zw.nbtapi.NBTItem;
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
import java.util.Map;

import static customloot.customloot.Utils.ChatUtils.format;
import static customloot.customloot.Utils.ChatUtils.formatList;
import static customloot.customloot.variables.VariableHandler.*;

public class LootMenu implements Listener {
    public static Inventory categorySelectionInv;
    public static Inventory lootItemListInv;
    public static Inventory talismanItemListInv;
    public static Inventory weaponItemListInv;
    public static Inventory potionListInv;

    ItemStack lootCategoryItem;
    ItemStack weaponCategoryItem;
    ItemStack talismanCategoryItem;
    ItemStack potionCategoryItem;

    public LootMenu() {
        // Create a new inventory
        categorySelectionInv = Bukkit.createInventory(null, 27, format("&eCategories"));

        lootItemListInv = Bukkit.createInventory(null, 27, format("&eLoot Items"));
        weaponItemListInv = Bukkit.createInventory(null, 27, format("&cWeapons"));
        talismanItemListInv = Bukkit.createInventory(null, 27, format("&aTalismans"));
        potionListInv = Bukkit.createInventory(null, 27, format("&bPotions"));

        // Put the items into the inventories
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        //main menu
        ArrayList<String> lootCategoryLore = new ArrayList<>();
        lootCategoryLore.add(format("&7Create, Remove and Manage all the Loot Items"));
        lootCategoryItem = createGuiItem("&eLoot Items", lootCategoryLore, Material.COD);

        ArrayList<String> weaponCategoryLore = new ArrayList<>();
        weaponCategoryLore.add(format("&7Create, Remove and Manage all the Weapons"));
        weaponCategoryItem = createGuiItem("&cWeapons", weaponCategoryLore, Material.IRON_SWORD);

        ArrayList<String> talismanCategoryLore = new ArrayList<>();
        talismanCategoryLore.add(format("&7Create, Remove and Manage all the Talismans"));
        talismanCategoryItem = createGuiItem("&aTalismans", talismanCategoryLore, Material.EMERALD);

        ArrayList<String> potionCategoryLore = new ArrayList<>();
        potionCategoryLore.add(format("&7Create, Remove and Manage all the Potions"));
        potionCategoryItem = createGuiItem("&bPotions", potionCategoryLore, Material.GLASS_BOTTLE);

        categorySelectionInv.setItem(10, lootCategoryItem);
        categorySelectionInv.setItem(12, weaponCategoryItem);
        categorySelectionInv.setItem(14, talismanCategoryItem);
        categorySelectionInv.setItem(16, potionCategoryItem);

        //loot items menu
        int itemIterator = 0;
        for (Map.Entry<String, Loot> value : lootItems.entrySet()) {
            lootItemListInv.setItem(itemIterator, generateLootItem(value.getValue()));
            itemIterator++;
        }
        itemIterator = 0;
        for (Map.Entry<String, Weapon> value : weaponItems.entrySet()) {
            weaponItemListInv.setItem(itemIterator, generateWeaponItem(value.getValue()));
            itemIterator++;
        }
        itemIterator = 0;
        for (Map.Entry<String, Talisman> value : talismanItems.entrySet()) {
            talismanItemListInv.setItem(itemIterator, generateTalismanItem(value.getValue()));
            itemIterator++;
        }
        itemIterator = 0;
        for (Map.Entry<String, Potion> value : potionItems.entrySet()) {
            potionListInv.setItem(itemIterator, generatePotionItem(value.getValue()));
            itemIterator++;
        }

    }

    private ItemStack generateLootItem(Loot itemTemplate) {
        ItemStack item = new ItemStack(itemTemplate.getItemType(), 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(format(itemTemplate.getName()));

        ArrayList<String> lores = new ArrayList<>();
        lores.add("");
        if (itemTemplate.getLores().size() > 0) {
            for (String lore : itemTemplate.getLores()) lores.add(lore);
            lores.add("");
        }

        if (itemTemplate.getRarity() == 0) lores.add("&7[✫✫✫]");
        if (itemTemplate.getRarity() == 1) lores.add("&6[&e✫&7✫✫&6]");
        if (itemTemplate.getRarity() == 2) lores.add("&5[&d✫✫&7✫&5]");
        if (itemTemplate.getRarity() == 3) lores.add("&3[&b✫✫✫&3]");
        lores.add("");
        lores.add("&9Attributes:");
        lores.add(" &7- &fValue: " + itemTemplate.getValue());

        meta.setLore(formatList(lores));

        item.setItemMeta(meta);

        NBTItem nbti = new NBTItem(item);
        nbti.setInteger("value", itemTemplate.getValue());
        nbti.applyNBT(item);

        return item;
    }

    private ItemStack generateWeaponItem(Weapon itemTemplate) {
        ItemStack item = new ItemStack(itemTemplate.getItemType(), 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(format(itemTemplate.getName()));


        ArrayList<String> lores = new ArrayList<>();
        lores.add("");
        lores.add("&cDamage&f: &c" + itemTemplate.getDamage());
        lores.add("&7Lv. Min: " + itemTemplate.getMinLvl());
        lores.add("");

        //Attributes
        if (itemTemplate.getXpBonus() != 0)
            lores.add("&7Xp Bonus: &a" + itemTemplate.getXpBonus() + "%");
        if (itemTemplate.getLootBonus() != 0)
            lores.add("&7Loot Bonus: &a" + itemTemplate.getLootBonus() + "%");
        if (itemTemplate.getAttackBonus() != 0)
            lores.add("&7Attack Bonus: &a" + itemTemplate.getAttackBonus() + "%");
        if (itemTemplate.getLifesteal() != 0)
            lores.add("&7Lifesteal: &a" + itemTemplate.getLifesteal());

        if (!lores.get(lores.size()-1).equals("")) lores.add("");

        if (itemTemplate.getLores().size() > 0) for (String lore : itemTemplate.getLores()) lores.add(lore);

        if (!lores.get(lores.size()-1).equals("")) lores.add("");

        if (itemTemplate.getRarity() == 0) lores.add("&7[✫✫✫]");
        if (itemTemplate.getRarity() == 1) lores.add("&6[&e✫&7✫✫&6]");
        if (itemTemplate.getRarity() == 2) lores.add("&5[&d✫✫&7✫&5]");
        if (itemTemplate.getRarity() == 3) lores.add("&3[&b✫✫✫&3]");

        if (!lores.get(lores.size()-1).equals("")) lores.add("");

        lores.add("&9Attributes:");
        lores.add(" &7- &fValue: " + itemTemplate.getValue());

        meta.setLore(formatList(lores));

        item.setItemMeta(meta);

        NBTItem nbti = new NBTItem(item);
        nbti.setInteger("value", itemTemplate.getValue());
        nbti.setInteger("damage", itemTemplate.getDamage());
        nbti.setInteger("minlvl", itemTemplate.getMinLvl());
        nbti.setInteger("xpbonus", itemTemplate.getXpBonus());
        nbti.setInteger("lootbonus", itemTemplate.getLootBonus());
        nbti.setInteger("attackbonus", itemTemplate.getAttackBonus());
        nbti.setInteger("lifesteal", itemTemplate.getLifesteal());
        nbti.applyNBT(item);

        return item;
    }

    private ItemStack generateTalismanItem(Talisman itemTemplate) {
        ItemStack item = new ItemStack(itemTemplate.getItemType(), 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(format(itemTemplate.getName()));

        ArrayList<String> lores = new ArrayList<>();
        lores.add("");
        lores.add("&4Health&f: &4" + itemTemplate.getHealth());
        lores.add("&7Lv. Min: " + itemTemplate.getMinLvl());
        lores.add("");

        //Attributes
        if (itemTemplate.getXpBonus() != 0)
            lores.add("&7Xp Bonus: &a" + itemTemplate.getXpBonus() + "%");
        if (itemTemplate.getLootBonus() != 0)
            lores.add("&7Loot Bonus: &a" + itemTemplate.getLootBonus() + "%");
        if (itemTemplate.getAttackBonus() != 0)
            lores.add("&7Attack Bonus: &a" + itemTemplate.getAttackBonus() + "%");
        if (itemTemplate.getLifesteal() != 0)
            lores.add("&7Lifesteal: &a" + itemTemplate.getLifesteal());

        if (!lores.get(lores.size()-1).equals("")) lores.add("");

        if (itemTemplate.getLores().size() > 0) for (String lore : itemTemplate.getLores()) {
            lores.add(lore);
        }

        if (!lores.get(lores.size()-1).equals("")) lores.add("");

        if (itemTemplate.getRarity() == 0) lores.add("&7[✫✫✫]");
        if (itemTemplate.getRarity() == 1) lores.add("&6[&e✫&7✫✫&6]");
        if (itemTemplate.getRarity() == 2) lores.add("&5[&d✫✫&7✫&5]");
        if (itemTemplate.getRarity() == 3) lores.add("&3[&b✫✫✫&3]");

        if (!lores.get(lores.size()-1).equals("")) lores.add("");

        lores.add("&9Attributes:");
        lores.add(" &7- &fValue: " + itemTemplate.getValue());

        meta.setLore(formatList(lores));

        item.setItemMeta(meta);

        NBTItem nbti = new NBTItem(item);
        nbti.setInteger("value", itemTemplate.getValue());
        nbti.setInteger("health", itemTemplate.getHealth());
        nbti.setInteger("minlvl", itemTemplate.getMinLvl());
        nbti.setInteger("xpbonus", itemTemplate.getXpBonus());
        nbti.setInteger("lootbonus", itemTemplate.getLootBonus());
        nbti.setInteger("attackbonus", itemTemplate.getAttackBonus());
        nbti.setInteger("lifesteal", itemTemplate.getLifesteal());
        nbti.applyNBT(item);

        return item;
    }

    private ItemStack generatePotionItem(Potion itemTemplate) {
        ItemStack item = new ItemStack(itemTemplate.getItemType(), 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(format(itemTemplate.getName()));

        ArrayList<String> lores = new ArrayList<>();
        lores.add("");
        lores.add("&4Health&f: &4" + itemTemplate.getHealth());
        lores.add("&7Lv. Min: " + itemTemplate.getMinLvl());
        lores.add("");

        //Attributes
        if (itemTemplate.getXpBonus() != 0)
            lores.add("&7Xp Bonus: &a" + itemTemplate.getXpBonus() + "%");
        if (itemTemplate.getLootBonus() != 0)
            lores.add("&7Loot Bonus: &a" + itemTemplate.getLootBonus() + "%");
        if (itemTemplate.getAttackBonus() != 0)
            lores.add("&7Attack Bonus: &a" + itemTemplate.getAttackBonus() + "%");
        if (itemTemplate.getLifesteal() != 0)
            lores.add("&7Lifesteal: &a" + itemTemplate.getLifesteal());

        if (!lores.get(lores.size()-1).equals("")) lores.add("");

        if (itemTemplate.getLores().size() > 0) for (String lore : itemTemplate.getLores()) {
            lores.add(lore);
        }

        if (!lores.get(lores.size()-1).equals("")) lores.add("");

        if (itemTemplate.getRarity() == 0) lores.add("&7[✫✫✫]");
        if (itemTemplate.getRarity() == 1) lores.add("&6[&e✫&7✫✫&6]");
        if (itemTemplate.getRarity() == 2) lores.add("&5[&d✫✫&7✫&5]");
        if (itemTemplate.getRarity() == 3) lores.add("&3[&b✫✫✫&3]");

        if (!lores.get(lores.size()-1).equals("")) lores.add("");

        lores.add("&9Attributes:");
        lores.add(" &7- &fValue: " + itemTemplate.getValue());

        meta.setLore(formatList(lores));

        item.setItemMeta(meta);

        NBTItem nbti = new NBTItem(item);
        nbti.setInteger("value", itemTemplate.getValue());
        nbti.setInteger("health", itemTemplate.getHealth());
        nbti.setInteger("minlvl", itemTemplate.getMinLvl());
        nbti.setInteger("xpbonus", itemTemplate.getXpBonus());
        nbti.setInteger("lootbonus", itemTemplate.getLootBonus());
        nbti.setInteger("attackbonus", itemTemplate.getAttackBonus());
        nbti.setInteger("lifesteal", itemTemplate.getLifesteal());
        nbti.applyNBT(item);

        return item;
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

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent, Inventory inv) {
        if (inv.equals(categorySelectionInv) || !(inv.getViewers().size() > 0))
            ent.openInventory(inv);
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

            Player p = (Player) e.getWhoClicked();

            //executes when you click the loot category
            if (clickedItem.isSimilar(lootCategoryItem)) {
                openInventory(p, lootItemListInv);
            }

            //executes when you click the weapon category
            else if (clickedItem.isSimilar(weaponCategoryItem)) {
                openInventory(p, weaponItemListInv);
            }

            //executes when you click the talisman category
            else if (clickedItem.isSimilar(talismanCategoryItem)) {
                openInventory(p, talismanItemListInv);
            }

            //executes when you click the potion category
            else if (clickedItem.isSimilar(potionCategoryItem)) {
                openInventory(p, potionListInv);
            }
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