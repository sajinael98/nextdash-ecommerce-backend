package com.saji.dashboard_backend.modules.stock_management.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.saji.dashboard_backend.modules.stock_management.entities.Item;
import com.saji.dashboard_backend.modules.stock_management.entities.ItemVariant;
import com.saji.dashboard_backend.modules.stock_management.entities.ItemVariantInformation;
import com.saji.dashboard_backend.modules.stock_management.repositories.ItemRepo;
import com.saji.dashboard_backend.shared.services.BaseService;
import com.saji.dashboard_backend.system.services.ResourceDbService;

@Service
public class ItemService extends BaseService<Item> {
    private ItemRepo repo;

    @Autowired
    private ResourceDbService resourceDbService;

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
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", itemId);
        var results = resourceDbService.getResourceValues("res_items", List.of("title"), List.of("id=:id"), params);
        String itemTitle = (String) results.get(0).get("title");
        List<String> subItemsName = new ArrayList<>();
        List<String> keys = new ArrayList<>(variantValues.keySet());

        combine(variantValues, keys, 0, "", subItemsName);

        List<Item> subItems = new ArrayList<>();
        for (String combination : subItemsName) {
            Item item = new Item();
            item.setTitle(itemTitle + "-" + combination);
            item.setEnabled(true);

            ItemVariantInformation info = new ItemVariantInformation();
            info.setTemplate(itemId);
            var spliitedStr = combination.split("-");
            Set<ItemVariant> values = new HashSet<>();
            for (String variantValue : spliitedStr) {
                String variantId = variantValues.entrySet().stream()
                        .filter(entry -> java.util.Arrays.asList(entry.getValue()).contains(
                                variantValue))
                        .map(Map.Entry::getKey)
                        .findFirst().orElse(null);
                ItemVariant variant = new ItemVariant();
                variant.setVariantId(Long.parseLong(variantId));
                variant.setValue(variantValue);
                values.add(variant);
            }
            info.setValues(values);
            item.setVariantInformation(info);
            subItems.add(item);
        }
        repo.saveAll(subItems);
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
