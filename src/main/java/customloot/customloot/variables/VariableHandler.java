package customloot.customloot.variables;

import customloot.customloot.Loot.Loot;
import customloot.customloot.Loot.Potion;
import customloot.customloot.Loot.Talisman;
import customloot.customloot.Loot.Weapon;
import javaslang.Tuple4;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VariableHandler {

    public static HashMap<String, Loot> lootItems = new HashMap<>();
    public static HashMap<String, Weapon> weaponItems = new HashMap<>();
    public static HashMap<String, Talisman> talismanItems = new HashMap<>();
    public static HashMap<String, Potion> potionItems = new HashMap<>();

    public static void saveVariables(String path) {

        File testConfigFile = new File(path, "Variables/lootItems.yml");
        testConfigFile.delete();

        YamlConfiguration itemsFile = YamlConfiguration.loadConfiguration(new File(path, "Variables/lootItems.yml"));

        //saving the loot items
        if (!lootItems.isEmpty()) {
            for (Map.Entry<String, Loot> value : lootItems.entrySet()) {

                itemsFile.set("lootitems." + value.getKey() + ".name", value.getValue().getName());

                itemsFile.set("lootitems." + value.getKey() + ".rarity", value.getValue().getRarity());

                itemsFile.set("lootitems." + value.getKey() + ".value", value.getValue().getValue());

                itemsFile.set("lootitems." + value.getKey() + ".item-type", value.getValue().getItemType().name());

                ArrayList<String> lores = value.getValue().getLores();
                if (lores.size() > 0) for (int i = 0; i < lores.size(); i++) {
                    itemsFile.set("lootitems." + value.getKey() + ".lores." + i, lores.get(i));
                }
            }
        }

        //saving the weapon items
        if (!weaponItems.isEmpty()) {
            for (Map.Entry<String, Weapon> value : weaponItems.entrySet()) {
                itemsFile.set("weapons." + value.getKey() + ".name", value.getValue().getName());

                itemsFile.set("weapons." + value.getKey() + ".rarity", value.getValue().getRarity());

                itemsFile.set("weapons." + value.getKey() + ".value", value.getValue().getValue());

                itemsFile.set("weapons." + value.getKey() + ".item-type", value.getValue().getItemType().name());

                itemsFile.set("weapons." + value.getKey() + ".damage", value.getValue().getDamage());

                itemsFile.set("weapons." + value.getKey() + ".minlvl", value.getValue().getMinLvl());

                //attributes
                if (value.getValue().getXpBonus() != 0)
                    itemsFile.set("weapons." + value.getKey() + ".attributes.xpbonus", value.getValue().getXpBonus());
                if (value.getValue().getLootBonus() != 0)
                    itemsFile.set("weapons." + value.getKey() + ".attributes.lootbonus", value.getValue().getLootBonus());
                if (value.getValue().getAttackBonus() != 0)
                    itemsFile.set("weapons." + value.getKey() + ".attributes.attackbonus", value.getValue().getAttackBonus());
                if (value.getValue().getLifesteal() != 0)
                    itemsFile.set("weapons." + value.getKey() + ".attributes.lifesteal", value.getValue().getLifesteal());

                ArrayList<String> lores = value.getValue().getLores();
                if (lores.size() > 0) for (int i = 0; i < lores.size(); i++) {
                    itemsFile.set("weapons." + value.getKey() + ".lores." + i, lores.get(i));
                }
            }
        }

        //saving the talisman items
        if (!talismanItems.isEmpty()) {
            for (Map.Entry<String, Talisman> value : talismanItems.entrySet()) {
                itemsFile.set("talismans." + value.getKey() + ".name", value.getValue().getName());

                itemsFile.set("talismans." + value.getKey() + ".rarity", value.getValue().getRarity());

                itemsFile.set("talismans." + value.getKey() + ".value", value.getValue().getValue());

                itemsFile.set("talismans." + value.getKey() + ".item-type", value.getValue().getItemType().name());

                itemsFile.set("talismans." + value.getKey() + ".health", value.getValue().getHealth());

                itemsFile.set("talismans." + value.getKey() + ".minlvl", value.getValue().getMinLvl());

                //attributes
                if (value.getValue().getXpBonus() != 0)
                    itemsFile.set("talismans." + value.getKey() + ".attributes.xpbonus", value.getValue().getXpBonus());
                if (value.getValue().getLootBonus() != 0)
                    itemsFile.set("talismans." + value.getKey() + ".attributes.lootbonus", value.getValue().getLootBonus());
                if (value.getValue().getAttackBonus() != 0)
                    itemsFile.set("talismans." + value.getKey() + ".attributes.attackbonus", value.getValue().getAttackBonus());
                if (value.getValue().getLifesteal() != 0)
                    itemsFile.set("talismans." + value.getKey() + ".attributes.lifesteal", value.getValue().getLifesteal());

                ArrayList<String> lores = value.getValue().getLores();
                if (lores.size() > 0) for (int i = 0; i < lores.size(); i++) {
                    itemsFile.set("talismans." + value.getKey() + ".lores." + i, lores.get(i));
                }
            }
        }

        //saving the potion items
        if (!potionItems.isEmpty()) {
            for (Map.Entry<String, Potion> value : potionItems.entrySet()) {
                itemsFile.set("potions." + value.getKey() + ".name", value.getValue().getName());

                itemsFile.set("potions." + value.getKey() + ".rarity", value.getValue().getRarity());

                itemsFile.set("potions." + value.getKey() + ".value", value.getValue().getValue());

                itemsFile.set("potions." + value.getKey() + ".item-type", value.getValue().getItemType().name());

                itemsFile.set("potions." + value.getKey() + ".health", value.getValue().getHealth());

                itemsFile.set("potions." + value.getKey() + ".minlvl", value.getValue().getMinLvl());

                //attributes
                if (value.getValue().getXpBonus() != 0)
                    itemsFile.set("potions." + value.getKey() + ".attributes.xpbonus", value.getValue().getXpBonus());
                if (value.getValue().getLootBonus() != 0)
                    itemsFile.set("potions." + value.getKey() + ".attributes.lootbonus", value.getValue().getLootBonus());
                if (value.getValue().getAttackBonus() != 0)
                    itemsFile.set("potions." + value.getKey() + ".attributes.attackbonus", value.getValue().getAttackBonus());
                if (value.getValue().getLifesteal() != 0)
                    itemsFile.set("potions." + value.getKey() + ".attributes.lifesteal", value.getValue().getLifesteal());

                ArrayList<String> lores = value.getValue().getLores();
                if (lores.size() > 0) for (int i = 0; i < lores.size(); i++) {
                    itemsFile.set("potions." + value.getKey() + ".lores." + i, lores.get(i));
                }
            }
        }
        try { saveFile(itemsFile,"Variables/lootItems.yml",path); } catch (IOException e) {throw new RuntimeException(e);}
        System.out.println("[CustomLoot] Saved " + (lootItems.size() + weaponItems.size() + talismanItems.size() + potionItems.size()) + " items to file");
    }

    public static void extractVariables(String path) {

        YamlConfiguration itemsFile = YamlConfiguration.loadConfiguration(new File(path, "Variables/lootItems.yml"));

        //extraction the loot items
        if (itemsFile.contains("lootitems")) {
            itemsFile.getConfigurationSection("lootitems").getKeys(false).forEach(lootItemKey -> {

                String name = itemsFile.getString("lootitems." + lootItemKey + ".name");
                int rarity = itemsFile.getInt("lootitems." + lootItemKey + ".rarity");
                int value = itemsFile.getInt("lootitems." + lootItemKey + ".value");
                Material material = Material.getMaterial(itemsFile.getString("lootitems." + lootItemKey + ".item-type"));

                ArrayList<String> lores = new ArrayList<>();
                itemsFile.getConfigurationSection("lootitems." + lootItemKey + ".lores").getKeys(false).forEach(lootItemLoreKey -> {
                    lores.add(itemsFile.getString("lootitems." + lootItemKey + ".lores." + lootItemLoreKey));
                });

                Loot lootItem = new Loot(name, rarity, value, lores, material, lootItemKey);
                lootItems.put(lootItemKey, lootItem);
            });
        }

        //extracting the weapons
        if (itemsFile.contains("weapons")) {
            itemsFile.getConfigurationSection("weapons").getKeys(false).forEach(weaponItemKey -> {

                String name = itemsFile.getString("weapons." + weaponItemKey + ".name");
                int rarity = itemsFile.getInt("weapons." + weaponItemKey + ".rarity");
                int value = itemsFile.getInt("weapons." + weaponItemKey + ".value");
                Material material = Material.getMaterial(itemsFile.getString("weapons." + weaponItemKey + ".item-type"));
                int damage = itemsFile.getInt("weapons." + weaponItemKey + ".damage");
                int minLvl = itemsFile.getInt("weapons." + weaponItemKey + ".minlvl");

                int xpBonus = 0;
                if (itemsFile.contains("weapons." + weaponItemKey + ".attributes.xpbonus")) {
                    xpBonus = itemsFile.getInt("weapons." + weaponItemKey + ".attributes.xpbonus");
                }

                int lootBonus = 0;
                if (itemsFile.contains("weapons." + weaponItemKey + ".attributes.lootbonus")) {
                    lootBonus = itemsFile.getInt("weapons." + weaponItemKey + ".attributes.lootbonus");
                }

                int attackBonus = 0;
                if (itemsFile.contains("weapons." + weaponItemKey + ".attributes.attackbonus")) {
                    attackBonus = itemsFile.getInt("weapons." + weaponItemKey + ".attributes.attackbonus");
                }

                int lifesteal = 0;
                if (itemsFile.contains("weapons." + weaponItemKey + ".attributes.lifesteal")) {
                    lifesteal = itemsFile.getInt("weapons." + weaponItemKey + ".attributes.lifesteal");
                }

                ArrayList<String> lores = new ArrayList<>();
                itemsFile.getConfigurationSection("weapons." + weaponItemKey + ".lores").getKeys(false).forEach(lootItemLoreKey -> {
                    lores.add(itemsFile.getString("weapons." + weaponItemKey + ".lores." + lootItemLoreKey));
                });

                Weapon weaponItem = new Weapon(name, rarity, value, lores, material, weaponItemKey, damage, minLvl, xpBonus, lootBonus, attackBonus, lifesteal);
                weaponItems.put(weaponItemKey, weaponItem);
            });
        }

        //extracting the talismans
        if (itemsFile.contains("talismans")) {
            itemsFile.getConfigurationSection("talismans").getKeys(false).forEach(talismanItemKey -> {

                String name = itemsFile.getString("talismans." + talismanItemKey + ".name");
                int rarity = itemsFile.getInt("talismans." + talismanItemKey + ".rarity");
                int value = itemsFile.getInt("talismans." + talismanItemKey + ".value");
                Material material = Material.getMaterial(itemsFile.getString("talismans." + talismanItemKey + ".item-type"));
                int health = itemsFile.getInt("talismans." + talismanItemKey + ".health");
                int minLvl = itemsFile.getInt("talismans." + talismanItemKey + ".minlvl");

                int xpBonus = 0;
                if (itemsFile.contains("talismans." + talismanItemKey + ".attributes.xpbonus")) {
                    xpBonus = itemsFile.getInt("talismans." + talismanItemKey + ".attributes.xpbonus");
                }

                int lootBonus = 0;
                if (itemsFile.contains("talismans." + talismanItemKey + ".attributes.lootbonus")) {
                    lootBonus = itemsFile.getInt("talismans." + talismanItemKey + ".attributes.lootbonus");
                }

                int attackBonus = 0;
                if (itemsFile.contains("talismans." + talismanItemKey + ".attributes.attackbonus")) {
                    attackBonus = itemsFile.getInt("talismans." + talismanItemKey + ".attributes.attackbonus");
                }

                int lifesteal = 0;
                if (itemsFile.contains("talismans." + talismanItemKey + ".attributes.lifesteal")) {
                    lifesteal = itemsFile.getInt("talismans." + talismanItemKey + ".attributes.lifesteal");
                }

                ArrayList<String> lores = new ArrayList<>();
                itemsFile.getConfigurationSection("talismans." + talismanItemKey + ".lores").getKeys(false).forEach(lootItemLoreKey -> {
                    lores.add(itemsFile.getString("talismans." + talismanItemKey + ".lores." + lootItemLoreKey));
                });

                Talisman talismanItem = new Talisman(name, rarity, value, lores, material, talismanItemKey, health, minLvl, xpBonus, lootBonus, attackBonus, lifesteal);
                talismanItems.put(talismanItemKey, talismanItem);
            });
        }


        //extracting the potions
        if (itemsFile.contains("potions")) {
            itemsFile.getConfigurationSection("potions").getKeys(false).forEach(potionItemKey -> {

                String name = itemsFile.getString("potions." + potionItemKey + ".name");
                int rarity = itemsFile.getInt("potions." + potionItemKey + ".rarity");
                int value = itemsFile.getInt("potions." + potionItemKey + ".value");
                Material material = Material.getMaterial(itemsFile.getString("potions." + potionItemKey + ".item-type"));
                int health = itemsFile.getInt("potions." + potionItemKey + ".health");
                int minLvl = itemsFile.getInt("potions." + potionItemKey + ".minlvl");

                int xpBonus = 0;
                if (itemsFile.contains("potions." + potionItemKey + ".attributes.xpbonus")) {
                    xpBonus = itemsFile.getInt("potions." + potionItemKey + ".attributes.xpbonus");
                }

                int lootBonus = 0;
                if (itemsFile.contains("potions." + potionItemKey + ".attributes.lootbonus")) {
                    lootBonus = itemsFile.getInt("potions." + potionItemKey + ".attributes.lootbonus");
                }

                int attackBonus = 0;
                if (itemsFile.contains("potions." + potionItemKey + ".attributes.attackbonus")) {
                    attackBonus = itemsFile.getInt("potions." + potionItemKey + ".attributes.attackbonus");
                }

                int lifesteal = 0;
                if (itemsFile.contains("potions." + potionItemKey + ".attributes.lifesteal")) {
                    lifesteal = itemsFile.getInt("potions." + potionItemKey + ".attributes.lifesteal");
                }

                ArrayList<String> lores = new ArrayList<>();
                itemsFile.getConfigurationSection("potions." + potionItemKey + ".lores").getKeys(false).forEach(lootItemLoreKey -> {
                    lores.add(itemsFile.getString("potions." + potionItemKey + ".lores." + lootItemLoreKey));
                });

                Potion potionItem = new Potion(name, rarity, value, lores, material, potionItemKey, health, minLvl, xpBonus, lootBonus, attackBonus, lifesteal);
                potionItems.put(potionItemKey, potionItem);
            });
        }
    }
    /*
    public static HashMap<String, Tuple4<String, ArrayList<String>, ArrayList<String>, Material>> itemsTable = new HashMap<>();


    public static void saveVariables(String path) {

        File testConfigFile = new File(path, "Variables/lootItems.yml");
        testConfigFile.delete();

        YamlConfiguration itemsFile = YamlConfiguration.loadConfiguration(new File(path, "Variables/lootItems.yml"));

        for (Map.Entry<String, Tuple4<String, ArrayList<String>, ArrayList<String>, Material>> value : itemsTable.entrySet()) {
            itemsFile.set("items." + value.getKey() + ".name", value.getValue()._1);
            ArrayList<String> lores = value.getValue()._2;
            if (lores.size() > 0) for (int i = 0; i < lores.size(); i++) {
                itemsFile.set("items." + value.getKey() + ".lores." + i, lores.get(i));
            }
            ArrayList<String> NBTs = value.getValue()._3;
            if (NBTs.size() > 0) for (int i = 0; i < NBTs.size(); i++) {
                itemsFile.set("items." + value.getKey() + ".nbts." + i, NBTs.get(i));
            }
            itemsFile.set("items." + value.getKey() + ".item-type", value.getValue()._4.name());
        }
        try { saveFile(itemsFile,"Variables/lootItems.yml",path); } catch (IOException e) {throw new RuntimeException(e);}
        System.out.println("[CustomLoot] Saved " + itemsTable.size() + " items to file");
    }
    
    public static void extractVariables(String path) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(new File(path, "Variables/lootItems.yml"));

        if (file.contains("items")) {
            file.getConfigurationSection("items").getKeys(false).forEach(itemkey -> {
                String name = file.getString("items." + itemkey + ".name");

                ArrayList<String> lores = new ArrayList<>();

                file.getConfigurationSection("items." + itemkey + ".lores").getKeys(false).forEach(lorekey -> {
                    lores.add(file.getString("items." + itemkey + ".lores." + lorekey));
                });

                ArrayList<String> NBTs = new ArrayList<>();

                file.getConfigurationSection("items." + itemkey + ".nbts").getKeys(false).forEach(nbtkey -> {
                    NBTs.add(file.getString("items." + itemkey + ".nbts." + nbtkey));
                });

                Material material =  Material.getMaterial(file.getString("items." + itemkey + ".item-type"));
                itemsTable.put(itemkey, new Tuple4<>(name, lores, NBTs, material));
            });
        }
        System.out.println("[CustomLoot] Loaded " + itemsTable.size() + " items");
    }
    */


    public static void saveFile(YamlConfiguration file, String s, String Path) throws IOException {
        file.save(new File(Path, s));
    }
}
