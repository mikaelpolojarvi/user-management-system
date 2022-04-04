package scim.util


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

object Utils {

    private const val BEARER = "Bearer asdfghjk"

    fun getLogger(name: String): Logger {
        return LoggerFactory.getLogger(name)
    }

    fun generateUuid(): String {
        return UUID.randomUUID().toString()
    }

    fun checkAuth(bearer: String?): Boolean {
        return BEARER == bearer
    }

}