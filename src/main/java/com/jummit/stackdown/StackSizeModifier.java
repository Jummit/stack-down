package com.jummit.stackdown;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jummit.stackdown.config.StackDownConfig;
import com.jummit.stackdown.util.ItemMatchUtils;
import com.jummit.stackdown.util.StringOperaterUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class StackSizeModifier {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final Map<Item, Integer> originalStackSizes = new HashMap<Item, Integer>();
	
	public static void init() {
		Registry.ITEM.forEach((item) -> {
			originalStackSizes.put(item, item.getMaxStackSize());
		});
	}
	
	public static void modifyStackSizes() {
		Field maxStackSize = ObfuscationReflectionHelper.findField(Item.class, "field_77777_bU");
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
		for (String config : StackDownConfig.SERVER.stackSizes.get()) {
			Matcher matcher = Pattern.compile("^(.+)([*=+-/])(\\d+)$").matcher(config);
			if (matcher.matches() && ItemMatchUtils.match(matcher.group(1), item, originalStackSizes)) {
				return StringOperaterUtils.calculate(originalStackSizes.get(item), Integer.parseInt(matcher.group(3)), matcher.group(2));
			}
		}
		
		String config = item instanceof BlockItem ? StackDownConfig.SERVER.blockStackSize.get() : StackDownConfig.SERVER.itemStackSize.get();
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
