package net.onvoid.adjunct.common.item;

import net.minecraft.item.Item;

public class KnowledgeSyringeItem extends ExperienceContainerItem {

    //hardcoded in knowledge_syringe.json
    public static final int MAX = 1395;
    public KnowledgeSyringeItem(Item.Properties properties) {
        super(properties, MAX);
    }
}
