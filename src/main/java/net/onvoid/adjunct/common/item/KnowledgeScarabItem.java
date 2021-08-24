package net.onvoid.adjunct.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.AdjunctHelper;

import javax.annotation.Nullable;
import java.util.List;

public class KnowledgeScarabItem extends ExperienceContainerItem {

    //hardcoded in knowledge_scarab.json
    public static final int MAX = 10000;
    public KnowledgeScarabItem(Item.Properties properties) {
        super(properties, MAX);
    }
}
