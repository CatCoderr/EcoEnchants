package com.willfp.ecoenchants.enchantments.ecoenchants.normal;

import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public class Kinetic extends EcoEnchant {
    public Kinetic() {
        super(
                "kinetic", EnchantmentType.NORMAL
        );
    }

    @Override
    public void onDamageWearingArmor(@NotNull final LivingEntity victim,
                                     final int level,
                                     @NotNull final EntityDamageEvent event) {
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.FLY_INTO_WALL)) {
            return;
        }

        double reduction = this.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION + "reduction-per-level");
        double multiplier = 1 - ((reduction / 100) * level);
        event.setDamage(event.getDamage() * multiplier);
    }
}
