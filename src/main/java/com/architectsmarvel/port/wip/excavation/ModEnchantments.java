package com.architectsmarvel.port.wip.excavation;

import com.architectsmarvel.port.ArchitectsMarvelPort;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

// WIP: Excavation enchantment constants — disabled, moved out of active package
public final class ModEnchantments {
    public static final int EXCAVATION_MAX_CHARGE_TICKS = 60; // 3 seconds at 20 TPS

    public static final ResourceKey<Enchantment> EXCAVATION = ResourceKey.create(
        Registries.ENCHANTMENT,
        ResourceLocation.fromNamespaceAndPath(ArchitectsMarvelPort.MOD_ID, "excavation")
    );

    private ModEnchantments() {}
}
