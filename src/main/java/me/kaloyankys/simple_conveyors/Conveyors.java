package me.kaloyankys.simple_conveyors;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Conveyors implements ModInitializer {
	public static final String MOD_ID = "simple_conveyors";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Block CONVEYER = new ConveyorBlock(FabricBlockSettings.copy(Blocks.SMOOTH_QUARTZ_SLAB));

	@Override
	public void onInitialize() {
		LOGGER.info("Loaded " + MOD_ID);

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "conveyor"), CONVEYER);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "conveyor"), new BlockItem(CONVEYER, new FabricItemSettings().group(ItemGroup.REDSTONE)));
	}
}
