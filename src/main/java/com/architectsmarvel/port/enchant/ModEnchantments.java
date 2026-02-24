package com.architectsmarvel.port.enchant;

import com.architectsmarvel.port.ArchitectsMarvelPort;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public final class ModEnchantments {
    public static final ResourceKey<Enchantment> EXCAVATION = ResourceKey.create(
        Registries.ENCHANTMENT,
        ResourceLocation.fromNamespaceAndPath(ArchitectsMarvelPort.MOD_ID, "excavation")
    );

    private ModEnchantments() {}
}
