package com.willfp.ecoenchants.enchantments.ecoenchants.normal;

import com.willfp.eco.util.VectorUtils;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class Hook extends EcoEnchant {
    public Hook() {
        super(
                "hook", EnchantmentType.NORMAL
        );
    }

    @Override
    public void onArrowDamage(@NotNull final LivingEntity attacker,
                              @NotNull final LivingEntity victim,
                              @NotNull final Arrow arrow,
                              final int level,
                              @NotNull final EntityDamageByEntityEvent event) {
        double baseMultiplier = this.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION + "velocity-multiplier");
        Vector vector = attacker.getLocation().toVector().clone().subtract(victim.getLocation().toVector()).normalize().multiply(level * baseMultiplier);
        if (VectorUtils.isFinite(vector)) {
            victim.setVelocity(vector);
        }
    }
}
