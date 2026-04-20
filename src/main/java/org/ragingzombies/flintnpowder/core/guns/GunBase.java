package org.ragingzombies.flintnpowder.core.guns;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.util.Lazy;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.attachments.AttachmentBase;
import org.ragingzombies.flintnpowder.enchantments.ModEnchantments;
import org.ragingzombies.flintnpowder.handlers.AttachmentRenderer;
import org.ragingzombies.flintnpowder.handlers.ClientModHandler;
import org.ragingzombies.flintnpowder.sound.ModSounds;
import org.spongepowered.asm.mixin.Mutable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.ragingzombies.flintnpowder.core.attachments.AttachmentBase.attachmentTypes;

public class GunBase extends Item {

    protected final Lazy<Multimap<Attribute, AttributeModifier>> lazyAttributeMap;

    public int cooldownTicks = 20;
    public int shootCooldownTicks = 20;
    public int reloadPitch = 1;

    public List<Item> allowedAmmo = new ArrayList<>();
    public List<Item> allowedAttachments = new ArrayList<>();

    public GunBase(Properties pProperties) {
        super(pProperties);

        this.lazyAttributeMap = Lazy.of(() -> {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                            BASE_ATTACK_DAMAGE_UUID,
                            "Weapon modifier",
                            2,
                            AttributeModifier.Operation.ADDITION
                    ));
            builder.put(Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                            BASE_ATTACK_SPEED_UUID,
                            "Weapon modifier",
                            -2.4,
                            AttributeModifier.Operation.ADDITION
                    ));

