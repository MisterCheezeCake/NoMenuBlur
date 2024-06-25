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

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigImp {
	public static final Color DEFAULT_COLOR_1 = new Color(16, 16, 16, 192);
	public static final Color DEFAULT_COLOR_2 = new Color(16, 16, 16, 208);
  private static final Gson gson = new Gson();
	private static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("nomenublur-config.json");
	public boolean enabled = true;
	public boolean disableBlur = true;
	public boolean defaultBackground = false;
	public Color color1 = DEFAULT_COLOR_1;
	public Color color2 = DEFAULT_COLOR_2;

	public void save() {

		File configFile = new File(String.valueOf(configPath));
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			SerializableConfig toWrite = new SerializableConfig(this);
			String json = gson.toJson(toWrite);
			FileWriter writer = new FileWriter(configFile);
			writer.write(json);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void load() {
		File configFile = new File(String.valueOf(configPath));
		if (!configFile.exists()) {
			save();
			return;
		}
		try {
			String configContent = Files.readString(configPath);
			SerializableConfig config;
			try {
				config = gson.fromJson(configContent, SerializableConfig.class);
			} catch (Exception e) {
				e.printStackTrace();
				save();
				config = new SerializableConfig(this);
			}

			this.enabled = config.enabled;
			this.disableBlur = config.disableBlur;
			this.color1 = new Color(config.color1, true);
			this.color2 = new Color(config.color2, true);
			this.defaultBackground = config.defaultBackground;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
