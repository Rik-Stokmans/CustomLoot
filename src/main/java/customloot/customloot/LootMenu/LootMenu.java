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
import org.bukkit.event.inventory.InventoryCloseEvent;
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
    public static Inventory potionItemListInv;
    boolean lootItemListInEditState;
    boolean weaponItemListInEditState;
    boolean talismanItemListInEditState;
    boolean potionItemListInEditState;
    int lootItemsListPage = 0;
    int weaponItemsListPage = 0;
    int talismanItemsListPage = 0;
    int potionItemsListPage = 0;


    ItemStack lootCategoryItem;
    ItemStack weaponCategoryItem;
    ItemStack talismanCategoryItem;
    ItemStack potionCategoryItem;

    //ui/ux
    ItemStack categoryAlreadyInUseItem;
    ItemStack backButton;
    ItemStack previousPageButton;
    ItemStack nextPageButton;
    ItemStack addItemButton;
    ItemStack removeItemButton;
    ItemStack darkDivider;
    ItemStack lightDivider;

    public LootMenu() {
        // Create a new inventory
        categorySelectionInv = Bukkit.createInventory(null, 27, format("&eCategories"));

        lootItemListInv = Bukkit.createInventory(null, 54, format("&eLoot Items"));
        weaponItemListInv = Bukkit.createInventory(null, 54, format("&cWeapons"));
        talismanItemListInv = Bukkit.createInventory(null, 54, format("&aTalismans"));
        potionItemListInv = Bukkit.createInventory(null, 54, format("&bPotions"));

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

        ArrayList<String> categoryAlreadyInUseLore = new ArrayList<>();
        categoryAlreadyInUseLore.add(format("&7This category is already in use"));
        categoryAlreadyInUseItem = createGuiItem("&cCategory Closed", categoryAlreadyInUseLore, Material.BARRIER);

        ArrayList<String> backButtonLore = new ArrayList<>();
        backButtonLore.add(format("&7Go back to the previous menu"));
        backButton = createGuiItem("&cBack", backButtonLore, Material.BARRIER);

        ArrayList<String> previousPageButtonLore = new ArrayList<>();
        previousPageButtonLore.add(format("&7Go to the previous page"));
        previousPageButton = createGuiItem("&ePrevious Page", previousPageButtonLore, Material.PAPER);

        ArrayList<String> nextPageButtonLore = new ArrayList<>();
        nextPageButtonLore.add(format("&7Go to the next page"));
        nextPageButton = createGuiItem("&eNext Page", backButtonLore, Material.PAPER);

        ArrayList<String> addItemButtonLore = new ArrayList<>();
        addItemButtonLore.add(format("&7Add a custom item"));
        addItemButton = createGuiItem("&aAdd Item", addItemButtonLore, Material.LIME_SHULKER_BOX);

        ArrayList<String> removeItemButtonLore = new ArrayList<>();
        removeItemButtonLore.add(format("&7Remove a custom item"));
        removeItemButton = createGuiItem("&cRemove Item", removeItemButtonLore, Material.RED_SHULKER_BOX);

        darkDivider = createGuiItem(" ", new ArrayList<>(), Material.BLACK_STAINED_GLASS_PANE);
        lightDivider = createGuiItem(" ", new ArrayList<>(), Material.GRAY_STAINED_GLASS_PANE);

        categorySelectionInv.setItem(10, lootCategoryItem);
        categorySelectionInv.setItem(12, weaponCategoryItem);
        categorySelectionInv.setItem(14, talismanCategoryItem);
        categorySelectionInv.setItem(16, potionCategoryItem);

        reloadLootMenu();

        reloadWeaponMenu();

        reloadTalismanMenu();

        reloadPotionMenu();
    }

    private void reloadLootMenu() {
        int itemIterator = 0;
        for (Map.Entry<String, Loot> value : lootItems.entrySet()) {
            if (itemIterator <= 35) {
                lootItemListInv.setItem(itemIterator, generateLootItem(value.getValue()));
                itemIterator++;
            }
        }
        for (int i = 36; i <= 44; i++) lootItemListInv.setItem(i, darkDivider);
        for (int i = 45; i <= 53; i++) lootItemListInv.setItem(i, lightDivider);

        lootItemListInv.setItem(45, backButton);
        lootItemListInv.setItem(47, previousPageButton);
        lootItemListInv.setItem(49, nextPageButton);
        lootItemListInv.setItem(51, addItemButton);
        lootItemListInv.setItem(53, removeItemButton);
        lootItemListInEditState = false;
    }

    private void reloadWeaponMenu() {
        int itemIterator = 0;
        for (Map.Entry<String, Weapon> value : weaponItems.entrySet()) {
            if (itemIterator <= 35) {
                weaponItemListInv.setItem(itemIterator, generateWeaponItem(value.getValue()));
                itemIterator++;
            }
        }
        for (int i = 36; i <= 44; i++) weaponItemListInv.setItem(i, darkDivider);
        for (int i = 45; i <= 53; i++) weaponItemListInv.setItem(i, lightDivider);

        weaponItemListInv.setItem(45, backButton);
        weaponItemListInv.setItem(47, previousPageButton);
        weaponItemListInv.setItem(49, nextPageButton);
        weaponItemListInv.setItem(51, addItemButton);
        weaponItemListInv.setItem(53, removeItemButton);
        weaponItemListInEditState = false;
    }

    private void reloadTalismanMenu() {
        int itemIterator = 0;
        for (Map.Entry<String, Talisman> value : talismanItems.entrySet()) {
            if (itemIterator <= 35) {
                talismanItemListInv.setItem(itemIterator, generateTalismanItem(value.getValue()));
                itemIterator++;
            }
        }
        for (int i = 36; i <= 44; i++) talismanItemListInv.setItem(i, darkDivider);
        for (int i = 45; i <= 53; i++) talismanItemListInv.setItem(i, lightDivider);

        talismanItemListInv.setItem(45, backButton);
        talismanItemListInv.setItem(47, previousPageButton);
        talismanItemListInv.setItem(49, nextPageButton);
        talismanItemListInv.setItem(51, addItemButton);
        talismanItemListInv.setItem(53, removeItemButton);
        talismanItemListInEditState = false;
    }

    private void reloadPotionMenu() {
        int itemIterator = 0;
        for (Map.Entry<String, Potion> value : potionItems.entrySet()) {
            if (itemIterator <= 35) {
                potionItemListInv.setItem(itemIterator, generatePotionItem(value.getValue()));
                itemIterator++;
            }
        }
        for (int i = 36; i <= 44; i++) potionItemListInv.setItem(i, darkDivider);
        for (int i = 45; i <= 53; i++) potionItemListInv.setItem(i, lightDivider);

        potionItemListInv.setItem(45, backButton);
        potionItemListInv.setItem(47, previousPageButton);
        potionItemListInv.setItem(49, nextPageButton);
        potionItemListInv.setItem(51, addItemButton);
        potionItemListInv.setItem(53, removeItemButton);
        potionItemListInEditState = false;
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
        nbti.setString("identifier", itemTemplate.getIdentifier());
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
        nbti.setString("identifier", itemTemplate.getIdentifier());
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
        nbti.setString("identifier", itemTemplate.getIdentifier());
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
        nbti.setString("identifier", itemTemplate.getIdentifier());
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
        if (inv.equals(categorySelectionInv) || !(inv.getViewers().size() > 0)) {
            ent.openInventory(inv);
            if (inv.equals(lootItemListInv)) {
                categorySelectionInv.setItem(10, categoryAlreadyInUseItem);
            }
            else if (inv.equals(weaponItemListInv)) {
                categorySelectionInv.setItem(12, categoryAlreadyInUseItem);
            }
            else if (inv.equals(talismanItemListInv)) {
                categorySelectionInv.setItem(14, categoryAlreadyInUseItem);
            }
            else if (inv.equals(potionItemListInv)) {
                categorySelectionInv.setItem(16, categoryAlreadyInUseItem);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();
        if (inv.equals(lootItemListInv)) {
            categorySelectionInv.setItem(10, lootCategoryItem);
        }
        else if (inv.equals(weaponItemListInv)) {
            categorySelectionInv.setItem(12, weaponCategoryItem);
        }
        else if (inv.equals(talismanItemListInv)) {
            categorySelectionInv.setItem(14, talismanCategoryItem);
        }
        else if (inv.equals(potionItemListInv)) {
            categorySelectionInv.setItem(16, potionCategoryItem);
        }
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        Inventory currentInv = e.getInventory();
        if (currentInv.equals(categorySelectionInv) ||
            currentInv.equals(lootItemListInv) ||
            currentInv.equals(weaponItemListInv) ||
            currentInv.equals(talismanItemListInv) ||
            currentInv.equals(potionItemListInv)) {

            e.setCancelled(true);

            int clickedSlot = e.getSlot();

            final ItemStack clickedItem = e.getCurrentItem();

            // verify current item is not null
            if (clickedItem == null || clickedItem.getType().isAir() || clickedItem.isSimilar(darkDivider) || clickedItem.isSimilar(lightDivider)) return;

            Player p = (Player) e.getWhoClicked();

            //triggers
            if (clickedItem.isSimilar(lootCategoryItem)) openInventory(p, lootItemListInv);

            else if (clickedItem.isSimilar(weaponCategoryItem)) openInventory(p, weaponItemListInv);

            else if (clickedItem.isSimilar(talismanCategoryItem)) openInventory(p, talismanItemListInv);

            else if (clickedItem.isSimilar(potionCategoryItem)) openInventory(p, potionItemListInv);

            if (currentInv.equals(lootItemListInv)) {
                if (clickedSlot <= 35) enableEditModeLootInv(clickedItem);

                if (clickedItem.equals(backButton)) {
                    if (lootItemListInEditState) {
                        reloadLootMenu();
                    } else {
                        openInventory(p, categorySelectionInv);
                    }
                }
            }
            if (currentInv.equals(weaponItemListInv)) {
                if (clickedSlot <= 35) enableEditModeWeaponInv(clickedItem);

                if (clickedItem.equals(backButton)) {
                    if (weaponItemListInEditState) {
                        reloadWeaponMenu();
                    } else {
                        openInventory(p, categorySelectionInv);
                    }
                }
            }
            if (currentInv.equals(talismanItemListInv)) {
                if (clickedSlot <= 35) enableEditModeTalismanInv(clickedItem);

                if (clickedItem.equals(backButton)) {
                    if (talismanItemListInEditState) {
                        reloadTalismanMenu();
                    } else {
                        openInventory(p, categorySelectionInv);
                    }
                }
            }
            if (currentInv.equals(potionItemListInv)) {
                if (clickedSlot <= 35) enableEditModePotionInv(clickedItem);

                if (clickedItem.equals(backButton)) {
                    if (potionItemListInEditState) {
                        reloadPotionMenu();
                    } else {
                        openInventory(p, categorySelectionInv);
                    }
                }
            }

        }
    }

    //todo
    private void enableEditModeLootInv(ItemStack item) {
        NBTItem nbti = new NBTItem(item);
        lootItemListInEditState = true;
        Loot lootItem = lootItems.get(nbti.getString("identifier"));

        lootItem.setName("ifthisworksthatwouldbeamazing");
    }
    //todo
    private void enableEditModeWeaponInv(ItemStack item) {
        weaponItemListInEditState = true;

    }
    //todo
    private void enableEditModeTalismanInv(ItemStack item) {
        talismanItemListInEditState = true;

    }
    //todo
    private void enableEditModePotionInv(ItemStack item) {
        potionItemListInEditState = true;

    }


    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        Inventory currentInv = e.getInventory();
        if (currentInv.equals(categorySelectionInv) ||
            currentInv.equals(lootItemListInv) ||
            currentInv.equals(weaponItemListInv) ||
            currentInv.equals(talismanItemListInv) ||
            currentInv.equals(potionItemListInv)) {
            e.setCancelled(true);
        }
    }
}