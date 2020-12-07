package com.jummit.stackmodify.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemMatchUtils {
	
	public static Boolean match(String pattern, Item item) {
		return NumberUtils.isCreatable(pattern) && item.getMaxStackSize() == Integer.parseInt(pattern) ||
				item.getRegistryName().getNamespace() == pattern ||
				item.getRegistryName().toString() == pattern ||
				ResourceLocation.isResouceNameValid(pattern) && item.getTags().contains(new ResourceLocation(pattern)) ||
				pattern.startsWith("(") && pattern.endsWith(")") && Pattern.compile(pattern.substring(1, pattern.length() - 1)).matcher(item.getRegistryName().toString()).matches();
	}
}
