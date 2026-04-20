package org.ragingzombies.flintnpowder.item.ammo;

import net.minecraft.network.chat.Component;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.core.jmx.Server;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.core.util.CameraWork;
import org.ragingzombies.flintnpowder.handlers.ServerTickHandler;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.CastIronRoundshotProjectile;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.ragingzombies.flintnpowder.core.util.CameraWork.OffsetEntityCamera;

public class CopperVolleyshot extends BaseAmmo {
    public CopperVolleyshot(Properties pProperties) {
        super(pProperties);
        this.damage = 7;
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, ItemStack gun, Level level) {
        if (shooter.level().isClientSide()) return;

        ServerLevel serverLevel = (ServerLevel) shooter.level();
        int currentTick = serverLevel.getServer().getTickCount();

        for (int i = 0; i < 5; i++) {
            // Delay
            ServerTickHandler.createTask(i*2, () -> {
                serverLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                        SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 9.0F, 1.0F, 0);

                CastIronRoundshotProjectile proj = new CastIronRoundshotProjectile(level, shooter);

                proj.damage = this.damage * ((GunBase) gun.getItem()).damageModifier(shooter.getUUID(), gun);
                proj.setOwner(shooter);

                proj.shootFromRotation(shooter, CameraWork.getPlayerViewX(shooter)-5, CameraWork.getPlayerViewY(shooter), 0.0F, 10F, 4F * ((GunBase) gun.getItem()).accuracyModifier(shooter.getUUID(), gun));

                // Recoil
                if (shooter instanceof Player) {
                    Random rand = new Random();
                    float angleX = rand.nextFloat(4.0F);
                    OffsetEntityCamera(shooter, (-5 + (angleX - 2)) * ((GunBase) gun.getItem()).recoilModifierX(shooter.getUUID(), gun), (angleX - 2) * ((GunBase) gun.getItem()).recoilModifierY(shooter.getUUID(), gun));
                }

                level.addFreshEntity(proj);
            });
        }
    }
}
