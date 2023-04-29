package cantwe.alldisagree;

import cantwe.alldisagree.config.BackpackInfo;
import cantwe.alldisagree.config.BaggedConfig;
import cantwe.alldisagree.item.BackpackItem;
import cantwe.alldisagree.item.DyeableBackpackItem;
import cantwe.alldisagree.item.EnderBackpackItem;
import cantwe.alldisagree.network.ServerNetworking;
import cantwe.alldisagree.api.TrinketCompat;
import cantwe.alldisagree.mixins.trinkets.TrinketsMixinPlugin;
import cantwe.alldisagree.ui.BackpackScreenHandler;

import draylar.omegaconfig.OmegaConfig;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Bagged implements ModInitializer {

    public static final boolean TRINKETS_LOADED = FabricLoader.getInstance().isModLoaded("trinkets");
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Identifier CONTAINER_ID = id("backpack");
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(CONTAINER_ID, () -> new ItemStack(Registry.ITEM.get(id("frayed_backpack"))));
    public static final BaggedConfig CONFIG = OmegaConfig.register(BaggedConfig.class);
    //public static final ScreenHandlerType<BackpackScreenHandler> CONTAINER_TYPE = ScreenHandlerRegistry.registerExtended(CONTAINER_ID, BackpackScreenHandler::new);
    public static final ScreenHandlerType<BackpackScreenHandler> CONTAINER_TYPE = Registry.register(Registry.SCREEN_HANDLER, CONTAINER_ID, new ExtendedScreenHandlerType<>(BackpackScreenHandler::new));
    public static final List<BackpackItem> BACKPACKS = new ArrayList<>();
    public static final Item ENDER_POUCH = Registry.register(Registry.ITEM, id("ender_pouch"), new EnderBackpackItem());

    @Override
    public void onInitialize() {
        registerBackpacks();
        ServerNetworking.init();
        setupTrinkets();
    }

    private void registerBackpacks() {
        BaggedConfig defaultConfig = new BaggedConfig();

        for (BackpackInfo backpack : Bagged.CONFIG.backpacks) {
            FabricItemSettings settings = new FabricItemSettings().group(Bagged.GROUP).maxCount(1);

            // If this config option is true, allow players to place backpacks inside the chest slot in their armor inventory.
            if(Bagged.CONFIG.allowBackpacksInChestplate) {
                settings.equipmentSlot(stack -> EquipmentSlot.CHEST);
            }

            // setup fireproof item settings
            if(backpack.isFireImmune()) {
                settings.fireproof();
            }

            // old config instances do not have the sound stuff
            if(backpack.getOpenSound() == null) {
                Optional<BackpackInfo> any = defaultConfig.backpacks.stream().filter(info -> info.getName().equals(backpack.getName())).findAny();
                any.ifPresent(backpackInfo -> backpack.setOpenSound(backpackInfo.getOpenSound()));

                // if it is STILL null, log an error and set a default
                if(backpack.getOpenSound() == null) {
                    LOGGER.info(String.format("Could not find a sound event for %s in bagged.json config.", backpack.getName()));
                    LOGGER.info("Consider regenerating your config, or assigning the openSound value. Rolling with defaults for now.");
                    backpack.setOpenSound("minecraft:item.armor.equip_leather");
                }
            }


            BackpackItem item;
            if(TRINKETS_LOADED && CONFIG.enableTrinketCompatibility) {
                item = TrinketCompat.createTrinketBackpack(backpack, settings);
            } else {
                item = backpack.isDyeable() ? new DyeableBackpackItem(backpack, settings) : new BackpackItem(backpack, settings);
            }

            BackpackItem registered = Registry.register(Registry.ITEM, new Identifier("bagged", backpack.getName().toLowerCase() + "_backpack"), item);
            BACKPACKS.add(registered);

            // Register to the TrinketsApi if both conditions are true.
            // This allows TrinketBackpackItem to handle API events (namely canUnequip).
            if(TRINKETS_LOADED && CONFIG.enableTrinketCompatibility) {
                TrinketCompat.registerTrinketBackpack(item);
            }
        }
    }

    private void setupTrinkets() {
        if(TrinketsMixinPlugin.isTrinketsLoaded && Bagged.CONFIG.enableTrinketCompatibility) {
            TrinketCompat.registerTrinketPredicate();
        }
    }

    public static boolean isBackpackEmpty(ItemStack stack) {
        NbtList tag = stack.getOrCreateNbt().getList("Inventory", NbtElement.COMPOUND_TYPE);

        // If any inventory element in the Backpack stack is non-empty, return false;
        for (NbtElement element : tag) {
            NbtCompound stackTag = (NbtCompound) element;
            ItemStack backpackStack = ItemStack.fromNbt(stackTag.getCompound("Stack"));
            if(!backpackStack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public static List<ItemStack> getBackpackContents(ItemStack stack) {
        List<ItemStack> stacks = new ArrayList<>();
        NbtList tag = stack.getOrCreateNbt().getList("Inventory", NbtElement.COMPOUND_TYPE);

        // If any inventory element in the Backpack stack is non-empty, return false;
        for (NbtElement element : tag) {
            NbtCompound stackTag = (NbtCompound) element;
            ItemStack backpackStack = ItemStack.fromNbt(stackTag.getCompound("Stack"));
            stacks.add(backpackStack);
        }

        return stacks;
    }

    public static void wipeBackpack(ItemStack stack) {
        stack.getOrCreateNbt().remove("Inventory");
    }

    public static Identifier id(String name) {
        return new Identifier("bagged", name);
    }
}
