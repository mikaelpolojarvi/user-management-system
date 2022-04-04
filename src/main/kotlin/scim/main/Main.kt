package scim.main

import scim.server.Server
import scim.util.AuthUtil

fun main() {
    val bearer = "Bearer <your bearer value>"
    AuthUtil.initBearer(bearer)
    Server.startServer()
}


