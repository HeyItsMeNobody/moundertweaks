package mounderfod.moundertweaks.util.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.Gui.Tooltip;

@Config(name = "client")
public class ClientConfig implements ConfigData {
    @Tooltip
    public boolean potionGlint = true;

    @Tooltip
    public boolean optimizedBamboo = true;

    @Tooltip
    public boolean advancedToolTips = true;
}
