package customloot.customloot;

import customloot.customloot.LootMenu.LootMenu;
import customloot.customloot.LootMenu.OpenLootMenu;
import customloot.customloot.variables.VariableHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class CustomLoot extends JavaPlugin {

    public static LootMenu lootMenu;

    @Override
    public void onEnable() {
        // Plugin startup logic
        String variablePath = getDataFolder().getAbsoluteFile().toString();
        //VariableHandler.extractVariables(variablePath);

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
        //VariableHandler.saveVariables(variablePath);
    }
}
