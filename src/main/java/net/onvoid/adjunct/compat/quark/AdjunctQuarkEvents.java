package net.onvoid.adjunct.compat.quark;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

// Code modified from Quark, a mod by Vazkii, all copyright belonging to Quark and its creator(s)
// Licensed under CC BY-NC-SA 3.0: https://github.com/VazkiiMods/Quark/blob/master/LICENSE.md
// File modified from: https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/content/building/module/VariantChestsModule.java

public class AdjunctQuarkEvents {

    private static final String DONK_CHEST = "Quark:DonkChest";

    @SubscribeEvent
    public void onClickEntity(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity target = event.getTarget();
        PlayerEntity player = event.getPlayer();
        ItemStack held = player.getItemInHand(event.getHand());

        if (!held.isEmpty() && target instanceof AbstractChestedHorseEntity) {
            AbstractChestedHorseEntity horse = (AbstractChestedHorseEntity) target;

            if (!horse.hasChest() && held.getItem() != Items.CHEST) {
                if (held.getItem().is(Tags.Items.CHESTS_WOODEN)) {
                    event.setCanceled(true);
                    event.setCancellationResult(ActionResultType.SUCCESS);

                    if (!target.level.isClientSide()) {
                        ItemStack copy = held.copy();
                        copy.setCount(1);
                        held.shrink(1);

                        horse.getPersistentData().put(DONK_CHEST, copy.serializeNBT());

                        horse.setChest(true);
                        horse.hasChest();
                        //horse.playChestEquipsSound(); protected access
                    }
                }
            }
        }
    }

    private static final ThreadLocal<ItemStack> WAIT_TO_REPLACE_CHEST = new ThreadLocal<>();

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        Entity target = event.getEntityLiving();
        if (target instanceof AbstractChestedHorseEntity) {
            AbstractChestedHorseEntity horse = (AbstractChestedHorseEntity) target;
            ItemStack chest = ItemStack.of(horse.getPersistentData().getCompound(DONK_CHEST));
            if (!chest.isEmpty() && horse.hasChest())
                WAIT_TO_REPLACE_CHEST.set(chest);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity target = event.getEntity();
        if (target instanceof ItemEntity && ((ItemEntity) target).getItem().getItem() == Items.CHEST) {
            ItemStack local = WAIT_TO_REPLACE_CHEST.get();
            if (local != null && !local.isEmpty())
                ((ItemEntity) target).setItem(local);
            WAIT_TO_REPLACE_CHEST.remove();
        }
    }

}
