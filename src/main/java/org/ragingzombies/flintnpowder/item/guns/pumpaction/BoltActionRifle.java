package org.ragingzombies.flintnpowder.item.guns.pumpaction;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.Lazy;
import org.ragingzombies.flintnpowder.core.guns.PumpActionBase;
import org.ragingzombies.flintnpowder.item.ammo.ModItemsAmmo;
import org.ragingzombies.flintnpowder.item.attachments.ModItemsAttachments;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class BoltActionRifle extends PumpActionBase {

    private static final UUID ENTITY_REACH_MODIFIER_UUID = UUID.fromString("5aa470a9-ce82-4124-ae14-11d5ee1c18e0");
    private static final UUID DAMAGE_MODIFIER_UUID = UUID.fromString("8f715ef6-7db2-4168-9006-36d10db1da44");
    private static final UUID ATTACK_SPEED_UUID = UUID.fromString("70b0062f-472a-430c-99cc-baa5f05828ed");

    private final Lazy<Multimap<Attribute, AttributeModifier>> lazyAttributeMap;

    public BoltActionRifle(Properties pProperties) {
        super(pProperties);

        this.maxAmmo = 4;
        this.shootCooldownTicks = 20;

        addAllowedAmmo(ModItemsAmmo.RIFLEROUND.get());

        addAllowedAttachment(ModItemsAttachments.LOWPROFILEOPTIC.get());
        addAllowedAttachment(ModItemsAttachments.HIGHPROFILEOPTIC.get());
        addAllowedAttachment(ModItemsAttachments.BAYONET.get());

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
        return 0.5F * super.accuracyModifier(ply);
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        return Rarity.EPIC;
    }

    @Override
    public boolean allowPressingTrigger(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        ItemStack secondItemStack;
        if (pUsedHand == InteractionHand.MAIN_HAND)
            secondItemStack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
        else
            secondItemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);

        if (secondItemStack.is(Items.AIR)) return true;

        return false;
    }

    @Override
    public void OnCockStart(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RIFLEBOLTBACK.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, 20);
        }
    }

    @Override
    public void OnCockEnd(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand){
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RIFLEBOLTFORW.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        setAimAnimation(gun);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, 20);
        }
    }

    @Override
    public boolean tryShoot(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        if (GetAmmoAmount(gun) == 0) return false;

        return true;
    }

    @Override
    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldown(shooter, gunStack));
        }

        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RIFLESHOOT.get(), SoundSource.NEUTRAL, 4.0F, 1.0F, 0);


        // Particles
        if (!pLevel.isClientSide()) {
            ServerLevel sLevel = (ServerLevel) pLevel;
            //Second index is your particle count. DO. NOT. TOUCH. pParticleCount.
            //I'm dead serious. I know it might be weird that the particle count is not the actual particle count, but just trust the process and don't touch it.
            //Thank you.
            for (int index0 = 0; index0 < 25; index0++) {
                double speed = 0.15;
                double spread = 0.32;

                sLevel.sendParticles(
                        ParticleTypes.FLAME,
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

    }

    @Override
    public void onAmmo(Level pLevel, LivingEntity shooter, ItemStack gun, ItemStack ammo, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.SHOTGUNRELOAD.get(), SoundSource.NEUTRAL, 5.0F, 1.0F, 0);

        setReloadAnimation(gun);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldown(shooter, gun));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.bolt_action_rifle.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.bolt_action_rifle.description_1"));


        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
