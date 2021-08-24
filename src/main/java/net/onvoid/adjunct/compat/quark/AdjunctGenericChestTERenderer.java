package net.onvoid.adjunct.compat.quark;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.Calendar;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger.ICallback;
import net.minecraft.tileentity.TileEntityMerger.ICallbackWrapper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraft.tileentity.ChestTileEntity;

// Code modified from Quark, a mod by Vazkii, all copyright belonging to Quark and its creator(s)
// Licensed under CC BY-NC-SA 3.0: https://github.com/VazkiiMods/Quark/blob/master/LICENSE.md
// File modified from: https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/base/client/render/GenericChestTERenderer.java

public abstract class AdjunctGenericChestTERenderer<T extends TileEntity & IChestLid> extends TileEntityRenderer<T> {
    public final ModelRenderer singleLid;
    public final ModelRenderer singleBottom;
    public final ModelRenderer singleLatch;
    public final ModelRenderer rightLid;
    public final ModelRenderer rightBottom;
    public final ModelRenderer rightLatch;
    public final ModelRenderer leftLid;
    public final ModelRenderer leftBottom;
    public final ModelRenderer leftLatch;
    public boolean isChristmas;

    public AdjunctGenericChestTERenderer(TileEntityRendererDispatcher disp) {
        super(disp);
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26) {
            this.isChristmas = true;
        }

        this.singleBottom = new ModelRenderer(64, 64, 0, 19);
        this.singleBottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
        this.singleLid = new ModelRenderer(64, 64, 0, 0);
        this.singleLid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
        this.singleLid.y = 9.0F;
        this.singleLid.z = 1.0F;
        this.singleLatch = new ModelRenderer(64, 64, 0, 0);
        this.singleLatch.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
        this.singleLatch.y = 8.0F;
        this.rightBottom = new ModelRenderer(64, 64, 0, 19);
        this.rightBottom.addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.rightLid = new ModelRenderer(64, 64, 0, 0);
        this.rightLid.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.rightLid.y = 9.0F;
        this.rightLid.z = 1.0F;
        this.rightLatch = new ModelRenderer(64, 64, 0, 0);
        this.rightLatch.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.rightLatch.y = 8.0F;
        this.leftBottom = new ModelRenderer(64, 64, 0, 19);
        this.leftBottom.addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.leftLid = new ModelRenderer(64, 64, 0, 0);
        this.leftLid.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.leftLid.y = 9.0F;
        this.leftLid.z = 1.0F;
        this.leftLatch = new ModelRenderer(64, 64, 0, 0);
        this.leftLatch.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.leftLatch.y = 8.0F;
    }

    public void render(T p_225616_1_, float p_225616_2_, MatrixStack p_225616_3_, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
        World world = p_225616_1_.getLevel();
        boolean flag = world != null;
        BlockState blockstate = flag ? p_225616_1_.getBlockState() : (BlockState)Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        ChestType chesttype = blockstate.getValues().containsKey(ChestBlock.TYPE) ? (ChestType)blockstate.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockstate.getBlock();
        if (block instanceof AbstractChestBlock) {
            AbstractChestBlock<?> abstractchestblock = (AbstractChestBlock)block;
            boolean flag1 = chesttype != ChestType.SINGLE;
            p_225616_3_.pushPose();
            float f = ((Direction)blockstate.getValue(ChestBlock.FACING)).toYRot();
            p_225616_3_.translate(0.5D, 0.5D, 0.5D);
            p_225616_3_.mulPose(Vector3f.YP.rotationDegrees(-f));
            p_225616_3_.translate(-0.5D, -0.5D, -0.5D);
            ICallbackWrapper<? extends ChestTileEntity> icallbackwrapper;
            if (flag) {
                icallbackwrapper = abstractchestblock.combine(blockstate, world, p_225616_1_.getBlockPos(), true);
            } else {
                icallbackwrapper = ICallback::acceptNone;
            }

            float f1 = ((Float2FloatFunction)icallbackwrapper.apply(ChestBlock.opennessCombiner((IChestLid)p_225616_1_))).get(p_225616_2_);
            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            int i = ((Int2IntFunction)icallbackwrapper.apply(new DualBrightnessCallback())).applyAsInt(p_225616_5_);
            RenderMaterial material = this.getMaterialFinal(p_225616_1_, chesttype);
            if (material != null) {
                IVertexBuilder ivertexbuilder = material.buffer(p_225616_4_, RenderType::entityCutout);
                if (flag1) {
                    if (chesttype == ChestType.LEFT) {
                        this.render(p_225616_3_, ivertexbuilder, this.leftLid, this.leftLatch, this.leftBottom, f1, i, p_225616_6_);
                    } else {
                        this.render(p_225616_3_, ivertexbuilder, this.rightLid, this.rightLatch, this.rightBottom, f1, i, p_225616_6_);
                    }
                } else {
                    this.render(p_225616_3_, ivertexbuilder, this.singleLid, this.singleLatch, this.singleBottom, f1, i, p_225616_6_);
                }
            }

            p_225616_3_.popPose();
        }

    }

    public final RenderMaterial getMaterialFinal(T t, ChestType type) {
        return this.isChristmas ? Atlases.chooseMaterial(t, type, this.isChristmas) : this.getMaterial(t, type);
    }

    public abstract RenderMaterial getMaterial(T var1, ChestType var2);

    public void render(MatrixStack matrixStack, IVertexBuilder builder, ModelRenderer chestLid, ModelRenderer chestLatch, ModelRenderer chestBottom, float lidAngle, int combinedLightIn, int combinedOverlayIn) {
        chestLid.xRot = -(lidAngle * ((float)Math.PI / 2F));
        chestLatch.xRot = chestLid.xRot;
        chestLid.render(matrixStack, builder, combinedLightIn, combinedOverlayIn);
        chestLatch.render(matrixStack, builder, combinedLightIn, combinedOverlayIn);
        chestBottom.render(matrixStack, builder, combinedLightIn, combinedOverlayIn);
    }
}
