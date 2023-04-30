package cantwe.alldisagree.config;

import draylar.omegaconfig.api.Config;
import draylar.omegaconfig.api.Syncing;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;
import java.util.List;

public class BaggedConfig implements Config {
    public List<BackpackInfo> backpacks = Arrays.asList(
            BackpackInfo.of("baby", 3, 1, false, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER),
            BackpackInfo.of("eingesackt", 7, 3, false, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER),
            BackpackInfo.of("frayed", 9, 1, false, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, true),
            BackpackInfo.of("plated", 9, 2, false, SoundEvents.ITEM_ARMOR_EQUIP_IRON),
            BackpackInfo.of("gilded", 9, 3, false, SoundEvents.ITEM_ARMOR_EQUIP_GOLD),
            BackpackInfo.of("bejeweled", 9, 5, false, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND),
            BackpackInfo.of("blazing", 9, 6, true, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER),
            BackpackInfo.of("withered", 11, 6, false, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER),
            BackpackInfo.of("endless", 15, 6, false, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER)
    );

    public boolean unstackablesOnly = false;


    public boolean disableShulkers = true;


    public boolean playSound = true;

    @Syncing

    public boolean requireArmorTrinketToOpen = false;

    public boolean allowBackpacksInChestplate = true;

    @Syncing

    public boolean enableTrinketCompatibility = true;

    @Syncing
    public boolean requireEmptyForUnequip = false;

    public boolean spillArmorBackpacksOnDeath = false;

    public boolean spillMainBackpacksOnDeath = false;

    public boolean trinketRendering = true;

    public String guiTitleColor = "0x404040";

    @Override
    public String getName() {
        return "bagged";
    }
}
