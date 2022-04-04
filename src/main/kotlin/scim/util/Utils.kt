package scim.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

object Utils {

    fun getLogger(name: String): Logger {
        return LoggerFactory.getLogger(name)
    }

    fun generateUuid(): String {
        return UUID.randomUUID().toString()
    }
}