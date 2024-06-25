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

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import wtf.cheeze.nomenublur.NoMenuBlur;

public final class ModMenuScreen implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return NoMenuBlur.config::createConfigScreen;
		}
}
