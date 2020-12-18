package com.jummit.stackdown;

import com.jummit.stackdown.config.StackDownConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.network.NetworkRegistry;

@Mod(StackDown.MOD_ID)
@Mod.EventBusSubscriber(modid = StackDown.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StackDown
{
	public static final String MOD_ID = "stackdown";
	private static final Logger LOGGER = LogManager.getLogger();

    public StackDown() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, StackDownConfig.SERVER_SPEC);
		MinecraftForge.EVENT_BUS.register(this);
		StackSizeModifier.init();
	}

	@SubscribeEvent
    static void onLoad(final ModConfig.Loading configEvent) {
		LOGGER.info("Reloading stack size config");
		StackSizeModifier.modifyStackSizes();
		LOGGER.info("Reloading complete");
    }

    @SubscribeEvent
    public static void onReload(ModConfig.Reloading configEvent) {
		LOGGER.info("Reloading stack size config");
		StackSizeModifier.modifyStackSizes();
		LOGGER.info("Reloading complete");
	}
}
