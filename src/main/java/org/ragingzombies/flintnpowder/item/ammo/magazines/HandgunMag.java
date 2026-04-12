package org.ragingzombies.flintnpowder.item.ammo.magazines;

import net.minecraft.world.item.ItemStack;
import org.ragingzombies.flintnpowder.core.ammo.BaseMagazine;
import org.ragingzombies.flintnpowder.item.ammo.PistolRound;
import org.ragingzombies.flintnpowder.item.guns.flintlocks.Pistol;

public class HandgunMag extends BaseMagazine {

    public HandgunMag(Properties pProperties) {
        super(pProperties);
        maxAmmo = 8;
        addAllowedAmmo(PistolRound.class);
    }
}
