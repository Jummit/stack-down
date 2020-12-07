package com.jummit.stackmodify;

import com.jummit.stackmodify.config.StackModifyConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(StackModify.MOD_ID)
@Mod.EventBusSubscriber(modid = StackModify.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StackModify
{
	public static final String MOD_ID = "stackmodify";
	private static final Logger LOGGER = LogManager.getLogger();

    public StackModify() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StackModifyConfig.COMMON_SPEC);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
    static void onLoad(final ModConfig.Loading configEvent) {
		LOGGER.info("Loading stack size config");
		StackSizeModifier.modifyStackSizes();
    }

    @SubscribeEvent
    public static void onReload(ModConfig.Reloading configEvent) {
		LOGGER.info("Reloading stack size config");
		StackSizeModifier.modifyStackSizes();
    }
}
