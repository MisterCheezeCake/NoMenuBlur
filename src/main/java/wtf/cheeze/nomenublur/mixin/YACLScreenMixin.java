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
package wtf.cheeze.nomenublur.mixin;

import dev.isxander.yacl3.gui.YACLScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.cheeze.nomenublur.NoMenuBlur;

// This is here because YACL does not save the config file if instant properties are used
// TODO: Remove this if YACL is ever fixed... or remove YACL
@Mixin(YACLScreen.class)
public abstract class YACLScreenMixin {
	@Inject(method = "close", at = @At("HEAD"))
	public void onCloseYACLScreen(CallbackInfo info) {
		if (MinecraftClient.getInstance().currentScreen.getTitle().toString().contains("NoMenuBlur")) {
			NoMenuBlur.config.save();
		};

	}
}


