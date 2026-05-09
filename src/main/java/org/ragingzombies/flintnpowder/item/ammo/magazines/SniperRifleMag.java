package org.ragingzombies.flintnpowder.item.ammo.magazines;

import org.ragingzombies.flintnpowder.core.ammo.BaseMagazine;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;

public class SniperRifleMag extends BaseMagazine {

    public SniperRifleMag(Properties pProperties) {
        super(pProperties);
        maxAmmo = 10;
        addAllowedAmmo(ModItemsAmmo.SNIPERRIFLEROUND.get());
    }
}
