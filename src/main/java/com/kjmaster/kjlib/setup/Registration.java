package com.kjmaster.kjlib.setup;

import com.kjmaster.kjlib.KJLib;
import com.kjmaster.kjlib.crafting.CopyNBTRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class Registration {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, KJLib.MODID);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        RECIPE_SERIALIZERS.register(bus);
    }

    public static RegistryObject<CopyNBTRecipeSerializer> COPYNBT_SERIALIZER = RECIPE_SERIALIZERS.register("copy_nbt", CopyNBTRecipeSerializer::new);

}
