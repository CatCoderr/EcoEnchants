package com.willfp.ecoenchants.enchantments.ecoenchants.special;

import com.willfp.eco.util.LightningUtils;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

public class Bolt extends EcoEnchant {
    public Bolt() {
        super(
                "bolt", EnchantmentType.SPECIAL
        );
    }

    @Override
    public String getPlaceholder(final int level) {
        return EnchantmentUtils.chancePlaceholder(this, level);
    }

    @Override
    public void onMeleeAttack(@NotNull final LivingEntity attacker,
                              @NotNull final LivingEntity victim,
                              final int level,
                              @NotNull final EntityDamageByEntityEvent event) {
        if (!EnchantmentUtils.isFullyChargeIfRequired(this, attacker)) {
            return;
        }
        if (!EnchantmentUtils.passedChance(this, level)) {
            return;
        }

        double damage = this.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION + "lightning-damage");

        boolean silent = this.getConfig().getBool(EcoEnchants.CONFIG_LOCATION + "local-lightning-sound");
        LightningUtils.strike(victim, damage, silent);
    }
}
