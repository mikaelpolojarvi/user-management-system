package scim.data.schemas

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var firstName: String,
    var lastName: String,
    var emailAddress: String,
    var title: String,
    var uuid: String? = null
)
