package org.ragingzombies.flintnpowder.item.ammo.magazines;

import org.ragingzombies.flintnpowder.core.ammo.BaseMagazine;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;

public class BattleRifleMag extends BaseMagazine {

    public BattleRifleMag(Properties pProperties) {
        super(pProperties);
        maxAmmo = 8;
        addAllowedAmmo(ModItemsAmmo.RIFLEROUND.get());
    }
}
