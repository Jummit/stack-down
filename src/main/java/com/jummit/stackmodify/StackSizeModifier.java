package com.jummit.stackmodify;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jummit.stackmodify.config.StackModifyConfig;
import com.jummit.stackmodify.util.ItemMatchUtils;
import com.jummit.stackmodify.util.StringOperaterUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class StackSizeModifier {
	
	private static final Logger LOGGER = LogManager.getLogger();

	private static final Map<Item, Integer> originalStackSizes = new HashMap<Item, Integer>();

	public static void modifyStackSizes() {
		Field maxStackSize = ObfuscationReflectionHelper.findField(Item.class, "field_200920_a");
		maxStackSize.setAccessible(true);
		Registry.ITEM.forEach((item) -> {
			originalStackSizes.put(item, item.getMaxStackSize());
		});
		Registry.ITEM.forEach((item) -> {
			int newSize = getModifiedStackSize(item);
			if (newSize != originalStackSizes.get(item)) {
				LOGGER.debug("Setting stack size of " + item + " to " + newSize);
			}
			try {
				maxStackSize.set(item, newSize);
			} catch (IllegalArgumentException e) {
				LOGGER.error(e);
			} catch (IllegalAccessException e) {
				LOGGER.error(e);
			}
		});
	}

	private static int getModifiedStackSize(Item item) {
		for (String config : StackModifyConfig.COMMON.stackSizes.get()) {
			Matcher matcher = Pattern.compile("^(.+)([*=+-/])(\\d+)$").matcher(config);
			if (matcher.matches() && ItemMatchUtils.match(matcher.group(1), item, originalStackSizes)) {
				return StringOperaterUtils.calculate(originalStackSizes.get(item), Integer.parseInt(matcher.group(3)), matcher.group(2));
			}
		}

		String config = item instanceof BlockItem ? StackModifyConfig.COMMON.blockStackSize.get() : StackModifyConfig.COMMON.itemStackSize.get();
		Matcher matcher = Pattern.compile("^([*+-/]?)(\\d+)$").matcher(config);
		if (matcher.matches()) {
			if (matcher.group(1).isEmpty()) {
				return Integer.parseInt(matcher.group(2));
			} else {
				return StringOperaterUtils.calculate(originalStackSizes.get(item), Integer.parseInt(matcher.group(2)), matcher.group(1));
			}
		}

		return originalStackSizes.get(item);
	}
}
