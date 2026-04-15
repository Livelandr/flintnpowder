package org.ragingzombies.flintnpowder.item.ammo.shotgun;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.core.util.CameraWork;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.shotgun.BuckshotProjectile;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.shotgun.DragonBreathProjectile;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.ragingzombies.flintnpowder.core.util.CameraWork.OffsetEntityCamera;

public class ShotgunShellDragon extends BaseAmmo {
    public ShotgunShellDragon(Properties pProperties) {
        super(pProperties);
        this.customDescription = true;
    }

    @Override
    public void onAmmoShot(LivingEntity shooter, GunBase gun, Level level) {
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            float angle = rand.nextFloat((float) (2.0F*Math.PI));
            float radius = rand.nextFloat(45);

            DragonBreathProjectile proj = new DragonBreathProjectile(level, shooter);

            proj.setOwner(shooter);
            proj.shootFromRotation(shooter, CameraWork.getPlayerViewX(shooter) + (float)(Math.cos(angle)*radius),
                    CameraWork.getPlayerViewY(shooter) + (float)(Math.sin(angle)*radius), 0.0F, 2F,5 * gun.accuracyModifier(shooter.getUUID()));
            proj.SetDamage(0.0F * gun.damageModifier());

            level.addFreshEntity(proj);
        }

        // Recoil
        if (shooter instanceof Player) {
            float angleX = rand.nextFloat(4.0F);
            OffsetEntityCamera(shooter, (-25 + (angleX - 2)) * gun.recoilModifierX(shooter.getUUID()), (angleX - 2) * gun.recoilModifierY(shooter.getUUID()));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("flintnpowder.bullet_description"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.shotgunshelldragon.description").withStyle(ChatFormatting.DARK_GREEN));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
