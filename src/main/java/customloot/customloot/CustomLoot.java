package customloot.customloot;

import customloot.customloot.variables.VariableHandler;
import de.tr7zw.nbtapi.NBTItem;
import javaslang.Tuple4;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class CustomLoot extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        String variablePath = getDataFolder().getAbsoluteFile().toString();
        VariableHandler.extractVariables(variablePath);

        //VariableHandler.itemsTable.put("testItem", new Tuple4<>("testname", "lore1||lore2||lore3", "nbt1||nbt2||nbt3", Material.KELP));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        String variablePath = getDataFolder().getAbsoluteFile().toString();
        VariableHandler.saveVariables(variablePath);
    }
}
