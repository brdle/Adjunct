package net.onvoid.adjunct.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemModelGenerator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.onvoid.adjunct.common.block.PizzaOvenBlock;
import net.onvoid.adjunct.common.item.AdjunctItems;
import net.onvoid.adjunct.common.tile.PizzaOvenTile;

public class PizzaOvenTileRenderer extends TileEntityRenderer<PizzaOvenTile> {

    public PizzaOvenTileRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    public int itemBrightness = 15728640;

    @Override
    public void render(PizzaOvenTile tile, float partialTicks, MatrixStack matrices, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
        if (!tile.isRemoved() && tile.getLevel().isClientSide()) {
            IItemHandler handler = (IItemHandler) tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
            if (handler != null) {
                ItemStack stack = handler.getStackInSlot(0);
                if (!stack.isEmpty()) {
                    matrices.pushPose(); {
                        matrices.translate(0.5D, 0.391D, 0.5D);
                        //matrices.scale(0.75F, 0.75F, 0.75F);
                        Direction facing = tile.getBlockState().getValue(PizzaOvenBlock.FACING);
                        matrices.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                        if (facing.equals(Direction.EAST)) {
                            matrices.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
                        } else if (facing.equals(Direction.SOUTH)) {
                            matrices.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
                        } else if (facing.equals(Direction.WEST)) {
                            matrices.mulPose(Vector3f.ZP.rotationDegrees(270.0F));
                        }
                        //itemBrightness = WorldRenderer.getLightColor(tile.getLevel(), tile.getBlockPos().above());
                        Minecraft.getInstance().getItemRenderer().renderStatic(null, stack, ItemCameraTransforms.TransformType.FIXED, true, matrices, buffer, null, itemBrightness, combinedOverlayIn);
                    } matrices.popPose();
                }
            }
        }
    }
}