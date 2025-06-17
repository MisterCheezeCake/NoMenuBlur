/*
 * Copyright (C) 2024 MisterCheezeCake
 *
 * This file is part of NoMenuBlur.
 *
 * NoMenuBlur is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * NoMenuBlur is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with NoMenuBlur. If not, see <https://www.gnu.org/licenses/>.
 */
package wtf.cheeze.nomenublur;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wtf.cheeze.nomenublur.config.NoMenuBlurConfig;

public class NoMenuBlur implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("nomenublur");
	public static NoMenuBlurConfig config = new NoMenuBlurConfig();

	public static int colorToRGBAInt(java.awt.Color color) {
		int alpha = color.getAlpha();
		int rgb = color.getRGB();
		return (alpha << 24) | (rgb & 0x00ffffff);
	}
	@Override
	public void onInitialize() {
		config.configHandler.load();
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("nomenublur")
			.executes(context -> {
					MinecraftClient mc = context.getSource().getClient();
					Screen screen = config.createConfigScreen(null);
					mc.send(() -> mc.setScreen(screen));
					return 1;
			 }
			)
		 )
		);
	}
}
