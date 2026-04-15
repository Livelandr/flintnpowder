package org.ragingzombies.flintnpowder.item.guns.flintlocks;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.util.Lazy;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.ragingzombies.flintnpowder.core.guns.FlintlockBase;
import org.ragingzombies.flintnpowder.item.ammo.CopperRoundshot;
import org.ragingzombies.flintnpowder.item.attachments.Bayonet;
import org.ragingzombies.flintnpowder.item.attachments.HighProfileOptic;
import org.ragingzombies.flintnpowder.item.attachments.LowProfileOptic;
import org.ragingzombies.flintnpowder.item.attachments.ModItemsAttachments;
import org.ragingzombies.flintnpowder.item.guns.ModItemsGuns;
import org.ragingzombies.flintnpowder.item.ammo.CastIronRoundshot;
import org.ragingzombies.flintnpowder.item.ammo.SteelRoundshot;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.ragingzombies.flintnpowder.core.attachments.AttachmentBase.attachmentTypes;

public class Musket extends FlintlockBase {

    private static final UUID ENTITY_REACH_MODIFIER_UUID = UUID.fromString("5aa470a9-ce82-4124-ae14-11d5ee1c18e0");
    private static final UUID DAMAGE_MODIFIER_UUID = UUID.fromString("8f715ef6-7db2-4168-9006-36d10db1da44");
    private static final UUID ATTACK_SPEED_UUID = UUID.fromString("70b0062f-472a-430c-99cc-baa5f05828ed");

    private final Lazy<Multimap<Attribute, AttributeModifier>> lazyAttributeMap;

    public Musket(Properties pProperties) {
        super(pProperties);

        shootCooldownTicks = 20;
        gunpowderCooldownTicks = 20;
        ramrodCooldownTicks = 60;

        addAllowedAmmo(CastIronRoundshot.class);
        addAllowedAmmo(SteelRoundshot.class);

        addAllowedAttachment(Bayonet.class);
        addAllowedAttachment(HighProfileOptic.class);
        addAllowedAttachment(LowProfileOptic.class);

        this.lazyAttributeMap = Lazy.of(() -> {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(ForgeMod.ENTITY_REACH.get(),
                    new AttributeModifier(
                            ENTITY_REACH_MODIFIER_UUID,
                            "BayonetReach",
                            1.25,
                            AttributeModifier.Operation.ADDITION
                    ));
            builder.put(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                            DAMAGE_MODIFIER_UUID,
                            "BayonetDamageIncrease",
                            6.0,
                            AttributeModifier.Operation.ADDITION
                    ));
            builder.put(Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                            ATTACK_SPEED_UUID,
                            "BayonetDamageIncrease",
                            -2.5,
                            AttributeModifier.Operation.ADDITION
                    ));

            return builder.build();
        });
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND && stack.getOrCreateTag().getBoolean("HaveBayonet")) {
            return lazyAttributeMap.get();
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public float accuracyModifier(UUID ply) {
        return 1 * super.accuracyModifier(ply);
    }

    @Override
    public void onStuff(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RAMROD.get(), SoundSource.NEUTRAL, 1.0F, 0.8F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 35);
        }
    }

    @Override
    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.FLINTSTRIKE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.MUSKETFIRE.get(), SoundSource.NEUTRAL, 3.0F, 1.0F, 0);
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.GUNSHOTDISTANT.get(), SoundSource.NEUTRAL, 9.0F, 1.0F, 0);

        setReloadAnimation(gunStack);

        // Particles
        if (!pLevel.isClientSide()) {
            ServerLevel sLevel = (ServerLevel) pLevel;
            //Second index is your particle count. DO. NOT. TOUCH. pParticleCount.
            //I'm dead serious. I know it might be weird that the particle count is not the actual particle count, but just trust the process and don't touch it.
            //Thank you.
            for (int index0 = 0; index0 < 25; index0++) {
                double speed = 0.55;
                double spread = 0.12;

                sLevel.sendParticles(
                        ParticleTypes.POOF,
                        shooter.getX(), shooter.getY() + shooter.getEyeHeight() * 0.6, shooter.getZ(),
                        0,
                        shooter.getDeltaMovement().x + shooter.getLookAngle().x * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        shooter.getDeltaMovement().y + shooter.getLookAngle().y * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        shooter.getDeltaMovement().z + shooter.getLookAngle().z * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        1.0
                );
            }
            for (int index1 = 0; index1 < 15; index1++) {
                double speed = 0.22;
                double spread = 0.28;

                sLevel.sendParticles(
                        ParticleTypes.LARGE_SMOKE,
                        shooter.getX(), shooter.getY() + shooter.getEyeHeight() * 0.5, shooter.getZ(),
                        0,
                        shooter.getDeltaMovement().x + shooter.getLookAngle().x * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        shooter.getDeltaMovement().y + shooter.getLookAngle().y * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        shooter.getDeltaMovement().z + shooter.getLookAngle().z * speed + Mth.nextDouble(RandomSource.create(), spread * (-1), spread),
                        1.0
                );
            }
        }

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldown(shooter, gunStack));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.musket.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.musket.description_1"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.musket.description_2"));
        pTooltipComponents.add(Component.literal(""));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
