package customloot.customloot.variables;

import customloot.customloot.Loot.GeneralItem;
import customloot.customloot.Loot.LootItem;
import javaslang.Tuple4;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VariableHandler {



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
