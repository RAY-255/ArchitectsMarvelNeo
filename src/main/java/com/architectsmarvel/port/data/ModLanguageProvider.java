package com.architectsmarvel.port.data;

import com.architectsmarvel.port.ArchitectsMarvelPort;
import com.architectsmarvel.port.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, ArchitectsMarvelPort.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + ArchitectsMarvelPort.MOD_ID + ".main", "ArchitectsMarvel Port");
        add("enchantment." + ArchitectsMarvelPort.MOD_ID + ".excavation", "Excavation");
        ModBlocks.entries().forEach((name, block) -> add(block.get(), humanize(name)));
    }

    private static String humanize(String name) {
        String[] parts = name.split("_");
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                out.append(' ');
            }
            if (parts[i].isEmpty()) {
                continue;
            }
            out.append(Character.toUpperCase(parts[i].charAt(0))).append(parts[i].substring(1));
        }
        String text = out.toString();
        if (text.startsWith("Lapis ")) {
            return text.replaceFirst("Lapis", "Lapis Lazuli");
        }
        return text;
    }
}
