package com.kjmaster.kjlib.modules;

import com.kjmaster.kjlib.datagen.DataGen;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public interface IModule {
    void init(FMLCommonSetupEvent event);
    void initClient(FMLClientSetupEvent event);

    default void initDatagen(DataGen dataGen) {}
}
