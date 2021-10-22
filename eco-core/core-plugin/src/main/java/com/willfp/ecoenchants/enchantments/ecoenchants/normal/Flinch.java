package com.willfp.ecoenchants.enchantments.ecoenchants.normal;

import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class Flinch extends EcoEnchant {
    public Flinch() {
        super(
                "flinch", EnchantmentType.NORMAL
        );
    }

    @Override
    public String getPlaceholder(final int level) {
        return EnchantmentUtils.chancePlaceholder(this, level);
    }

    @Override
    public void onDeflect(@NotNull final Player blocker,
                          @NotNull final LivingEntity attacker,
                          final int level,
                          @NotNull final EntityDamageByEntityEvent event) {
        int duration = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "ticks-per-level");

        if (!EnchantmentUtils.passedChance(this, level)) {
            return;
        }

        int finalDuration = duration * level;

        attacker.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, finalDuration, 1, false, false, false));
    }
}
