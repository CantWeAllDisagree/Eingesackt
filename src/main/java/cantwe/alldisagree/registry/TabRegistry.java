package cantwe.alldisagree.registry;

import java.util.ArrayList;
import java.util.List;

import cantwe.alldisagree.api.*;
import cantwe.alldisagree.ui.BackpackHandledScreen;
import cantwe.alldisagree.util.BackpackTab;
import cantwe.alldisagree.util.SortList;
import cantwe.alldisagree.BaggedClient;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;


@Environment(EnvType.CLIENT)
public class TabRegistry {

    public static void registerInventoryTab(InventoryTab tab) {
        BaggedClient.inventoryTabs.add(tab);
        // Sort preferred pos
        List<Integer> priorityList = new ArrayList<Integer>();
        for (int i = 0; i < BaggedClient.inventoryTabs.size(); i++) {
            int preferredPos = BaggedClient.inventoryTabs.get(i).getPreferredPos();
            if (preferredPos == -1) {
                preferredPos = 99;
            }
            priorityList.add(preferredPos);
        }
        SortList.concurrentSort(priorityList, BaggedClient.inventoryTabs);
    }

    public static void registerOtherTab(InventoryTab tab, Class<?> parentClass) {
        if (BaggedClient.otherTabs.get(parentClass) != null) {
            BaggedClient.otherTabs.get(parentClass).add(tab);
            // Sort preferred pos
            List<Integer> priorityList = new ArrayList<Integer>();
            for (int i = 0; i < BaggedClient.otherTabs.get(parentClass).size(); i++) {
                int preferredPos = BaggedClient.otherTabs.get(parentClass).get(i).getPreferredPos();
                if (preferredPos == -1) {
                    preferredPos = 99;
                }
                priorityList.add(preferredPos);
            }
            SortList.concurrentSort(priorityList, BaggedClient.otherTabs.get(parentClass));
        } else {
            List<InventoryTab> list = new ArrayList<InventoryTab>();
            list.add(tab);
            BaggedClient.otherTabs.put(parentClass, list);
        }
    }
    public static void init() {
        TabRegistry.registerInventoryTab(new BackpackTab(Text.translatable("screen.bagged.backpack_screen"), null, -1, BackpackHandledScreen.class));
    }
}
