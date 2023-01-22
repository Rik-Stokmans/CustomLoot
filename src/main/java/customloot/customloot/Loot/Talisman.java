package customloot.customloot.Loot;

import java.util.ArrayList;

public class Talisman extends GeneralItem {

    private int health;
    private int minLvl;

    //attributes
    private int xpBonus;
    private int lootBonus;
    private int attackBonus;
    private int lifesteal;

    public Talisman(String _name, int _rarity, int _worth, ArrayList<String> _lores, int _health, int _minLvl, int _xpBonus, int _lootBonus, int _attackBonus, int _lifesteal) {
        super(_name, _rarity, _worth, _lores);
        health = _health;
        minLvl = _minLvl;
        xpBonus = _xpBonus;
        lootBonus = _lootBonus;
        attackBonus = _attackBonus;
        lifesteal = _lifesteal;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMinLvl() {
        return minLvl;
    }

    public void setMinLvl(int minLvl) {
        this.minLvl = minLvl;
    }

    public int getXpBonus() {
        return xpBonus;
    }

    public void setXpBonus(int xpBonus) {
        this.xpBonus = xpBonus;
    }

    public int getLootBonus() {
        return lootBonus;
    }

    public void setLootBonus(int lootBonus) {
        this.lootBonus = lootBonus;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }

    public int getLifesteal() {
        return lifesteal;
    }

    public void setLifesteal(int lifesteal) {
        this.lifesteal = lifesteal;
    }
}
