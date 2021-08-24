package net.onvoid.adjunct;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class AdjunctHelper {
    public static void giveToHand(PlayerEntity player, Hand hand, ItemStack stack){
        if (player.getItemInHand(hand).isEmpty()){
            player.setItemInHand(hand, stack);
            return;
        }
        ItemHandlerHelper.giveItemToPlayer(player, stack, player.inventory.selected);
    }

    public static ITextComponent styleComponent(ITextComponent component, Color color){
        if (color == null){
            return component;
        }
        return ((IFormattableTextComponent)component).withStyle(component.getStyle().withColor(color));
    }

    public static void setDisplayNameColor(List<ITextComponent> tooltip, Color color){
        tooltip.set(0, styleComponent(tooltip.get(0), color));
    }

    public static int compareStrings(String str1, String str2){
        for (int i = 0; i < Math.min(str1.length(), str2.length()); i++){
            int str1_val = (int) str1.charAt(i);
            int str2_val = (int) str2.charAt(i);
            if (str1_val > str2_val){
                return str1_val - str2_val;
            } else if (str2_val > str1_val){
                return str2_val - str1_val;
            }
            continue;
        }
        return 0;
    }

    public static boolean isBottle(ItemStack stack){
        return !stack.isEmpty() && stack.getItem().getRegistryName().toString().contains("bottle");
    }

    public static boolean isBucket(ItemStack stack){
        return !stack.isEmpty() && stack.getItem().getRegistryName().toString().contains("bucket");
    }

    public static void setPlayerExp(PlayerEntity player, int amount){
        player.experienceLevel = 0;
        player.experienceProgress = 0.0F;
        player.totalExperience = 0;
        player.giveExperiencePoints(amount);
    }

    public static void addPlayerExp(PlayerEntity player, int amount){
        setPlayerExp(player, player.totalExperience + amount > 0 ? player.totalExperience + amount : 0);
    }

    public static int exp_from_level(int level){
        return level >= 32 ? (9 * level * level - 325 * level + 4440) / 2 : level >= 17 ? (5 * level * level - 81 * level + 720) / 2 : (level * level + 6 * level);
    }

    public static int level_from_exp(int exp){
        if (exp >= 1395){
            return 30;
        } else if (exp >= 1288){
            return 29;
        } else if (exp >= 1186){
            return 28;
        } else if (exp >= 1098){
            return 27;
        } else if (exp >= 997){
            return 26;
        } else if (exp >= 910){
            return 25;
        } else if (exp >= 828){
            return 24;
        } else if (exp >= 751){
            return 23;
        } else if (exp >= 679){
            return 22;
        } else if (exp >= 612){
            return 21;
        } else if (exp >= 550){
            return 20;
        } else if (exp >= 493){
            return 19;
        } else if (exp >= 441){
            return 18;
        } else if (exp >= 394){
            return 17;
        } else if (exp >= 352){
            return 16;
        } else if (exp >= 315){
            return 15;
        } else if (exp >= 280){
            return 14;
        } else if (exp >= 247){
            return 13;
        } else if (exp >= 216){
            return 12;
        } else if (exp >= 187){
            return 11;
        } else if (exp >= 160){
            return 10;
        } else if (exp >= 135){
            return 9;
        } else if (exp >= 112){
            return 8;
        } else if (exp >= 91){
            return 7;
        } else if (exp >= 72){
            return 6;
        } else if (exp >= 55){
            return 5;
        } else if (exp >= 40){
            return 4;
        } else if (exp >= 27){
            return 3;
        } else if (exp >= 16){
            return 2;
        } else if (exp >= 7){
            return 1;
        }
        return 0;
    }
}
