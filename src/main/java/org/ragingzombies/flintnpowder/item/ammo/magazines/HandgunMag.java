package org.ragingzombies.flintnpowder.item.ammo.magazines;

import org.ragingzombies.flintnpowder.core.ammo.BaseMagazine;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;

public class HandgunMag extends BaseMagazine {

    public HandgunMag(Properties pProperties) {
        super(pProperties);
        maxAmmo = 8;
        addAllowedAmmo(ModItemsAmmo.PISTOLROUND.get());
    }
}
