package customloot.customloot;

import customloot.customloot.variables.LootMenu;
import customloot.customloot.variables.OpenLootMenu;
import customloot.customloot.variables.VariableHandler;
import de.tr7zw.nbtapi.NBTItem;
import javaslang.Tuple4;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class CustomLoot extends JavaPlugin {

    public static LootMenu lootMenu;

    @Override
    public void onEnable() {
        // Plugin startup logic
        String variablePath = getDataFolder().getAbsoluteFile().toString();
        VariableHandler.extractVariables(variablePath);

        ArrayList<Listener> events = new ArrayList<>();
        //list of events
        events.add(new LootMenu());

        for (Listener l : events) {
            getServer().getPluginManager().registerEvents(l, this);
        }

        this.getCommand("OpenLootMenu").setExecutor(new OpenLootMenu());

        lootMenu = new LootMenu();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        String variablePath = getDataFolder().getAbsoluteFile().toString();
        VariableHandler.saveVariables(variablePath);
    }
}
