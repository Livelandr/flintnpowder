package org.ragingzombies.flintnpowder.item.ammo.magazines;

import org.ragingzombies.flintnpowder.core.ammo.BaseMagazine;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;

public class ShotgunMag extends BaseMagazine {

    public ShotgunMag(Properties pProperties) {
        super(pProperties);
        maxAmmo = 8;
        addAllowedAmmo(ModItemsAmmo.SHOTGUNSHELL.get());
        addAllowedAmmo(ModItemsAmmo.SHOTGUNSHELLSLUG.get());
        addAllowedAmmo(ModItemsAmmo.SHOTGUNSHELLDRAGON.get());
    }
}
