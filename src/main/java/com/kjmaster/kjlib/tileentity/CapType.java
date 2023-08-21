package com.kjmaster.kjlib.tileentity;

import com.kjmaster.kjlib.api.container.CapabilityContainerProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public enum CapType {
    ITEMS(ForgeCapabilities.ITEM_HANDLER),
    ITEMS_AUTOMATION(ForgeCapabilities.ITEM_HANDLER),
    CONTAINER(CapabilityContainerProvider.CONTAINER_PROVIDER_CAPABILITY),
    ENERGY(ForgeCapabilities.ENERGY),
    FLUIDS(ForgeCapabilities.FLUID_HANDLER);

    private final Capability capability;

    CapType(Capability capability) {
        this.capability = capability;
    }

    public Capability getCapability() {
        return capability;
    }
}
