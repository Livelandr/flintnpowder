package org.ragingzombies.flintnpowder.item.ammo;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.core.util.CameraWork;
import org.ragingzombies.flintnpowder.handlers.ServerTickHandler;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.CastIronRoundshotProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.FlamingBuckshotProjectile;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.ragingzombies.flintnpowder.core.util.CameraWork.OffsetEntityCamera;

public class FlamingGrapeshot extends BaseAmmo {
    public FlamingGrapeshot(Properties pProperties) {
        super(pProperties);
        this.damage = 3;
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, GunBase gun, Level level) {
        if (shooter.level().isClientSide()) return;

        ServerLevel serverLevel = (ServerLevel) shooter.level();
        int currentTick = serverLevel.getServer().getTickCount();

        Random rand = new Random();

        serverLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 9.0F, .5F+rand.nextFloat(), 0);

        for (int i = 0; i < 8; i++) {
            float angle = rand.nextFloat((float) (2.0F*Math.PI));
            float radius = rand.nextFloat(7);

             FlamingBuckshotProjectile proj = new FlamingBuckshotProjectile(level, shooter);

             proj.setOwner(shooter);
             proj.SetDamage(this.damage * gun.damageModifier());

             proj.shootFromRotation(shooter, CameraWork.getPlayerViewX(shooter) + (float)(Math.cos(angle)*radius),
                    CameraWork.getPlayerViewY(shooter) + (float)(Math.sin(angle)*radius), 0.0F, 5F,2 * gun.accuracyModifier(shooter.getUUID()));


             // Recoil
             if (shooter instanceof Player) {
                 float angleX = rand.nextFloat(4.0F);
                 OffsetEntityCamera(shooter, (-7 + (angleX - 2)) * gun.recoilModifierX(shooter.getUUID()), (angleX - 2) * gun.recoilModifierY(shooter.getUUID()));
             }

             level.addFreshEntity(proj);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("flintnpowder.incendiary"));
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("flintnpowder.bullet_description"));
        pTooltipComponents.add(Component.translatable("flintnpowder.projectile_damage")
                .append(String.valueOf(Math.round(this.damage)))
                .append("x10").withStyle(ChatFormatting.DARK_GREEN));
    }
}
