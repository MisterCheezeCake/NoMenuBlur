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

import wtf.cheeze.nomenublur.NoMenuBlur;

public class SerializableConfig {

	public int color1;
	public int color2;
	public boolean enabled;
	public boolean disableBlur;
	public boolean defaultBackground;

	public SerializableConfig(ConfigImp config) {
		this.color1 = NoMenuBlur.colorToRGBAInt(config.color1);
		this.color2 = NoMenuBlur.colorToRGBAInt(config.color2);
		this.enabled = config.enabled;
		this.disableBlur = config.disableBlur;
		this.defaultBackground = config.defaultBackground;
	}
}
