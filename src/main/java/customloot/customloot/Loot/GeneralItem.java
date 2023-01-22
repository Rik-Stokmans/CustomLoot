package customloot.customloot.Loot;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GeneralItem {
    private String name;
    private int rarity;
    private int worth;
    private ArrayList<String> lores;

    public GeneralItem(String _name, int _rarity, int _worth, ArrayList<String> _lores) {
        name = _name;
        rarity = _rarity;
        worth = _worth;
        lores = _lores;
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

    public int getWorth() {
        return worth;
    }

    public void setWorth(int worth) {
        this.worth = worth;
    }

    public ArrayList<String> getLores() {
        return lores;
    }

    public void setLores(ArrayList<String> lores) {
        this.lores = lores;
    }
}
