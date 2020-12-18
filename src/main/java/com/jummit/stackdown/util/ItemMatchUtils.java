package com.jummit.stackdown.util;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemMatchUtils {
	
	public static Boolean match(String pattern, Item item, Map<Item, Integer> originalStackSizes) {
		return NumberUtils.isCreatable(pattern) && originalStackSizes.get(item).equals(Integer.parseInt(pattern)) ||
				item.getRegistryName().getNamespace().equals(pattern) ||
				item.getRegistryName().toString().equals(pattern) ||
				item.getTags().contains(ResourceLocation.tryCreate(pattern)) ||
				pattern.startsWith("(") && pattern.endsWith(")") && Pattern.compile(pattern.substring(1, pattern.length() - 1)).matcher(item.getRegistryName().toString()).matches();
	}

}
