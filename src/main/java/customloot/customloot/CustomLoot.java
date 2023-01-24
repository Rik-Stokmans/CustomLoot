package customloot.customloot;

import customloot.customloot.Loot.*;
import customloot.customloot.LootMenu.*;
import customloot.customloot.variables.VariableHandler;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

import static customloot.customloot.variables.VariableHandler.*;

public final class CustomLoot extends JavaPlugin {

    public static LootMenu lootMenu;

    @Override
    public void onEnable() {
        // Plugin startup logic
        String variablePath = getDataFolder().getAbsoluteFile().toString();
        VariableHandler.extractVariables(variablePath);
        /*
        ArrayList<String> lores = new ArrayList<>();
        lores.add("&7nice lore");
        lores.add("&7very nice lore");
        Loot lootItem = new Loot("&7Funny Item", 2, 132, lores, Material.BEACON, "test");
        Weapon weaponItem = new Weapon("&7Nice Sword", 2, 132, lores, Material.IRON_SWORD, "sword", 3, 5, 1, 2, 3, 4);
        Talisman talismanItem = new Talisman("&7Expensive Ring", 2, 132, lores, Material.DIAMOND, "talisman", 3, 5, 1, 2, 3, 4);
        Potion potionItem = new Potion("&7Tasty Potion", 2, 132, lores, Material.EXPERIENCE_BOTTLE, "potion", 3, 5, 1, 2, 3, 4);
        Weapon weaponItem2 = new Weapon("&7Useless Sword", 0, 1, lores, Material.WOODEN_SWORD, "sword2", 0, 0, 0, 0, 0, 0);
        Talisman talismanItem2 = new Talisman("&7Useless Ring", 0, 1, lores, Material.BROWN_DYE, "talisman2", 0, 0, 0, 0, 0, 0);
        Potion potionItem2 = new Potion("&7Useless Potion", 0, 1, lores, Material.GLASS_BOTTLE, "potion2", 0, 0, 0, 0, 0, 0);

        lootItems.put("test", lootItem);

        weaponItems.put("sword", weaponItem);
        weaponItems.put("sword2", weaponItem2);

        talismanItems.put("talisman", talismanItem);
        talismanItems.put("talisman2", talismanItem2);

        potionItems.put("potion", potionItem);
        potionItems.put("potion2", potionItem2);
        */
        System.out.println("[CustomLoot] " + lootItems.size());

        ArrayList<Listener> events = new ArrayList<>();
        //list of events
        events.add(new LootMenu());

        for (Listener l : events) {
            getServer().getPluginManager().registerEvents(l, this);
        }

        lootMenu = new LootMenu();

        this.getCommand("OpenLootMenu").setExecutor(new OpenLootMenu());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        String variablePath = getDataFolder().getAbsoluteFile().toString();
        VariableHandler.saveVariables(variablePath);
    }
}
