package me.kaloyankys.simple_conveyors.client;

import me.kaloyankys.simple_conveyors.Conveyors;
import net.fabricmc.api.ClientModInitializer;

public class ConveyorsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Conveyors.LOGGER.info("Loaded " + Conveyors.MOD_ID + " client");
    }
}

