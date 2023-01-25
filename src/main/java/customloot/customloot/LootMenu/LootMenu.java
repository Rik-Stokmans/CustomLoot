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
import org.bukkit.event.player.PlayerChatEvent;
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
    boolean lootItemListInEditState = false;
    boolean weaponItemListInEditState = false;
    boolean talismanItemListInEditState = false;
    boolean potionItemListInEditState = false;
    int lootItemsListPage = 0;
    int weaponItemsListPage = 0;
    int talismanItemsListPage = 0;
    int potionItemsListPage = 0;

    String lootInvEditItemIdentifier;
    String weaponInvEditItemIdentifier;
    String talismanInvEditItemIdentifier;
    String potionInvEditItemIdentifier;

    public static Player lootInvEditor;
    public static String expectedInputTypeLoot = "NONE";
    public static Player weaponInvEditor;
    public static String expectedInputTypeWeapon = "NONE";
    public static Player talismanInvEditor;
    public static String expectedInputTypeTalisman = "NONE";
    public static Player potionInvEditor;
    public static String expectedInputTypePotion = "NONE";

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
    ItemStack editNameButton;
    ItemStack editLoreButton;
    ItemStack editValueButton;
    ItemStack editDamageButton;
    ItemStack editHealthButton;
    ItemStack editMinLvlButton;
    ItemStack editAttributeButton;

    //constructor that generates the lootMenu
    public LootMenu() {
        //creates all the inventories in the lootMenu
        categorySelectionInv = Bukkit.createInventory(null, 27, format("&eCategories"));
        lootItemListInv = Bukkit.createInventory(null, 54, format("&eLoot Items"));
        weaponItemListInv = Bukkit.createInventory(null, 54, format("&cWeapons"));
        talismanItemListInv = Bukkit.createInventory(null, 54, format("&aTalismans"));
        potionItemListInv = Bukkit.createInventory(null, 54, format("&bPotions"));

        //initializes all the items in the inventories
        initializeItems();
    }

    //initializes all the items and adds them to the inventories
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

        ArrayList<String> editNameButtonLore = new ArrayList<>();
        editNameButtonLore.add(format("&7Edit the name of this item"));
        editNameButton = createGuiItem("&eEdit Name", editNameButtonLore, Material.NAME_TAG);

        ArrayList<String> editLoreButtonLore = new ArrayList<>();
        editLoreButtonLore.add(format("&7Edit the lore of this item"));
        editLoreButton = createGuiItem("&eEdit Lore", editLoreButtonLore, Material.OAK_SIGN);

        ArrayList<String> editValueButtonLore = new ArrayList<>();
        editValueButtonLore.add(format("&7Edit the value of this item"));
        editValueButton = createGuiItem("&eEdit Value", editValueButtonLore, Material.RAW_GOLD);

        ArrayList<String> editDamageButtonLore = new ArrayList<>();
        editDamageButtonLore.add(format("&7Edit the damage the item deals"));
        editDamageButton = createGuiItem("&eEdit Damage", editDamageButtonLore, Material.RED_DYE);

        ArrayList<String> editHealthButtonLore = new ArrayList<>();
        editHealthButtonLore.add(format("&7Edit the health amount of this item"));
        editHealthButton = createGuiItem("&eEdit health", editHealthButtonLore, Material.RED_DYE);

        ArrayList<String> editMinLvlButtonLore = new ArrayList<>();
        editMinLvlButtonLore.add(format("&7Edit the minimal level of this item"));
        editMinLvlButton = createGuiItem("&eEdit Min. Lvl", editMinLvlButtonLore, Material.EXPERIENCE_BOTTLE);

        ArrayList<String> editAttributeButtonLore = new ArrayList<>();
        editAttributeButtonLore.add(format("&7Edit the attributes of this item"));
        editAttributeButton = createGuiItem("&eEdit Attributes", editAttributeButtonLore, Material.BOOK);

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

    //methods to reload the lootMenu
    private void reloadLootMenu() {
        lootItemListInv.clear();
        if (!lootItemListInEditState) {
            int pageStartIndex = lootItemsListPage * 36;
            int pageEndIndex = pageStartIndex + 35;
            int valueIndex = 0;
            int itemIterator = 0;
            for (Map.Entry<String, Loot> value : lootItems.entrySet()) {
                if (itemIterator <= 35) {
                    if (valueIndex >= pageStartIndex && valueIndex <= pageEndIndex) {
                        lootItemListInv.setItem(itemIterator, generateLootItem(value.getValue()));
                        itemIterator++;
                    }
                }
                valueIndex++;
            }
            for (int i = 36; i <= 44; i++) lootItemListInv.setItem(i, darkDivider);
            for (int i = 45; i <= 53; i++) lootItemListInv.setItem(i, lightDivider);

            lootItemListInv.setItem(45, backButton);
            lootItemListInv.setItem(47, previousPageButton);
            lootItemListInv.setItem(49, nextPageButton);
            lootItemListInv.setItem(51, addItemButton);
            lootItemListInv.setItem(53, removeItemButton);
        } else {

            for (int i = 36; i <= 44; i++) lootItemListInv.setItem(i, darkDivider);
            for (int i = 45; i <= 53; i++) lootItemListInv.setItem(i, lightDivider);

            lootItemListInv.setItem(20, editNameButton);
            lootItemListInv.setItem(22, editLoreButton);
            lootItemListInv.setItem(24, editValueButton);

            lootItemListInv.setItem(45, backButton);
            lootItemListInv.setItem(49, generateLootItem(lootItems.get(lootInvEditItemIdentifier)));
        }
    }
    private void reloadWeaponMenu() {
        weaponItemListInv.clear();
        if (!weaponItemListInEditState) {
            int pageStartIndex = weaponItemsListPage * 36;
            int pageEndIndex = pageStartIndex + 35;
            int valueIndex = 0;
            int itemIterator = 0;
            for (Map.Entry<String, Weapon> value : weaponItems.entrySet()) {
                if (itemIterator <= 35) {
                    if (valueIndex >= pageStartIndex && valueIndex <= pageEndIndex) {
                        weaponItemListInv.setItem(itemIterator, generateWeaponItem(value.getValue()));
                        itemIterator++;
                    }
                }
                valueIndex++;
            }
            for (int i = 36; i <= 44; i++) weaponItemListInv.setItem(i, darkDivider);
            for (int i = 45; i <= 53; i++) weaponItemListInv.setItem(i, lightDivider);

            weaponItemListInv.setItem(45, backButton);
            weaponItemListInv.setItem(47, previousPageButton);
            weaponItemListInv.setItem(49, nextPageButton);
            weaponItemListInv.setItem(51, addItemButton);
            weaponItemListInv.setItem(53, removeItemButton);
        } else {

            for (int i = 36; i <= 44; i++) weaponItemListInv.setItem(i, darkDivider);
            for (int i = 45; i <= 53; i++) weaponItemListInv.setItem(i, lightDivider);

            weaponItemListInv.setItem(11, editNameButton);
            weaponItemListInv.setItem(13, editLoreButton);
            weaponItemListInv.setItem(15, editValueButton);

            weaponItemListInv.setItem(29, editDamageButton);
            weaponItemListInv.setItem(31, editMinLvlButton);
            weaponItemListInv.setItem(33, editAttributeButton);

            weaponItemListInv.setItem(45, backButton);
            weaponItemListInv.setItem(49, generateWeaponItem(weaponItems.get(weaponInvEditItemIdentifier)));
        }
    }
    private void reloadTalismanMenu() {
        talismanItemListInv.clear();
        if (!talismanItemListInEditState) {
            int pageStartIndex = talismanItemsListPage * 36;
            int pageEndIndex = pageStartIndex + 35;
            int valueIndex = 0;
            int itemIterator = 0;
            for (Map.Entry<String, Talisman> value : talismanItems.entrySet()) {
                if (itemIterator <= 35) {
                    if (valueIndex >= pageStartIndex && valueIndex <= pageEndIndex) {
                        talismanItemListInv.setItem(itemIterator, generateTalismanItem(value.getValue()));
                        itemIterator++;
                    }
                }
                valueIndex++;
            }
            for (int i = 36; i <= 44; i++) talismanItemListInv.setItem(i, darkDivider);
            for (int i = 45; i <= 53; i++) talismanItemListInv.setItem(i, lightDivider);

            talismanItemListInv.setItem(45, backButton);
            talismanItemListInv.setItem(47, previousPageButton);
            talismanItemListInv.setItem(49, nextPageButton);
            talismanItemListInv.setItem(51, addItemButton);
            talismanItemListInv.setItem(53, removeItemButton);
        } else {

            for (int i = 36; i <= 44; i++) talismanItemListInv.setItem(i, darkDivider);
            for (int i = 45; i <= 53; i++) talismanItemListInv.setItem(i, lightDivider);

            talismanItemListInv.setItem(11, editNameButton);
            talismanItemListInv.setItem(13, editLoreButton);
            talismanItemListInv.setItem(15, editValueButton);

            talismanItemListInv.setItem(29, editHealthButton);
            talismanItemListInv.setItem(31, editMinLvlButton);
            talismanItemListInv.setItem(33, editAttributeButton);

            talismanItemListInv.setItem(45, backButton);
            talismanItemListInv.setItem(49, generateTalismanItem(talismanItems.get(talismanInvEditItemIdentifier)));
        }
    }
    private void reloadPotionMenu() {
        potionItemListInv.clear();
        if (!potionItemListInEditState) {
            int pageStartIndex = potionItemsListPage * 36;
            int pageEndIndex = pageStartIndex + 35;
            int valueIndex = 0;
            int itemIterator = 0;
            for (Map.Entry<String, Potion> value : potionItems.entrySet()) {
                if (itemIterator <= 35) {
                    if (valueIndex >= pageStartIndex && valueIndex <= pageEndIndex) {
                        potionItemListInv.setItem(itemIterator, generatePotionItem(value.getValue()));
                        itemIterator++;
                    }
                }
                valueIndex++;
            }
            for (int i = 36; i <= 44; i++) potionItemListInv.setItem(i, darkDivider);
            for (int i = 45; i <= 53; i++) potionItemListInv.setItem(i, lightDivider);

            potionItemListInv.setItem(45, backButton);
            potionItemListInv.setItem(47, previousPageButton);
            potionItemListInv.setItem(49, nextPageButton);
            potionItemListInv.setItem(51, addItemButton);
            potionItemListInv.setItem(53, removeItemButton);
        } else {

            for (int i = 36; i <= 44; i++) potionItemListInv.setItem(i, darkDivider);
            for (int i = 45; i <= 53; i++) potionItemListInv.setItem(i, lightDivider);

            potionItemListInv.setItem(11, editNameButton);
            potionItemListInv.setItem(13, editLoreButton);
            potionItemListInv.setItem(15, editValueButton);

            potionItemListInv.setItem(29, editHealthButton);
            potionItemListInv.setItem(31, editMinLvlButton);
            potionItemListInv.setItem(33, editAttributeButton);

            potionItemListInv.setItem(45, backButton);
            potionItemListInv.setItem(49, generatePotionItem(potionItems.get(potionInvEditItemIdentifier)));
        }
    }

    //methods to generate custom items for the gui
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
        lores.add("&cDamage: &c" + itemTemplate.getDamage());
        lores.add("&eLv. Min: " + itemTemplate.getMinLvl());
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
        lores.add("&4Health +&4" + itemTemplate.getHealth());
        lores.add("&eLv. Min: " + itemTemplate.getMinLvl());
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
        lores.add("&4Health +&4" + itemTemplate.getHealth());
        lores.add("&eLv. Min: " + itemTemplate.getMinLvl());
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

    //constructs the item and returns it with lore and custom nbt
    protected ItemStack createGuiItem(String name, ArrayList<String> lore, Material itemType) {
        final ItemStack item = new ItemStack(itemType, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(format(name));
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    //opens the lootMenu to the given player
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

    //keeps track on when the loot menu is closed
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();
        if (inv.equals(lootItemListInv) && !lootItemListInEditState) {
            categorySelectionInv.setItem(10, lootCategoryItem);
        }
        else if (inv.equals(weaponItemListInv) && !weaponItemListInEditState) {
            categorySelectionInv.setItem(12, weaponCategoryItem);
        }
        else if (inv.equals(talismanItemListInv) && !talismanItemListInEditState) {
            categorySelectionInv.setItem(14, talismanCategoryItem);
        }
        else if (inv.equals(potionItemListInv) && !potionItemListInEditState) {
            categorySelectionInv.setItem(16, potionCategoryItem);
        }
    }

    //handles the clicks in the lootMenu
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
                if (!lootItemListInEditState) {
                    int maxPageNumber = (int) Math.ceil(lootItems.size() / 36);
                    if (clickedSlot <= 35) enableEditModeLootInv(clickedItem, p);

                    if (clickedItem.equals(backButton)) {
                        if (lootItemListInEditState) {
                            lootItemListInEditState = false;
                            reloadLootMenu();
                        } else {
                            openInventory(p, categorySelectionInv);
                            lootInvEditor = null;
                        }
                    }
                    else if (clickedItem.isSimilar(previousPageButton)) {
                        if (lootItemsListPage > 0) {
                            lootItemsListPage--;
                            reloadLootMenu();
                        }
                    }
                    else if (clickedItem.isSimilar(nextPageButton)) {
                        if (lootItemsListPage < maxPageNumber) {
                            lootItemsListPage++;
                            reloadLootMenu();
                        }
                    }
                    Bukkit.broadcastMessage(String.valueOf(lootItemsListPage));
                }  else {
                    if (!clickedItem.isSimilar(backButton)) {
                        handleLootItemEdit(clickedItem);
                    } else {
                        lootItemListInEditState = false;
                        openInventory(p, lootItemListInv);
                        reloadLootMenu();
                    }
                }
            }

            if (currentInv.equals(weaponItemListInv)) {
                if (!weaponItemListInEditState) {
                    int maxPageNumber = (int) Math.ceil(weaponItems.size()/36);
                    if (clickedSlot <= 35) enableEditModeWeaponInv(clickedItem, p);

                    if (clickedItem.equals(backButton)) {
                        if (weaponItemListInEditState) {
                            weaponItemListInEditState = false;
                            reloadWeaponMenu();
                        } else {
                            openInventory(p, categorySelectionInv);
                            weaponInvEditor = null;
                        }
                    }
                    else if (clickedItem.isSimilar(previousPageButton)) {
                        if (weaponItemsListPage > 0) {
                            weaponItemsListPage--;
                            reloadWeaponMenu();
                        }
                    }
                    else if (clickedItem.isSimilar(nextPageButton)) {
                        if (weaponItemsListPage < maxPageNumber) {
                            weaponItemsListPage++;
                            reloadWeaponMenu();
                        }
                    }
                    Bukkit.broadcastMessage(String.valueOf(weaponItemsListPage));
                } else {
                    if (!clickedItem.isSimilar(backButton)) {
                        handleWeaponItemEdit(clickedItem);
                    } else {
                        weaponItemListInEditState = false;
                        openInventory(p, weaponItemListInv);
                        reloadWeaponMenu();
                    }
                }
            }

            if (currentInv.equals(talismanItemListInv)) {
                if (!talismanItemListInEditState) {
                    int maxPageNumber = (int) Math.ceil(talismanItems.size()/36);
                    if (clickedSlot <= 35) enableEditModeTalismanInv(clickedItem, p);

                    if (clickedItem.equals(backButton)) {
                        if (talismanItemListInEditState) {
                            talismanItemListInEditState = false;
                            reloadTalismanMenu();
                        } else {
                            openInventory(p, categorySelectionInv);
                            talismanInvEditor = null;
                        }
                    }
                    else if (clickedItem.isSimilar(previousPageButton)) {
                        if (talismanItemsListPage > 0) {
                            talismanItemsListPage--;
                            reloadTalismanMenu();
                        }
                    }
                    else if (clickedItem.isSimilar(nextPageButton)) {
                        if (talismanItemsListPage < maxPageNumber) {
                            talismanItemsListPage++;
                            reloadTalismanMenu();
                        }
                    }
                    Bukkit.broadcastMessage(String.valueOf(talismanItemsListPage));
                } else {
                    if (!clickedItem.isSimilar(backButton)) {
                        handleTalismanItemEdit(clickedItem);
                    } else {
                        talismanItemListInEditState = false;
                        openInventory(p, talismanItemListInv);
                        reloadTalismanMenu();
                    }
                }
            }

            if (currentInv.equals(potionItemListInv)) {
                if (!potionItemListInEditState) {
                    int maxPageNumber = (int) Math.ceil(potionItems.size()/36);
                    if (clickedSlot <= 35) enableEditModePotionInv(clickedItem, p);

                    if (clickedItem.equals(backButton)) {
                        if (potionItemListInEditState) {
                            potionItemListInEditState = false;
                            reloadPotionMenu();
                        } else {
                            openInventory(p, categorySelectionInv);
                            potionInvEditor = null;
                        }
                    }
                    else if (clickedItem.isSimilar(previousPageButton)) {
                        if (potionItemsListPage > 0) {
                            potionItemsListPage--;
                            reloadPotionMenu();
                        }
                    }
                    else if (clickedItem.isSimilar(nextPageButton)) {
                        if (potionItemsListPage < maxPageNumber) {
                            potionItemsListPage++;
                            reloadPotionMenu();
                        }
                    }
                    Bukkit.broadcastMessage(String.valueOf(talismanItemsListPage));
                } else {
                    if (!clickedItem.isSimilar(backButton)) {
                        handlePotionItemEdit(clickedItem);
                    } else {
                        potionItemListInEditState = false;
                        openInventory(p, potionItemListInv);
                        reloadPotionMenu();
                    }
                }
            }
        }
    }

    //handles for the item edits
    private void handleLootItemEdit(ItemStack clickedItem) {
        if (clickedItem.isSimilar(editNameButton)) {
            expectedInputTypeLoot = "NAME";
            lootInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editLoreButton)) {
            expectedInputTypeLoot = "LORE";
            lootInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editValueButton)) {
            expectedInputTypeLoot = "VALUE";
            lootInvEditor.closeInventory();
        }
    }
    private void handleWeaponItemEdit(ItemStack clickedItem) {
        if (clickedItem.isSimilar(editNameButton)) {
            expectedInputTypeWeapon = "NAME";
            weaponInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editLoreButton)) {
            expectedInputTypeWeapon = "LORE";
            weaponInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editValueButton)) {
            expectedInputTypeWeapon = "VALUE";
            weaponInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editDamageButton)) {
            expectedInputTypeWeapon = "DAMAGE";
            weaponInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editMinLvlButton)) {
            expectedInputTypeWeapon = "MINLVL";
            weaponInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editAttributeButton)) {
            expectedInputTypeWeapon = "ATTRIBUTE";
            weaponInvEditor.closeInventory();
        }
    }
    private void handleTalismanItemEdit(ItemStack clickedItem) {
        if (clickedItem.isSimilar(editNameButton)) {
            expectedInputTypeTalisman = "NAME";
            talismanInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editLoreButton)) {
            expectedInputTypeTalisman = "LORE";
            talismanInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editValueButton)) {
            expectedInputTypeTalisman = "VALUE";
            talismanInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editHealthButton)) {
            expectedInputTypeTalisman = "HEALTH";
            talismanInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editMinLvlButton)) {
            expectedInputTypeTalisman = "MINLVL";
            talismanInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editAttributeButton)) {
            expectedInputTypeTalisman = "ATTRIBUTE";
            talismanInvEditor.closeInventory();
        }
    }
    private void handlePotionItemEdit(ItemStack clickedItem) {
        if (clickedItem.isSimilar(editNameButton)) {
            expectedInputTypePotion = "NAME";
            potionInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editLoreButton)) {
            expectedInputTypePotion = "LORE";
            potionInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editValueButton)) {
            expectedInputTypePotion = "VALUE";
            potionInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editHealthButton)) {
            expectedInputTypePotion = "HEALTH";
            potionInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editMinLvlButton)) {
            expectedInputTypePotion = "MINLVL";
            potionInvEditor.closeInventory();
        }
        else if (clickedItem.isSimilar(editAttributeButton)) {
            expectedInputTypePotion = "ATTRIBUTE";
            potionInvEditor.closeInventory();
        }
    }

    //takes input when you want to edit an item
    @EventHandler
    private void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();
        if (p.equals(lootInvEditor)) {
            e.setCancelled(true);
            String message = e.getMessage();

            if (message.equalsIgnoreCase("cancel")) {
                expectedInputTypeLoot = "NONE";
                openInventory(p, lootItemListInv);
            }
            else if (expectedInputTypeLoot.equals("NAME")) {
                lootItems.get(lootInvEditItemIdentifier).setName(message);
                expectedInputTypeLoot = "NONE";
                openInventory(p, lootItemListInv);
            }
            else if (expectedInputTypeLoot.equals("LORE")) {
                ArrayList<String> lores = lootItems.get(lootInvEditItemIdentifier).getLores();
                if (message.split(":")[0].equals("+")) {
                    lores.add(message.split(":")[1]);
                    lootItems.get(lootInvEditItemIdentifier).setLores(lores);
                    expectedInputTypeLoot = "NONE";
                    openInventory(p, lootItemListInv);
                } else {
                    int loreLine = Integer.parseInt(message.split(":")[0]);
                    if (loreLine > lores.size() - 1) {
                        p.sendMessage(format("&cLore index " + loreLine + " out of bounds"));
                        p.sendMessage(format("&cMax index is " + (lores.size() - 1)));
                    } else {
                        String newLoreLine = message.split(":")[1];
                        if (newLoreLine.equals("-")) lores.set(loreLine, " ");
                        else if (newLoreLine.equals("remove")) lores.remove(loreLine);
                        else {
                            lores.set(loreLine, newLoreLine);
                        }
                        lootItems.get(lootInvEditItemIdentifier).setLores(lores);
                        expectedInputTypeLoot = "NONE";
                        openInventory(p, lootItemListInv);
                    }
                }
            }
            else if (expectedInputTypeLoot.equals("VALUE")) {
                int oldValue = lootItems.get(lootInvEditItemIdentifier).getValue();
                int newValue;
                try {
                    newValue = (Integer.parseInt(message));
                } catch (NumberFormatException exception) {
                    p.sendMessage(format("&7You have to give an integer number"));
                    newValue = oldValue;
                }
                if (newValue != oldValue) {
                    expectedInputTypeLoot = "NONE";
                    openInventory(p, lootItemListInv);
                    lootItems.get(lootInvEditItemIdentifier).setValue(newValue);
                } else {
                    p.sendMessage(format("&cYour given input is identical to the current value"));
                }
            }
            reloadLootMenu();
        }
        else if (e.getPlayer().equals(weaponInvEditor)) {
            e.setCancelled(true);
            String message = e.getMessage();

            if (message.equalsIgnoreCase("cancel")) {
                expectedInputTypeWeapon = "NONE";
                openInventory(p, weaponItemListInv);
            }
            else if (expectedInputTypeWeapon.equals("NAME")) {
                weaponItems.get(weaponInvEditItemIdentifier).setName(message);
                expectedInputTypeWeapon = "NONE";
                openInventory(p, weaponItemListInv);
            }
            else if (expectedInputTypeWeapon.equals("LORE")) {
                ArrayList<String> lores = weaponItems.get(weaponInvEditItemIdentifier).getLores();
                if (message.split(":")[0].equals("+")) {
                    lores.add(message.split(":")[1]);
                    weaponItems.get(weaponInvEditItemIdentifier).setLores(lores);
                    expectedInputTypeWeapon = "NONE";
                    openInventory(p, weaponItemListInv);
                } else {
                    int loreLine = Integer.parseInt(message.split(":")[0]);
                    if (loreLine > lores.size() - 1) {
                        p.sendMessage(format("&cLore index " + loreLine + " out of bounds"));
                        p.sendMessage(format("&cMax index is " + (lores.size() - 1)));
                    } else {
                        String newLoreLine = message.split(":")[1];
                        if (newLoreLine.equals("-")) lores.set(loreLine, " ");
                        else if (newLoreLine.equals("remove")) lores.remove(loreLine);
                        else {
                            lores.set(loreLine, newLoreLine);
                        }
                        weaponItems.get(weaponInvEditItemIdentifier).setLores(lores);
                        expectedInputTypeWeapon = "NONE";
                        openInventory(p, weaponItemListInv);
                    }
                }
            }
            else if (expectedInputTypeWeapon.equals("VALUE")) {
                int oldValue = weaponItems.get(weaponInvEditItemIdentifier).getValue();
                int newValue;
                try {
                    newValue = (Integer.parseInt(message));
                } catch (NumberFormatException exception) {
                    p.sendMessage(format("&7You have to give an integer number"));
                    newValue = oldValue;
                }
                if (newValue != oldValue) {
                    expectedInputTypeWeapon = "NONE";
                    openInventory(p, weaponItemListInv);
                    weaponItems.get(weaponInvEditItemIdentifier).setValue(newValue);
                } else {
                    p.sendMessage(format("&cYour given input is identical to the current value"));
                }
            }
            else if (expectedInputTypeWeapon.equals("DAMAGE")) {
                int oldDamage = weaponItems.get(weaponInvEditItemIdentifier).getDamage();
                int newDamage;
                try {
                    newDamage = (Integer.parseInt(message));
                } catch (NumberFormatException exception) {
                    p.sendMessage(format("&7You have to give an integer number"));
                    newDamage = oldDamage;
                }
                if (newDamage != oldDamage) {
                    expectedInputTypeWeapon = "NONE";
                    openInventory(p, weaponItemListInv);
                    weaponItems.get(weaponInvEditItemIdentifier).setDamage(newDamage);
                } else {
                    p.sendMessage(format("&cYour given input is identical to the current value"));
                }
            }
            else if (expectedInputTypeWeapon.equals("MINLVL")) {
                int oldMinLvl = weaponItems.get(weaponInvEditItemIdentifier).getMinLvl();
                int newMinLvl;
                try {
                    newMinLvl = (Integer.parseInt(message));
                } catch (NumberFormatException exception) {
                    p.sendMessage(format("&7You have to give an integer number"));
                    newMinLvl = oldMinLvl;
                }
                if (newMinLvl != oldMinLvl) {
                    expectedInputTypeWeapon = "NONE";
                    openInventory(p, weaponItemListInv);
                    weaponItems.get(weaponInvEditItemIdentifier).setMinLvl(newMinLvl);
                } else {
                    p.sendMessage(format("&cYour given input is identical to the current value"));
                }
            }
            reloadWeaponMenu();
        }
        else if (e.getPlayer().equals(talismanInvEditor)) {
            e.setCancelled(true);
            String message = e.getMessage();

            if (message.equalsIgnoreCase("cancel")) {
                expectedInputTypeTalisman = "NONE";
                openInventory(p, talismanItemListInv);
            }
            else if (expectedInputTypeTalisman.equals("NAME")) {
                talismanItems.get(talismanInvEditItemIdentifier).setName(message);
                expectedInputTypeTalisman = "NONE";
                openInventory(p, talismanItemListInv);
            }
            else if (expectedInputTypeTalisman.equals("LORE")) {
                ArrayList<String> lores = talismanItems.get(talismanInvEditItemIdentifier).getLores();
                if (message.split(":")[0].equals("+")) {
                    lores.add(message.split(":")[1]);
                    talismanItems.get(talismanInvEditItemIdentifier).setLores(lores);
                    expectedInputTypeTalisman = "NONE";
                    openInventory(p, talismanItemListInv);
                } else {
                    int loreLine = Integer.parseInt(message.split(":")[0]);
                    if (loreLine > lores.size() - 1) {
                        p.sendMessage(format("&cLore index " + loreLine + " out of bounds"));
                        p.sendMessage(format("&cMax index is " + (lores.size() - 1)));
                    } else {
                        String newLoreLine = message.split(":")[1];
                        if (newLoreLine.equals("-")) lores.set(loreLine, " ");
                        else if (newLoreLine.equals("remove")) lores.remove(loreLine);
                        else {
                            lores.set(loreLine, newLoreLine);
                        }
                        talismanItems.get(talismanInvEditItemIdentifier).setLores(lores);
                        expectedInputTypeTalisman = "NONE";
                        openInventory(p, talismanItemListInv);
                    }
                }
            }
            else if (expectedInputTypeTalisman.equals("VALUE")) {
                int oldValue = talismanItems.get(talismanInvEditItemIdentifier).getValue();
                int newValue;
                try {
                    newValue = (Integer.parseInt(message));
                } catch (NumberFormatException exception) {
                    p.sendMessage(format("&7You have to give an integer number"));
                    newValue = oldValue;
                }
                if (newValue != oldValue) {
                    expectedInputTypeTalisman = "NONE";
                    openInventory(p, talismanItemListInv);
                    talismanItems.get(talismanInvEditItemIdentifier).setValue(newValue);
                } else {
                    p.sendMessage(format("&cYour given input is identical to the current value"));
                }
            }
            else if (expectedInputTypeTalisman.equals("HEALTH")) {
                int oldHealth = talismanItems.get(talismanInvEditItemIdentifier).getHealth();
                int newHealth;
                try {
                    newHealth = (Integer.parseInt(message));
                } catch (NumberFormatException exception) {
                    p.sendMessage(format("&7You have to give an integer number"));
                    newHealth = oldHealth;
                }
                if (newHealth != oldHealth) {
                    expectedInputTypeTalisman = "NONE";
                    openInventory(p, talismanItemListInv);
                    talismanItems.get(talismanInvEditItemIdentifier).setHealth(newHealth);
                } else {
                    p.sendMessage(format("&cYour given input is identical to the current value"));
                }
            }
            else if (expectedInputTypeTalisman.equals("MINLVL")) {
                int oldMinLvl = talismanItems.get(talismanInvEditItemIdentifier).getMinLvl();
                int newMinLvl;
                try {
                    newMinLvl = (Integer.parseInt(message));
                } catch (NumberFormatException exception) {
                    p.sendMessage(format("&7You have to give an integer number"));
                    newMinLvl = oldMinLvl;
                }
                if (newMinLvl != oldMinLvl) {
                    expectedInputTypeTalisman = "NONE";
                    openInventory(p, talismanItemListInv);
                    talismanItems.get(talismanInvEditItemIdentifier).setMinLvl(newMinLvl);
                } else {
                    p.sendMessage(format("&cYour given input is identical to the current value"));
                }
            }
            reloadTalismanMenu();
        }
        else if (e.getPlayer().equals(potionInvEditor)) {
            e.setCancelled(true);
            String message = e.getMessage();

            if (message.equalsIgnoreCase("cancel")) {
                expectedInputTypePotion = "NONE";
                openInventory(p, potionItemListInv);
            }
            else if (expectedInputTypePotion.equals("NAME")) {
                potionItems.get(potionInvEditItemIdentifier).setName(message);
                expectedInputTypePotion = "NONE";
                openInventory(p, potionItemListInv);
            }
            else if (expectedInputTypePotion.equals("LORE")) {
                ArrayList<String> lores = potionItems.get(potionInvEditItemIdentifier).getLores();
                if (message.split(":")[0].equals("+")) {
                    lores.add(message.split(":")[1]);
                    potionItems.get(potionInvEditItemIdentifier).setLores(lores);
                    expectedInputTypePotion = "NONE";
                    openInventory(p, potionItemListInv);
                } else {
                    int loreLine = Integer.parseInt(message.split(":")[0]);
                    if (loreLine > lores.size() - 1) {
                        p.sendMessage(format("&cLore index " + loreLine + " out of bounds"));
                        p.sendMessage(format("&cMax index is " + (lores.size() - 1)));
                    } else {
                        String newLoreLine = message.split(":")[1];
                        if (newLoreLine.equals("-")) lores.set(loreLine, " ");
                        else if (newLoreLine.equals("remove")) lores.remove(loreLine);
                        else {
                            lores.set(loreLine, newLoreLine);
                        }
                        potionItems.get(potionInvEditItemIdentifier).setLores(lores);
                        expectedInputTypePotion = "NONE";
                        openInventory(p, potionItemListInv);
                    }
                }
            }
            else if (expectedInputTypePotion.equals("VALUE")) {
                int oldValue = potionItems.get(potionInvEditItemIdentifier).getValue();
                int newValue;
                try {
                    newValue = (Integer.parseInt(message));
                } catch (NumberFormatException exception) {
                    p.sendMessage(format("&7You have to give an integer number"));
                    newValue = oldValue;
                }
                if (newValue != oldValue) {
                    expectedInputTypePotion = "NONE";
                    openInventory(p, potionItemListInv);
                    potionItems.get(potionInvEditItemIdentifier).setValue(newValue);
                } else {
                    p.sendMessage(format("&cYour given input is identical to the current value"));
                }
            }
            else if (expectedInputTypePotion.equals("HEALTH")) {
                int oldHealth = potionItems.get(potionInvEditItemIdentifier).getHealth();
                int newHealth;
                try {
                    newHealth = (Integer.parseInt(message));
                } catch (NumberFormatException exception) {
                    p.sendMessage(format("&7You have to give an integer number"));
                    newHealth = oldHealth;
                }
                if (newHealth != oldHealth) {
                    expectedInputTypePotion = "NONE";
                    openInventory(p, potionItemListInv);
                    potionItems.get(potionInvEditItemIdentifier).setHealth(newHealth);
                } else {
                    p.sendMessage(format("&cYour given input is identical to the current value"));
                }
            }
            else if (expectedInputTypePotion.equals("MINLVL")) {
                int oldMinLvl = potionItems.get(potionInvEditItemIdentifier).getMinLvl();
                int newMinLvl;
                try {
                    newMinLvl = (Integer.parseInt(message));
                } catch (NumberFormatException exception) {
                    p.sendMessage(format("&7You have to give an integer number"));
                    newMinLvl = oldMinLvl;
                }
                if (newMinLvl != oldMinLvl) {
                    expectedInputTypePotion = "NONE";
                    openInventory(p, potionItemListInv);
                    potionItems.get(potionInvEditItemIdentifier).setMinLvl(newMinLvl);
                } else {
                    p.sendMessage(format("&cYour given input is identical to the current value"));
                }
            }
            reloadPotionMenu();
        }
    }

    //enable edit mode functions
    private void enableEditModeLootInv(ItemStack item, Player p) {
        NBTItem nbti = new NBTItem(item);
        lootInvEditor = p;
        lootItemListInEditState = true;
        lootInvEditItemIdentifier = nbti.getString("identifier");

        reloadLootMenu();
    }
    private void enableEditModeWeaponInv(ItemStack item, Player p) {
        NBTItem nbti = new NBTItem(item);
        weaponInvEditor = p;
        weaponItemListInEditState = true;
        weaponInvEditItemIdentifier = nbti.getString("identifier");

        reloadWeaponMenu();
    }
    private void enableEditModeTalismanInv(ItemStack item, Player p) {
        NBTItem nbti = new NBTItem(item);
        talismanInvEditor = p;
        talismanItemListInEditState = true;
        talismanInvEditItemIdentifier = nbti.getString("identifier");
        reloadTalismanMenu();
    }
    private void enableEditModePotionInv(ItemStack item, Player p) {
        NBTItem nbti = new NBTItem(item);
        potionInvEditor = p;
        potionItemListInEditState = true;
        potionInvEditItemIdentifier = nbti.getString("identifier");

        reloadPotionMenu();
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