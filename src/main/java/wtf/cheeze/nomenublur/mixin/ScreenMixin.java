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

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.cheeze.nomenublur.NoMenuBlur;

@Mixin(Screen.class)
public abstract class ScreenMixin {
	@Shadow
	@Nullable
	protected MinecraftClient client;

	@Shadow
	public int width;

	@Shadow
	public int height;

	@Shadow
	protected abstract void renderDarkening(DrawContext context);

	@Shadow
	protected abstract void applyBlur(float delta);

	@Inject(method = "renderBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;applyBlur(F)V", ordinal = 0), cancellable = true)
	protected void onRenderBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo info) {
		if (NoMenuBlur.config.configHandler.enabled == false) return;
		if (this.client.world == null) {
			this.renderDarkening(context);
			info.cancel();
			return;
		}
		if (NoMenuBlur.config.configHandler.disableBlur == false) {
			this.applyBlur(delta);
		}

		if (NoMenuBlur.config.configHandler.defaultBackground == true) {
			this.renderDarkening(context);
		} else {
			context.fillGradient(0, 0, this.width, this.height, NoMenuBlur.colorToRGBAInt(NoMenuBlur.config.configHandler.color1), NoMenuBlur.colorToRGBAInt(NoMenuBlur.config.configHandler.color2));
		}
		info.cancel();
	}
}
