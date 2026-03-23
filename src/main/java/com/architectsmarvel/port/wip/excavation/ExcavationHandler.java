package com.architectsmarvel.port.wip.excavation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
// @EventBusSubscriber(modid = com.architectsmarvel.port.ArchitectsMarvelPort.MOD_ID) // WIP — disabled
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.joml.Vector3f;

// WIP: Excavation enchantment handler — disabled, moved out of active package
public final class ExcavationHandler {
    private static final Map<UUID, MiningStreak> STREAKS = new HashMap<>();

    private ExcavationHandler() {}

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        ItemStack tool = player.getMainHandItem();
        if (tool.isEmpty()) {
            STREAKS.remove(player.getUUID());
            return;
        }

        int level = getExcavationLevel(player.registryAccess(), tool);
        if (level <= 0) {
            STREAKS.remove(player.getUUID());
            return;
        }

        long gameTime = player.level().getGameTime();
        MiningStreak streak = updateStreak(player.getUUID(), gameTime, tool, level);

        float progress = Math.min(1.0F, streak.holdTicks / (float) ModEnchantments.EXCAVATION_MAX_CHARGE_TICKS);
        float maxMultiplier = switch (level) {
            case 1 -> 1.75F;
            case 2 -> 2.35F;
            default -> 3.0F; // level 3
        };
        float multiplier = 1.0F + (maxMultiplier - 1.0F) * progress;

        event.setNewSpeed(event.getNewSpeed() * multiplier);

        if (progress >= 1.0F && player.level() instanceof ServerLevel serverLevel) {
            maybeSpawnMaxChargeParticles(serverLevel, player, streak);
        }
    }

    private static MiningStreak updateStreak(UUID uuid, long gameTime, ItemStack tool, int level) {
        MiningStreak prev = STREAKS.get(uuid);
        String sig = signature(tool, level);

        // Keep streak between neighboring breaks while mouse is held;
        // reset only after a noticeable pause or tool/enchant change.
        if (prev == null || !prev.signature.equals(sig) || gameTime - prev.lastTick > 12) {
            MiningStreak next = new MiningStreak(sig, gameTime, 1, 0);
            STREAKS.put(uuid, next);
            return next;
        }

        int ticks = prev.holdTicks + Math.max(1, (int) (gameTime - prev.lastTick));
        MiningStreak next = new MiningStreak(sig, gameTime, ticks, prev.lastParticleTick);
        STREAKS.put(uuid, next);
        return next;
    }

    private static void maybeSpawnMaxChargeParticles(ServerLevel level, Player player, MiningStreak streak) {
        long now = level.getGameTime();
        if (now - streak.lastParticleTick < 6) {
            return;
        }

        level.sendParticles(
            new DustParticleOptions(new Vector3f(1.0F, 0.1F, 0.1F), 0.8F),
            player.getX(),
            player.getY() + 1.0,
            player.getZ(),
            4,
            0.2,
            0.2,
            0.2,
            0.0
        );

        STREAKS.put(player.getUUID(), new MiningStreak(streak.signature, streak.lastTick, streak.holdTicks, now));
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

    private static String signature(ItemStack stack, int level) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()) + "#lvl=" + level;
    }

    private record MiningStreak(String signature, long lastTick, int holdTicks, long lastParticleTick) {}
}
