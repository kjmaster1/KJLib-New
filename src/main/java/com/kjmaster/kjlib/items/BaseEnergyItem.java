package com.kjmaster.kjlib.items;

import com.kjmaster.kjlib.varia.IEnergyItem;
import com.kjmaster.kjlib.varia.ItemCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.kjmaster.kjlib.util.helpers.StringHelper.*;

public class BaseEnergyItem extends BaseItem implements IEnergyItem {

    private final boolean isReceiver;

    private final long capacity;
    private final long maxReceive;

    public BaseEnergyItem(Properties pProperties) {
        super(pProperties);
        this.isReceiver = false;
        this.capacity = 0;
        this.maxReceive = 0;
    }

    public BaseEnergyItem(Properties pProperties, boolean isReceiver, long capacity, long maxReceive) {
        super(pProperties);
        this.isReceiver = isReceiver;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
    }

    @Override
    protected void tooltipDelegate(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {

        if (getMaxEnergyStored(stack) > 0) {
            tooltip.add(getTextComponent(localize("info.cofh.energy") + ": " + getScaledNumber(getEnergyStored(stack)) + " / " + getScaledNumber(getMaxEnergyStored(stack)) + " " + localize("info.cofh.unit_rf")));
        }
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemCapabilityProvider(stack, this);
    }

    @Override
    public long receiveEnergyL(ItemStack container, long maxReceive, boolean simulate) {

        if (isReceiver) {
            if (!canReceive()) {
                return 0;
            }

            CompoundTag tag = container.getOrCreateTag();
            long energy = tag.getLong("Energy");
            long energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

            if (!simulate) {
                energy += energyReceived;
                tag.putLong("Energy", energy);
            }
            return energyReceived;
        }

        return 0;
    }

    public boolean canReceive() {
        return isReceiver;
    }

    @Override
    public long extractEnergyL(ItemStack container, long maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public long getEnergyStoredL(ItemStack container) {
        if (container.getTag() == null || !container.getTag().contains("Energy")) {
            return 0;
        }
        return container.getTag().getLong("Energy");
    }

    @Override
    public long getMaxEnergyStoredL(ItemStack container) {
        return capacity;
    }
}
