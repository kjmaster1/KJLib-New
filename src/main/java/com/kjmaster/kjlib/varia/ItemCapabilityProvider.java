package com.kjmaster.kjlib.varia;

import com.kjmaster.kjlib.api.power.IBigPower;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class ItemCapabilityProvider implements ICapabilityProvider, IBigPower {

    public final ItemStack itemStack;
    public final IEnergyItem item;

    public final LazyOptional<IEnergyStorage> energy = LazyOptional.of(this::createEnergyStorage);

    @Nonnull
    public  <T> IEnergyStorage createEnergyStorage() {
        return new IEnergyStorage() {
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                return (int)item.receiveEnergyL(itemStack, maxReceive, simulate);
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                return (int)item.extractEnergyL(itemStack, maxExtract, simulate);
            }

            @Override
            public int getEnergyStored() {
                return EnergyTools.getIntEnergyStored(item.getEnergyStoredL(itemStack), item.getMaxEnergyStoredL(itemStack));
            }

            @Override
            public int getMaxEnergyStored() {
                return EnergyTools.unsignedClampToInt(item.getMaxEnergyStoredL(itemStack));
            }

            @Override
            public boolean canExtract() {
                return true;
            }

            @Override
            public boolean canReceive() {
                return true;
            }
        };
    }

    public ItemCapabilityProvider(ItemStack itemStack, IEnergyItem item) {
        this.itemStack = itemStack;
        this.item = item;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability) {
        if (capability == ForgeCapabilities.ENERGY) {
            return energy.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public long getStoredPower() {
        return item.getEnergyStoredL(itemStack);
    }

    @Override
    public long getCapacity() {
        return item.getMaxEnergyStoredL(itemStack);
    }
}
