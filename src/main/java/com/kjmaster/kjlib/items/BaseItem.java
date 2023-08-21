package com.kjmaster.kjlib.items;

import com.kjmaster.kjlib.api.ITabExpander;
import com.kjmaster.kjlib.util.helpers.StringHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.kjmaster.kjlib.util.helpers.StringHelper.getTextComponent;
import static net.minecraft.ChatFormatting.GRAY;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class BaseItem extends Item implements ITabExpander {

    public BaseItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public List<ItemStack> getItemsForTab() {
        return Collections.emptyList();
    }

    protected void tooltipDelegate(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        List<Component> additionalTooltips = new ArrayList<>();
        tooltipDelegate(pStack, pLevel, additionalTooltips, pIsAdvanced);

        if (!additionalTooltips.isEmpty()) {
            if (Screen.hasShiftDown()) {
                pTooltipComponents.addAll(additionalTooltips);
            } else {
                pTooltipComponents.add(getTextComponent("info.cofh.hold_shift_for_details").withStyle(GRAY));
            }
        }
    }
}
