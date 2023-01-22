package customloot.customloot.Loot;

import java.util.ArrayList;

public class Weapon extends GeneralItem {

    int damage;
    int minLvl;

    //attributes
    int xpBonus;
    int lootBonus;
    int attackBonus;
    int lifesteal;

    public Weapon(String _name, int _rarity, int _worth, ArrayList<String> _lores, int _damage, int _minLvl, int _xpBonus, int _lootBonus, int _attackBonus, int _lifesteal) {
        super(_name, _rarity, _worth, _lores);
        damage = _damage;
        minLvl = _minLvl;
        xpBonus = _xpBonus;
        lootBonus = _lootBonus;
        attackBonus = _attackBonus;
        lifesteal = _lifesteal;
    }
}
