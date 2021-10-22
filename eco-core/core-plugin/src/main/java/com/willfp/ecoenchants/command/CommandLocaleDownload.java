package com.willfp.ecoenchants.command;

import com.willfp.eco.core.command.CommandHandler;
import com.willfp.eco.core.command.impl.Subcommand;
import com.willfp.eco.core.config.interfaces.Config;
import com.willfp.eco.core.config.yaml.YamlTransientConfig;
import com.willfp.eco.core.web.Paste;
import com.willfp.ecoenchants.EcoEnchantsPluginImpl;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CommandLocaleDownload extends Subcommand {
    /**
     * Instantiate a new /ecoenchants locale download command handler.
     *
     * @param plugin The plugin for the commands to listen for.
     */
    public CommandLocaleDownload(@NotNull final EcoEnchantsPluginImpl plugin) {
        super(plugin, "download", "ecoenchants.command.locale.download", false);
    }

    @Override
    public CommandHandler getHandler() {
        return (sender, args) -> {
            if (args.isEmpty()) {
                sender.sendMessage(this.getPlugin().getLangYml().getMessage("invalid-locale"));
                return;
            }

            Paste paste = Paste.getFromHastebin(args.get(0));
            if (paste == null) {
                sender.sendMessage(this.getPlugin().getLangYml().getMessage("invalid-locale"));
                return;
            }

            String contents = paste.getContents();
            Config configuration = new YamlTransientConfig(contents);

            for (String key : configuration.getKeys(true)) {
                this.getPlugin().getLangYml().set(key, configuration.get(key));
            }

            try {
                this.getPlugin().getLangYml().save();
                this.getPlugin().getLangYml().clearCache();

                for (EcoEnchant value : EcoEnchants.values()) {
                    value.getConfig().loadFromLang();
                    value.getConfig().save();
                    value.getConfig().clearCache();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            sender.sendMessage(this.getPlugin().getLangYml().getMessage("downloaded-locale"));
        };
    }
}
