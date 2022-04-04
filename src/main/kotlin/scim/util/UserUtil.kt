package scim.util

import scim.client.JedisClient
import scim.data.schemas.User
import java.util.*


object UserUtil {

    private val dbClient = JedisClient()

    private val log = Utils.getLogger(javaClass.name)

    fun getUsers(): List<User> {
        val users = ArrayList<User>()
        val keys = ArrayList(dbClient.getAllKeys())
        keys.forEach { key ->
            val user = getUser(key)
            if (user != null) {
                users.add(user)
            }
        }
        return users
    }

    fun getUser(uuid: String): User? {
        val user = dbClient.getUser(uuid)
        log.info("Get user response from database: $user")
        return user
    }

    fun createUser(user: User): User? {
        val uuid = Utils.generateUuid()
        user.uuid = uuid
        return if (dbClient.createUser(uuid, user) != null) {
            log.info("User $user created successfully")
            user
        } else null
    }

    fun updateUser(uuid: String, incomingUser: User): User? {
        val existingUser: User? = dbClient.getUser(uuid)
        if (existingUser != null) {
            val doUpdate: Boolean = checkUserFields(incomingUser, existingUser) // Compare incoming user attributes to existing and update user attributes if needed
            return if (doUpdate) { // Update if anything has changed
                log.info("Updating user $uuid")
                val updatedUser = dbClient.updateUser(uuid, existingUser)
                updatedUser
            } else existingUser
        }
        log.warn("User $uuid not found")
        return null
    }

    private fun checkUserFields(incomingUser: User, existingUser: User): Boolean {
        var doUpdate = false
        if (existingUser.firstName != incomingUser.firstName) {
            existingUser.firstName = incomingUser.firstName
            doUpdate = true
        }
        if (existingUser.lastName != incomingUser.lastName) {
            existingUser.lastName = incomingUser.lastName
            doUpdate = true
        }
        if (existingUser.emailAddress != incomingUser.emailAddress) {
            existingUser.emailAddress = incomingUser.emailAddress
            doUpdate = true
        }
        if (existingUser.title != incomingUser.title) {
            existingUser.title = incomingUser.title
            doUpdate = true
        }
        return doUpdate
    }
}