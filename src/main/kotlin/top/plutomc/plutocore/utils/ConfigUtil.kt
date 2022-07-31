package top.plutomc.plutocore.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object ConfigUtil {
    fun getConfigByFile(file: File) = YamlConfiguration.loadConfiguration(file)

    fun saveConfigToFile(fileConfiguration: FileConfiguration, file: File) {
        fileConfiguration.save(file)
    }
}