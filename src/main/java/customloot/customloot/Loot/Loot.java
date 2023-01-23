package customloot.customloot.Loot;

import org.bukkit.Material;

import java.util.ArrayList;

public class Loot extends GeneralItem {

    public Loot(String _name, int _rarity, int _value, ArrayList<String> _lore, Material _itemType) {
        super(_name, _rarity, _value, _lore, _itemType);
    }
}
