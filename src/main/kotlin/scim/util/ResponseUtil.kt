package scim.util

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import scim.data.response.UserResponse
import scim.data.schemas.User
import scim.data.response.UsersResponse

class ResponseUtil {

    fun formUsersResponse(users: List<User>): JsonElement {
        return Json.encodeToJsonElement(UsersResponse(users.size, users))
    }

    fun formUserResponse(user: User): JsonElement {
        return Json.encodeToJsonElement(UserResponse(user))
    }
}