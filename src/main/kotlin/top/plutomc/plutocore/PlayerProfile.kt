package top.plutomc.plutocore

import org.bukkit.configuration.serialization.ConfigurationSerializable
import java.util.*

class PlayerProfile : ConfigurationSerializable {

    var name: String
        private set
    var uuid: UUID
        private set
    var metadata: MutableMap<String, Any>
        private set

    constructor(name: String) {
        this.name = name
        this.uuid = CorePlugin.instance.server.getPlayer(name).let { it!!.uniqueId }
        metadata = HashMap()

    }

    constructor(serialized: MutableMap<String, Any>) {
        this.name = serialized["name"] as String
        this.uuid = serialized["uuid"] as UUID
        this.metadata = serialized["metadata"] as MutableMap<String, Any>
    }

    override fun serialize(): MutableMap<String, Any> {
        val map = HashMap<String, Any>()
        map["name"] = name
        map["uuid"] = uuid
        map["metadata"] = metadata
        return map
    }

}