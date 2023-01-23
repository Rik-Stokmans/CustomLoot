package customloot.customloot.Loot;

import org.bukkit.Material;

import java.util.ArrayList;

public class GeneralItem {
    private String name;
    private int rarity;
    private int value;
    private ArrayList<String> lores;
    private Material itemType;
    private String identifier;

    public GeneralItem(String _name, int _rarity, int _value, ArrayList<String> _lores, Material _itemType, String _identifier) {
        name = _name;
        rarity = _rarity;
        value = _value;
        lores = _lores;
        itemType = _itemType;
        identifier = _identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ArrayList<String> getLores() {
        return lores;
    }

    public void setLores(ArrayList<String> lores) {
        this.lores = lores;
    }

    public Material getItemType() {
        return itemType;
    }

    public void setItemType(Material itemType) {
        this.itemType = itemType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
