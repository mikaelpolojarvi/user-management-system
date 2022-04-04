package scim.client

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import redis.clients.jedis.Jedis
import scim.data.schemas.User

class JedisClient {

    private val client = Jedis()

    fun getAllKeys(): Set<String> {
        return this.client.keys("*")
    }

    fun getUser(uuid: String): User? {
        return try {
            if (this.client.exists(uuid)) {
                Json.decodeFromString(this.client.get(uuid))
            } else null
        } catch (e: Exception) {
            null
        }
    }

    fun createUser(uuid: String, user: User): User? {
        return if (this.client.exists(uuid)) {
            null
        } else {
            this.client.set(uuid, Json.encodeToJsonElement(user).toString())
            getUser(uuid)
        }
    }

    fun updateUser(uuid: String, user: User): User? {
        return try {
            if (this.client.exists(uuid)) {
                this.client.set(uuid, Json.encodeToJsonElement(user).toString())
                user
            } else null
        } catch(e: Exception) {
            null
        }
    }
}