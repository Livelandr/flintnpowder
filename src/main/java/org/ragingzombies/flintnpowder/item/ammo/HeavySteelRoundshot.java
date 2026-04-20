package org.ragingzombies.flintnpowder.item.ammo;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.core.util.CameraWork;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.FoolsGoldRoundshotProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.HeavyCastIronRoundshotProjectile;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.ragingzombies.flintnpowder.core.util.CameraWork.OffsetEntityCamera;

public class HeavySteelRoundshot extends BaseAmmo {
    public HeavySteelRoundshot(Properties pProperties) {
        super(pProperties);
        this.damage = 30;
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, GunBase gun, Level level) {
        FoolsGoldRoundshotProjectile proj = new FoolsGoldRoundshotProjectile(level, shooter);

        proj.damage = this.damage * gun.damageModifier();
        proj.setOwner(shooter);

        proj.shootFromRotation(shooter, CameraWork.getPlayerViewX(shooter), CameraWork.getPlayerViewY(shooter), 0.0F, 5F, 3F * gun.accuracyModifier(shooter.getUUID()));

        // Recoil

        if (shooter instanceof Player) {
            Random rand = new Random();
            float angleX = rand.nextFloat(4.0F);
            OffsetEntityCamera(shooter, (-15 + (angleX - 2)) * gun.recoilModifierX(shooter.getUUID()), (angleX - 2) * gun.recoilModifierY(shooter.getUUID()));
        }

        level.addFreshEntity(proj);
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.golden_roundshot.description_1"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
