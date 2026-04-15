package org.ragingzombies.flintnpowder.handlers;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.ragingzombies.flintnpowder.Flintnpowder;

@OnlyIn(Dist.CLIENT)
public class AttachmentRenderer extends BlockEntityWithoutLevelRenderer {
    public static AttachmentRenderer INSTANCE;

    public AttachmentRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
                             MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        poseStack.pushPose();
        //renderBaseTextureManually(stack, poseStack, bufferSource, packedLight, packedOverlay);
        poseStack.popPose();
    }

    private void renderBaseTextureManually(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer,
                                           int packedLight, int packedOverlay) {

        // Получаем спрайт базовой текстуры
        ResourceLocation textureLocation = new ResourceLocation(
                Flintnpowder.MOD_ID,
                "textures/item/" + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + ".png"
        );

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(textureLocation);

        VertexConsumer vertex = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocation));

        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();

        float minU = sprite.getU0();
        float maxU = sprite.getU1();
        float minV = sprite.getV0();
        float maxV = sprite.getV1();

        // Размер предмета (обычно 1.0 для плоского предмета)
        float size = 1.0f;

        // Рисуем квадрат с текстурой
        vertex.vertex(matrix, 0, size, 0).color(1f, 1f, 1f, 1f).uv(minU, maxV).overlayCoords(packedOverlay).uv2(packedLight).normal(0, 1, 0).endVertex();
        vertex.vertex(matrix, size, size, 0).color(1f, 1f, 1f, 1f).uv(maxU, maxV).overlayCoords(packedOverlay).uv2(packedLight).normal(0, 1, 0).endVertex();
        vertex.vertex(matrix, size, 0, 0).color(1f, 1f, 1f, 1f).uv(maxU, minV).overlayCoords(packedOverlay).uv2(packedLight).normal(0, 1, 0).endVertex();
        vertex.vertex(matrix, 0, 0, 0).color(1f, 1f, 1f, 1f).uv(minU, minV).overlayCoords(packedOverlay).uv2(packedLight).normal(0, 1, 0).endVertex();
    }
}