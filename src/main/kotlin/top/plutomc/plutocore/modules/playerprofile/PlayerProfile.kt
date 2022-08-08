package top.plutomc.plutocore.modules.playerprofile

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import top.plutomc.plutocore.Module
import java.io.File
import java.util.*

class PlayerProfile : Module("playerProfile"), Listener {
    companion object {
        lateinit var folder: File
        lateinit var profiles: HashMap<UUID, FileConfiguration>

        fun getProfileFile(uuid: UUID): File {
            return File(folder, "$uuid.yml")
        }

        fun hasProfile(uuid: UUID) = getProfileFile(uuid).exists()


        fun createProfile(uuid: UUID) {
            if (hasProfile(uuid).not()) {
                val file = getProfileFile(uuid)
                file.createNewFile()
            }
        }

        fun getProfile(uuid: UUID): FileConfiguration {
            if (hasProfile(uuid).not()) {
                createProfile(uuid)
            }
            if (profiles.containsKey(uuid).not()) {
                profiles[uuid] = YamlConfiguration.loadConfiguration(getProfileFile(uuid))
            }
            return profiles[uuid]!!
        }

        fun saveProfile(uuid: UUID) {
            getProfile(uuid).save(getProfileFile(uuid))
        }

        fun unloadProfile(uuid: UUID) {
            saveProfile(uuid)
            profiles.remove(uuid)
        }

        fun unloadAllProfiles() {
            for (profile in profiles.keys) {
                unloadProfile(profile)
            }
        }
    }

    override fun load() {
        folder = File(dataFolder, "${File.separator}profiles${File.separator}")
        if (folder.exists().not()) folder.mkdirs()
        profiles = HashMap()

        registerEvents("playerJoin", this)
    }

    override fun unload() {
    }

    @EventHandler
    fun playerJoinEvent(event: PlayerJoinEvent) {
        val uuid = event.player.uniqueId
        val profile = getProfile(uuid)
        if (profile.contains("name").not()) profile.set("name", event.player.name)
        if (profile.contains("uuid").not()) profile.set("uuid", uuid.toString())
        saveProfile(uuid)
    }
}