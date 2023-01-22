package customloot.customloot.Loot;

import java.util.ArrayList;

public class Talisman extends GeneralItem {

    int health;
    int minLvl;

    //attributes
    int xpBonus;
    int lootBonus;
    int attackBonus;
    int lifesteal;

    public Talisman(String _name, int _rarity, int _worth, ArrayList<String> _lores, int _health, int _minLvl, int _xpBonus, int _lootBonus, int _attackBonus, int _lifesteal) {
        super(_name, _rarity, _worth, _lores);
        health = _health;
        minLvl = _minLvl;
        xpBonus = _xpBonus;
        lootBonus = _lootBonus;
        attackBonus = _attackBonus;
        lifesteal = _lifesteal;
    }
}
