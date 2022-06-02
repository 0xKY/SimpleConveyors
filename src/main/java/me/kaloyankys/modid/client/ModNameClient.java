package me.kaloyankys.modid.client;

import me.kaloyankys.modid.ModName;
import net.fabricmc.api.ClientModInitializer;

public class ModNameClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModName.LOGGER.info("Loaded " + ModName.MOD_ID + " client");
    }
}

