package net.onvoid.adjunct.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.onvoid.adjunct.common.block.PizzaOvenBlock;
import net.onvoid.adjunct.common.block.PizzaStationBlock;
import net.onvoid.adjunct.common.tile.PizzaStationTile;

public class PizzaStationTileRenderer extends TileEntityRenderer<PizzaStationTile> {

    public PizzaStationTileRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(PizzaStationTile tile, float partialTicks, MatrixStack matrices, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
        if (!tile.isRemoved() && tile.getLevel().isClientSide()) {
            IItemHandler handler = (IItemHandler) tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
            if (handler != null) {
                ItemStack stack = handler.getStackInSlot(0);
                if (!stack.isEmpty()) {
                    matrices.pushPose(); {
                        matrices.translate(0.5D, 1.016D, 0.5D);
                        Direction facing = tile.getBlockState().getValue(PizzaStationBlock.FACING);
                        matrices.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                        if (facing.equals(Direction.EAST)) {
                            matrices.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
                        } else if (facing.equals(Direction.SOUTH)) {
                            matrices.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
                        } else if (facing.equals(Direction.WEST)) {
                            matrices.mulPose(Vector3f.ZP.rotationDegrees(270.0F));
                        }
                        Minecraft.getInstance().getItemRenderer().renderStatic(null, stack, ItemCameraTransforms.TransformType.FIXED, true, matrices, buffer, null, WorldRenderer.getLightColor(tile.getLevel(), tile.getBlockPos().above()), combinedOverlayIn);
                    } matrices.popPose();
                }
            }
        }
    }
}