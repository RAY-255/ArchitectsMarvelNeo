package com.architectsmarvel.port.enchant;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = com.architectsmarvel.port.ArchitectsMarvelPort.MOD_ID)
public final class ExcavationHandler {
    private static final Map<UUID, MiningStreak> STREAKS = new HashMap<>();

    private ExcavationHandler() {}

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        ItemStack tool = player.getMainHandItem();
        if (tool.isEmpty()) {
            return;
        }

        int level = getExcavationLevel(player.registryAccess(), tool);
        if (level <= 0) {
            STREAKS.remove(player.getUUID());
            return;
        }

        long gameTime = player.level().getGameTime();
        int holdTicks = updateAndGetHoldTicks(player.getUUID(), gameTime, tool);

        float progress = Math.min(1.0F, holdTicks / 60.0F);
        float maxMultiplier = 1.0F + (level / 3.0F);
        float multiplier = 1.0F + (maxMultiplier - 1.0F) * progress;

        event.setNewSpeed(event.getNewSpeed() * multiplier);
    }

    private static int updateAndGetHoldTicks(UUID uuid, long gameTime, ItemStack tool) {
        MiningStreak prev = STREAKS.get(uuid);
        String sig = signature(tool);

        if (prev == null || !prev.signature.equals(sig) || gameTime - prev.lastTick > 1) {
            MiningStreak next = new MiningStreak(sig, gameTime, 1);
            STREAKS.put(uuid, next);
            return 1;
        }

        MiningStreak next = new MiningStreak(sig, gameTime, prev.holdTicks + 1);
        STREAKS.put(uuid, next);
        return next.holdTicks;
    }

    private static int getExcavationLevel(RegistryAccess registryAccess, ItemStack stack) {
        Holder<Enchantment> holder = registryAccess.lookupOrThrow(Registries.ENCHANTMENT)
            .get(ModEnchantments.EXCAVATION)
            .orElse(null);
        if (holder == null) {
            return 0;
        }
        return EnchantmentHelper.getTagEnchantmentLevel(holder, stack);
    }

    private static String signature(ItemStack stack) {
        return stack.getItem().toString() + "#" + stack.getDamageValue();
    }

    private record MiningStreak(String signature, long lastTick, int holdTicks) {}
}
