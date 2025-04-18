package com.saji.dashboard_backend.modules.stock_management.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.stock_management.entities.Item;
import com.saji.dashboard_backend.modules.stock_management.entities.ItemVariant;
import com.saji.dashboard_backend.modules.stock_management.entities.ItemVariantInformation;
import com.saji.dashboard_backend.modules.stock_management.repositories.ItemRepo;
import com.saji.dashboard_backend.shared.services.BaseServiceImpl;

@Service
public class ItemService extends BaseServiceImpl<Item> {
    private ItemRepo repo;

    public ItemService(ItemRepo repo) {
        super(repo);
        this.repo = repo;
    }

    @Override
    public void validate(Item entity) {
        ItemVariantInformation variantInformation = entity.getVariantInformation();
        if (variantInformation.isHasSubItems()) {
            if (variantInformation.getValues().isEmpty()) {
                throw new IllegalArgumentException("You should at least include one variant.");
            } else {
                Map<Long, Long> variantCount = variantInformation.getValues().stream()
                        .collect(Collectors.groupingBy(ItemVariant::getVariantId, Collectors.counting()));

                List<Long> duplicates = variantCount.entrySet().stream()
                        .filter(entry -> entry.getValue() > 1)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());

                if (!duplicates.isEmpty()) {
                    throw new IllegalArgumentException(
                            String.format("Variants %s are already included in the variants table.", duplicates));
                }
            }
        }
    }

    public void createSubItems(Long itemId, Map<String, String[]> variantValues) {
        // Fetch the template item
        Item templateItem = this.findEntityById(itemId);
        String itemTitle = templateItem.getTitle();

        // Generate all combinations of variant values
        List<String> subItemsName = new ArrayList<>();
        List<String> keys = new ArrayList<>(variantValues.keySet());
        combine(variantValues, keys, 0, "", subItemsName);

        // Precompute reverse lookup for variant values
        Map<String, String> valueToKeyMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : variantValues.entrySet()) {
            for (String value : entry.getValue()) {
                valueToKeyMap.put(value, entry.getKey());
            }
        }

        // Create sub-items
        List<Item> subItems = new ArrayList<>();
        for (String combination : subItemsName) {
            Item item = new Item();
            item.setTitle(itemTitle + "-" + combination);
            item.setEnabled(true);

            // Prepare variant information
            ItemVariantInformation variantInfo = new ItemVariantInformation();
            variantInfo.setTemplateId(itemId);
            variantInfo.setTemplate(templateItem.getTitle());

            Set<ItemVariant> values = Arrays.stream(combination.split("-"))
                    .map(variantValue -> createItemVariant(templateItem, valueToKeyMap, variantValue))
                    .collect(Collectors.toSet());

            variantInfo.setValues(values);
            item.setVariantInformation(variantInfo);
            item.setUoms(new HashSet<>(templateItem.getUoms()));
            subItems.add(item);
        }

        // Save all sub-items
        repo.saveAll(subItems);
    }

    // Helper method to create an ItemVariant
    private ItemVariant createItemVariant(Item templateItem, Map<String, String> valueToKeyMap, String variantValue) {
        String key = valueToKeyMap.get(variantValue);
        if (key == null) {
            throw new IllegalArgumentException("Variant value '" + variantValue + "' not found in variantValues map");
        }

        Long variantId = Long.parseLong(key);
        return templateItem.getVariantInformation().getValues().stream()
                .filter(v -> v.getVariantId().equals(variantId))
                .map(templateVariant -> {
                    ItemVariant variant = new ItemVariant();
                    variant.setVariantId(variantId);
                    variant.setVariant(templateVariant.getVariant());
                    variant.setValue(variantValue);
                    return variant;
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Variant with id: " + variantId + " not found"));
    }

    public List<Map<String, Object>> getItemUoms(Long itemId) {
        return repo.getItemUoms(itemId);
    }

    public Double getItemUomFactor(Long itemId, Long uomId) {
        return repo.getItemUomFactor(itemId, uomId);
    }

    private static void combine(Map<String, String[]> variantValues, List<String> keys,
            int index, String current, List<String> result) {
        if (index == keys.size()) {
            result.add(current);
            return;
        }

        String key = keys.get(index);
        String[] values = variantValues.get(key);

        for (String value : values) {
            combine(variantValues, keys, index + 1, current.length() == 0 ? value : current + "-" + value, result);
        }
    }
}
