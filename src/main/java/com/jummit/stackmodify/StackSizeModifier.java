package com.jummit.stackmodify;

import java.lang.reflect.Field;

import com.jummit.stackmodify.config.StackModifyConfig;
import com.jummit.stackmodify.util.ItemMatchUtils;
import com.jummit.stackmodify.util.NumberModifierUtils.NumberModifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class StackSizeModifier {
	
	private static final Logger LOGGER = LogManager.getLogger();

	public static void modifyStackSizes(NumberModifier[] modifiers) {
		Field maxStackSize = ObfuscationReflectionHelper.findField(Item.class, "field_200920_a");
		maxStackSize.setAccessible(true);
		Registry.ITEM.iterator().forEachRemaining((item) -> {
			StackModifyConfig.COMMON.stackSizes.get().forEach((stackSize) -> {
				String[] split = null;
				NumberModifier numberModifier = null;
				for (int i = 0; i < modifiers.length; i++) {
					NumberModifier numberModifierObject = modifiers[i];
					String modifier = numberModifierObject.getModifier();
					if (stackSize.contains(modifier)) {
						split = stackSize.split(modifier);
						numberModifier = numberModifierObject;
					}
				}
				if (split == null || numberModifier == null) {
					return;
				}
				if (split.length < 2) {
					return;
				}
				String itemPattern = split[0];
				String stackSizeModifier = split[1];
				String modifier;
				if (ItemMatchUtils.match(itemPattern, item)) {
					modifier = stackSizeModifier;
				} else if (item instanceof BlockItem) {
					modifier = StackModifyConfig.COMMON.blockStackSize.get();
				} else {
					modifier = StackModifyConfig.COMMON.itemStackSize.get();
				}
				try {
					LOGGER.debug("Modifying stack size of " + item);
					maxStackSize.set(item, numberModifier.modify(item.getMaxStackSize(), Integer.parseInt(modifier)));
				} catch (NumberFormatException e) {
					LOGGER.error("Invalid number format in stack size config");
				} catch (IllegalArgumentException e) {
					LOGGER.error(e);
				} catch (IllegalAccessException e) {
					LOGGER.error(e);
				}
			});
		});
	}
}