            return builder.build();
        });
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == ModEnchantments.QUALITY_PROPELLANT.get() || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 22;
    }

    public void OnCockEnd(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) { }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            return lazyAttributeMap.get();
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private static final HumanoidModel.ArmPose GUN_AIM = HumanoidModel.ArmPose.create("GUN_AIM", true, (model, entity, arm) -> {
                if (arm == HumanoidArm.RIGHT) {
                    model.body.yRot = 0.5F;

                    model.rightArm.xRot = (float) (-Math.PI*0.5F);
                    model.rightArm.x = -4;
                    model.rightArm.z = -1;

                    model.leftArm.xRot = (float) (-Math.PI*0.5F);
                    model.leftArm.yRot = (float) (Math.PI*0.25F);
                } else {
                    model.body.yRot = -0.5F;

                    model.leftArm.xRot = (float) -(Math.PI*0.5F);
                    model.leftArm.x = 4;
                    model.leftArm.z = 1;

                    model.rightArm.xRot = (float) -(Math.PI*0.5F);
                    model.rightArm.yRot = (float) -(Math.PI*0.25);
                }
            });

            private static final HumanoidModel.ArmPose GUN_RELOAD = HumanoidModel.ArmPose.create("GUN_RELOAD", true, (model, entity, arm) -> {
                if (arm == HumanoidArm.RIGHT) {
                    model.rightArm.xRot = (float) (-Math.PI*0.25F);
                    model.rightArm.yRot = (float) -(Math.PI*0.15F);
                    model.rightArm.zRot = (float) -(Math.PI*0.05F);

                    model.leftArm.xRot = (float) (-Math.PI*0.25F);
                    model.leftArm.yRot = (float) (Math.PI*0.25F);
                } else {
                    model.leftArm.xRot = (float) (-Math.PI*0.25F);
                    model.leftArm.yRot = (float) (Math.PI*0.15F);
                    model.leftArm.zRot = (float) (Math.PI*0.05F);

                    model.rightArm.xRot = (float) (-Math.PI*0.25F);
                    model.rightArm.yRot = (float) -(Math.PI*0.25F);
                }
            });


            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ClientModHandler.getRenderer();
            }

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                if (!itemStack.isEmpty()) {
                    if (itemStack.getOrCreateTag().getBoolean("IsAiming")) {
                        return GUN_AIM;
                    } else {
                        return GUN_RELOAD;
                    }
                }
                return HumanoidModel.ArmPose.EMPTY;
            }
        });
    }

    public void setAimAnimation(ItemStack gun) {
        gun.getOrCreateTag().putBoolean("IsAiming", true);
    }
    public void setReloadAnimation(ItemStack gun) {
        gun.getOrCreateTag().putBoolean("IsAiming", false);
    }

    public int shootCooldown(LivingEntity ply, ItemStack gun) {
        return shootCooldownTicks;
    }

    public void addAllowedAmmo(Item ammo) {
        allowedAmmo.add(ammo);
    }

    public void addAllowedAttachment(Item attach) {
        allowedAttachments.add(attach);
    }

    public boolean checkAmmo(Item ammo) {
        for (Item a : allowedAmmo) {
            if (ammo.getClass() == a.getClass()) {
                return true;
            }
        }

        return false;
    }

    public boolean checkAttachmentComparability(Player ply, ItemStack gun, Item attachment) {
        for (Item a : allowedAttachments) {
            if (attachment.getClass() == a.getClass()) {
                return true;
            }
        }

        return false;
    }

    public void setAttachment(Player ply, ItemStack gun, ItemStack attachment) {
        CompoundTag attachmentData = gun.getTag().getCompound("Attachments");
        String attachType = ((AttachmentBase) attachment.getItem()).getType();

        // Return old attachment
        if (isAttachmentValidAndEnabled(gun, attachType)) {
            detachAttachment(ply, gun, attachType);
        }

        CompoundTag newAttachments = new CompoundTag();
        newAttachments.putBoolean("enabled", true);

        CompoundTag attachItem = attachment.serializeNBT();
        newAttachments.put("item", attachItem);
        attachmentData.put(attachType, newAttachments);

        ((AttachmentBase) attachment.getItem()).onAttach(ply, attachment, gun);

        onAttachmentAttach(ply, gun, attachment);

        gun.getTag().put("Attachments", attachmentData);
    }

    public void detachAttachment(Player ply, ItemStack gun, String type) {
        ItemStack detached = getAttachmentStack(gun, type);
        ((AttachmentBase) detached.getItem()).onDetach(ply, detached, gun);
        onAttachmentDetach(ply, gun, type);
        ply.getInventory().add(detached);
        gun.getOrCreateTag().getCompound("Attachments").getCompound(type).putBoolean("enabled", false);
    }

    public ItemStack getAttachmentStack(ItemStack gun, String type) {
        CompoundTag attachmentsData = gun.getOrCreateTag().getCompound("Attachments");

        if (!attachmentsData.getCompound(type).getBoolean("enabled")) {
            return new ItemStack(Items.AIR);
        }

        CompoundTag item = attachmentsData.getCompound(type).getCompound("item");
        ItemStack deserializedAttachment = ItemStack.of( item );
        deserializedAttachment.deserializeNBT(attachmentsData.getCompound(type).getCompound("item"));

        return deserializedAttachment;
    }

    public boolean isAttachmentValidAndEnabled(ItemStack gun, String type) {
        return (getAttachmentStack(gun, type).getItem() != Items.AIR);
    }

    public Item getAttachmentItem(ItemStack gun, String type) {
        return getAttachmentStack(gun, type).getItem();
    }

    public String getAttachmentName(ItemStack gun, String type) {
        return getAttachmentStack(gun, type).getDisplayName().getString();
    }

    public void onAttachmentAttach(Player ply, ItemStack gun, ItemStack attachment) {}
    public void onAttachmentDetach(Player ply, ItemStack gun, String type) {}
    //


    public boolean allowPressingTrigger(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        return true;
    }

    public boolean tryShoot(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        return true;
    }

    public void onTryFailure(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {
        pLevel.playSeededSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                ModSounds.FLINTSTRIKE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);
    }


    public float damageModifier(UUID shooter, ItemStack gun) {
        int amoLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.QUALITY_PROPELLANT.get(), gun);
        return 1 + amoLevel*0.10F;
    }

    public float recoilModifierX(UUID id, ItemStack gun) {
        return 1;
    }
    public float recoilModifierY(UUID id, ItemStack gun) {
        return 1;
    }
    public float accuracyModifier(UUID id, ItemStack gun) {
        return 1;
    }


    public void Shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {}

    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.FLINTPRIME.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldown(shooter, gunStack));
        }
    }

    public void onAmmo(Level pLevel, LivingEntity shooter, ItemStack gun, ItemStack ammo ,InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RIFLERELOAD.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldown(shooter, gun));
        }
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(""));

        int totalAttach = 0;
        for (String type : attachmentTypes) {
            if (isAttachmentValidAndEnabled(pStack, type)) {
                ItemStack item = getAttachmentStack(pStack, type);
                pTooltipComponents.add(Component.translatable("flintnpowder.attachment").append(item.getDisplayName()));
                item.getItem().appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

                totalAttach++;
            }
        }
        if (totalAttach > 0) {
            pTooltipComponents.add(Component.literal(""));
        }


        if (!Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("flintnpowder.guninfoshift"));
            pTooltipComponents.add(Component.literal(""));
        } else {
            pTooltipComponents.add(Component.translatable("flintnpowder.guninfoammo"));
            for (Item ammo : allowedAmmo) {
                pTooltipComponents.add(Component.literal("   ").append((new ItemStack(ammo)).getDisplayName()));
            }

            pTooltipComponents.add(Component.literal(""));

            if (!allowedAttachments.isEmpty()) {
                pTooltipComponents.add(Component.translatable("flintnpowder.guninfoattachment"));
                for (Item ammo : allowedAttachments) {
                    pTooltipComponents.add(Component.literal("   ").append((new ItemStack(ammo)).getDisplayName()));
                }
            } else {
                pTooltipComponents.add(Component.translatable("flintnpowder.guninfonoattachment"));
            }

            pTooltipComponents.add(Component.literal(""));
        }
        //

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

}
