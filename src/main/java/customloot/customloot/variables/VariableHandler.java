package customloot.customloot.variables;

import javaslang.Tuple4;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class VariableHandler {

    public static HashMap<String, Tuple4<String, String, String, Material>> itemsTable = new HashMap<>();

    public static void saveVariables(String path) {
        YamlConfiguration itemsFile = YamlConfiguration.loadConfiguration(new File(path, "Variables/lootItems.yml"));

        for (Map.Entry<String, Tuple4<String, String, String, Material>> value : itemsTable.entrySet()) {
            itemsFile.set("items." + value.getKey() + ".name", value.getValue()._1);
            itemsFile.set("items." + value.getKey() + ".lore", value.getValue()._2);
            itemsFile.set("items." + value.getKey() + ".nbt", value.getValue()._3);
            itemsFile.set("items." + value.getKey() + ".item-type", value.getValue()._4.name());
        }
        try { saveFile(itemsFile,"Variables/lootItems.yml",path); } catch (IOException e) {throw new RuntimeException(e);}
        System.out.println("[CustomLoot] Saved " + itemsTable.size() + " items to file");
    }
    
    public static void extractVariables(String path) {
        YamlConfiguration file = YamlConfiguration.loadConfiguration(new File(path, "Variables/lootItems.yml"));

        if (file.contains("items")) {
            file.getConfigurationSection("items").getKeys(false).forEach(key -> {
                String name = file.getString("items." + key + ".name");
                String lore = file.getString("items." + key + ".lore");
                String nbt = file.getString("items." + key + ".nbt");
                Material material =  Material.getMaterial(file.getString("items." + key + ".item-type"));
                itemsTable.put(key, new Tuple4<>(name, lore, nbt, material));
            });
        }
        System.out.println("[CustomLoot] Loaded " + itemsTable.size() + " items");
    }

    public static void saveFile(YamlConfiguration file, String s, String Path) throws IOException {
        file.save(new File(Path, s));
    }
}
