package org.ragingzombies.flintnpowder.item.ammo.magazines;

import org.ragingzombies.flintnpowder.core.ammo.BaseMagazine;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;

public class ExtendedHandgunMag extends BaseMagazine {

    public ExtendedHandgunMag(Properties pProperties) {
        super(pProperties);
        maxAmmo = 27;
        addAllowedAmmo(ModItemsAmmo.PISTOLROUND.get());
    }
}
