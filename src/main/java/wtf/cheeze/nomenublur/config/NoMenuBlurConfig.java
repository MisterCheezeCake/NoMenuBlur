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
package wtf.cheeze.nomenublur.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import wtf.cheeze.nomenublur.NoMenuBlur;

import java.awt.Color;

public class NoMenuBlurConfig {

			public ConfigImp configHandler = new ConfigImp();
			public void save() {
				NoMenuBlur.LOGGER.info("NoMenuBlur: Saving Config");
				this.configHandler.save();
			}

			public Screen createConfigScreen(Screen parent) {

				Option<Color> color1 = Option.<Color>createBuilder()
					.name(Text.literal("Background Color 1"))
					.description(OptionDescription.of(Text.literal("The first color of the background gradient")))
					.stateManager(StateManager.createInstant(
						ConfigImp.DEFAULT_COLOR_1,
						() -> configHandler.color1,
						color -> {
							configHandler.color1 = color;
						})
					)
					.available(configHandler.enabled && !configHandler.defaultBackground)
					.controller(opt -> ColorControllerBuilder.create(opt).allowAlpha(true))
					.build();
				Option<Color> color2 = Option.<Color>createBuilder()
					.name(Text.literal("Background Color 2"))
					.description(OptionDescription.of(Text.literal("The second color of the background gradient")))
					.stateManager(StateManager.createInstant(
						ConfigImp.DEFAULT_COLOR_2,
						() -> configHandler.color2,
						color -> {
							configHandler.color2 = color;
						}
					))
					.available(configHandler.enabled && !configHandler.defaultBackground)
					.controller(opt -> ColorControllerBuilder.create(opt).allowAlpha(true))
					.build();
				ButtonOption sync = ButtonOption.createBuilder()
					.name(Text.literal("Sync Colors"))
					.text(Text.literal("Click Me!"))
					.description(OptionDescription.of(Text.literal("Set the second color to the first one")))
					.action((screen, option) -> {
						configHandler.color2 = configHandler.color1;
						color2.requestSet(configHandler.color1);
					})
					.available(configHandler.enabled && !configHandler.defaultBackground)
					.build();
				Option<Boolean> disableBlur = Option.<Boolean>createBuilder()
					.name(Text.literal("Disable Blur"))
					.description(OptionDescription.of(Text.literal("Whether or not the mod should disable the blur effect.")))
					.stateManager(StateManager.createInstant(
						true,
						() -> configHandler.disableBlur,
						b -> {
							configHandler.disableBlur = b;
						}
					))
					.available(configHandler.enabled)
					.controller(TickBoxControllerBuilder::create)
					.build();

				Option<Boolean> defaultBackground = Option.<Boolean>createBuilder()
					.name(Text.literal("Use Default Background"))
					.description(OptionDescription.of(Text.literal("Whether or not to render the default background or the customized one. This option does not care if blur is on or off.")))
					.stateManager(StateManager.createInstant(
						false,
						() -> configHandler.defaultBackground,
						b -> {
							configHandler.defaultBackground = b;
							if (b) {
								color1.setAvailable(false);
								color2.setAvailable(false);
								sync.setAvailable(false);
							} else {
								color1.setAvailable(true);
								color2.setAvailable(true);
								sync.setAvailable(true);
							}
						}
					))
					.available(configHandler.enabled)
					.controller(TickBoxControllerBuilder::create)
					.build();

				Option<Boolean> toggle = Option.<Boolean>createBuilder()
					.name(Text.literal("Mod Toggle"))
					.description(OptionDescription.of(Text.literal("Whether or not the mod should do anything. If this option is disabled, the mod won't apply any changes to the game's rendering.")))
					.stateManager(StateManager.createInstant(
						true,
						() -> configHandler.enabled,
						enabled -> {
							configHandler.enabled = enabled;
							if (!enabled) {
								disableBlur.setAvailable(false);
								defaultBackground.setAvailable(false);
								color1.setAvailable(false);
								color2.setAvailable(false);
								sync.setAvailable(false);
							} else {
								disableBlur.setAvailable(true);
								defaultBackground.setAvailable(true);
								if (!configHandler.defaultBackground)  color1.setAvailable(true);
								if (!configHandler.defaultBackground) color2.setAvailable(true);
								if (!configHandler.defaultBackground) sync.setAvailable(true);
							}
						}
					))
					.controller(opt -> BooleanControllerBuilder.create(opt)
						.formatValue(b -> b ? Text.literal("Enabled") : Text.literal("Disabled"))
						.coloured(true))
					.build();

				return YetAnotherConfigLib.createBuilder()
					.title(Text.literal("NoMenuBlur Config"))
					.category(
						ConfigCategory.createBuilder()
							.name(Text.literal("General"))
							.option(toggle)
							.option(disableBlur)
							.option(defaultBackground)
							.option(color1)
							.option(color2)
							.option(sync)
							.build()
					)
					.save(this::save)
					.build()
					.generateScreen(parent);
			}
}
