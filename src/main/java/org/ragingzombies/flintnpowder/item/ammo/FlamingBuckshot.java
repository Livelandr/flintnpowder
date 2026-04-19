package org.ragingzombies.flintnpowder.item.ammo;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.core.util.CameraWork;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.FlamingBuckshotProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.shotgun.BuckshotProjectile;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.ragingzombies.flintnpowder.core.util.CameraWork.OffsetEntityCamera;

public class FlamingBuckshot extends BaseAmmo {
    public FlamingBuckshot(Properties pProperties) {
        super(pProperties);
        this.damage = 3F;
        this.customDescription = true;
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, GunBase gun, Level level) {
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            float angle = rand.nextFloat((float) (2.0F*Math.PI));
            float radius = rand.nextFloat(20);

            FlamingBuckshotProjectile proj = new FlamingBuckshotProjectile(level, shooter);

            proj.setOwner(shooter);
            proj.shootFromRotation(shooter, CameraWork.getPlayerViewX(shooter) + (float)(Math.cos(angle)*radius),
                    CameraWork.getPlayerViewY(shooter) + (float)(Math.sin(angle)*radius), 0.0F, 5F,1.25F * gun.accuracyModifier(shooter.getUUID()));
            proj.SetDamage(this.damage * gun.damageModifier());

            level.addFreshEntity(proj);
        }


        if (shooter instanceof Player) {
            // Recoil
            float angleX = rand.nextFloat(4.0F);
            OffsetEntityCamera(shooter, (-25 + (angleX - 2)) * gun.recoilModifierX(shooter.getUUID()), (angleX - 2) * gun.recoilModifierY(shooter.getUUID()));
        }
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("flintnpowder.incendiary"));
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("flintnpowder.bullet_description"));
        pTooltipComponents.add(Component.translatable("flintnpowder.projectile_damage")
                .append(String.valueOf(Math.round(this.damage)))
                .append("x6").withStyle(ChatFormatting.DARK_GREEN));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
