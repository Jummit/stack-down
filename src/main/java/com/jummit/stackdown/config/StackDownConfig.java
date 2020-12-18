package com.jummit.stackdown.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class StackDownConfig {
	
	public static final ServerConfig SERVER;
	public static final ForgeConfigSpec SERVER_SPEC;
	static {
		final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
		SERVER_SPEC = specPair.getRight();
		SERVER = specPair.getLeft();
	}
	
	public static class ServerConfig {
		
		public final ConfigValue<String> itemStackSize;
		public final ConfigValue<String> blockStackSize;
		public final ConfigValue<List<? extends String>> stackSizes;
		
		ServerConfig(ForgeConfigSpec.Builder builder) {
			itemStackSize = builder
					.comment("The max stack size modifier of all non-block items.\nModifiers can be operators like +, -, * and /.\nExamples:\n	+3\n	*5\n	/6\n	3\n	15\nIf this is empty, the stack sizes will only be modified when specified in the stackSizes list.")
					.define("itemStackSize", "");
			blockStackSize = builder
					.comment("The max stack size modifier of all block items.")
					.define("blockStackSize", "");
			stackSizes = builder
					.comment("A list of item patterns and their max stack size modifiers.\nPatterns can be items, mods, regular expressions surrounded by brackets, max stack sizes or tags.\nModifiers can be operators like +, -, * and / or an equal sign.")
					.comment("Examples:\n[\n	\"minecraft:stone=64\",\n	\"16=64\",\n	\"forge:stone/32\",\n	\"(diamond_.*)+30\",\n	\"forestry-30\"\n]")
					.defineList("stackSizes", new ArrayList<String>(), s -> s instanceof String);
		}
	}
}
