package scim.data.response

import kotlinx.serialization.Serializable
import scim.data.schemas.User

@Serializable
data class UsersResponse(
    val count: Int,
    val resources: List<User>
)
