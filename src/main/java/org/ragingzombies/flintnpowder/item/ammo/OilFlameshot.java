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

public class OilFlameshot extends BaseAmmo {
    public OilFlameshot(Properties pProperties) {
        super(pProperties);
        this.damage = 2;
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, GunBase gun, Level level) {
        if (shooter.level().isClientSide()) return;

        ServerLevel serverLevel = (ServerLevel) shooter.level();
        int currentTick = serverLevel.getServer().getTickCount();


        for (int i = 0; i < 10; i++) {
            // Delay
            ServerTickHandler.createTask(i*4, () -> {
                Random rand = new Random();
                serverLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                        SoundEvents.FIRECHARGE_USE, SoundSource.NEUTRAL, 9.0F, 0.3F + rand.nextFloat(0.8F), 0);

                FlamingBuckshotProjectile proj = new FlamingBuckshotProjectile(level, shooter);

                proj.damage = this.damage * gun.damageModifier();
                proj.setOwner(shooter);

                proj.shootFromRotation(shooter, CameraWork.getPlayerViewX(shooter)-5, CameraWork.getPlayerViewY(shooter), 0.0F, 2F, 2F * gun.accuracyModifier(shooter.getUUID()));

                level.addFreshEntity(proj);
            });
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("flintnpowder.incendiary"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.greek_fire_charge.description_1"));
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("flintnpowder.bullet_description"));
        pTooltipComponents.add(Component.translatable("flintnpowder.projectile_damage")
                .append(String.valueOf(Math.round(this.damage)))
                .append("x10").withStyle(ChatFormatting.DARK_GREEN));
    }
}
