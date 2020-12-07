package com.jummit.stackmodify;

import com.jummit.stackmodify.config.StackModifyConfig;
import com.jummit.stackmodify.util.NumberModifierUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(StackModify.MOD_ID)
@Mod.EventBusSubscriber
public class StackModify
{
	public static final String MOD_ID = "stackmodify";
	public NumberModifierUtils modifierUtils = new NumberModifierUtils();
	private static final Logger LOGGER = LogManager.getLogger();

    public StackModify() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StackModifyConfig.COMMON_SPEC);
	}

	@SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
		LOGGER.info("Loading stack size config");
		StackSizeModifier.modifyStackSizes(new NumberModifierUtils().modifiers);
    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
		LOGGER.info("Reloading stack size config");
		StackSizeModifier.modifyStackSizes(new NumberModifierUtils().modifiers);
    }
}
