package scim.server

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.LoggerFactory
import scim.util.AuthUtil
import scim.util.ResponseUtil
import scim.util.UserUtil
import scim.util.Utils
import java.lang.Exception

object Server {

    fun startServer() {
        val log = Utils.getLogger(javaClass.name)
        log.info("Server starting...")

        runCatching {
            embeddedServer(Netty, port = 8080) {
                install(ContentNegotiation) {
                    json()
                }

                routing {

                    get("/Users") {
                        log.info("GET /Users")
                        if (!AuthUtil.checkAuth(call.request.headers["Authorization"])) {
                            log.warn("Unauthorized")
                            call.respond(HttpStatusCode.Unauthorized)
                        } else {
                            call.respond(ResponseUtil().formUsersResponse(UserUtil.getUsers()))
                        }
                    }

                    get("/Users/{uuid}"){
                        log.info("GET /Users/{uuid}")
                        if (!AuthUtil.checkAuth(call.request.headers["Authorization"])) {
                            log.warn("Unauthorized")
                            call.respond(HttpStatusCode.Unauthorized)
                        } else {
                            val user = call.parameters["uuid"]?.let { uuid -> UserUtil.getUser(uuid) }
                            if (user != null) {
                                call.respond(ResponseUtil().formUserResponse(user))
                            } else call.respond(HttpStatusCode.NotFound)
                        }
                    }

                    post("/Users"){
                        log.info("POST /Users")
                        if (!AuthUtil.checkAuth(call.request.headers["Authorization"])) {
                            log.warn("Unauthorized")
                            call.respond(HttpStatusCode.Unauthorized)
                        } else {
                            try {
                                val user = UserUtil.createUser(call.receive())
                                if (user != null) {
                                    call.response.status(HttpStatusCode.Created)
                                    call.respond(ResponseUtil().formUserResponse(user))
                                } else call.respond(HttpStatusCode.Conflict)
                            } catch (e: Exception) {
                                log.error("Bad request ", e.printStackTrace())
                                call.respond(HttpStatusCode.BadRequest)
                            }
                        }
                    }

                    put("/Users/{uuid}"){
                        log.info("PUT /Users/{uuid}")
                        if (!AuthUtil.checkAuth(call.request.headers["Authorization"])) {
                            log.warn("Unauthorized")
                            call.respond(HttpStatusCode.Unauthorized)
                        } else {
                            try {
                                val user = call.parameters["uuid"]?.let { uuid -> UserUtil.updateUser(uuid, call.receive()) }
                                if (user != null) {
                                    call.response.status(HttpStatusCode.Created)
                                    call.respond(ResponseUtil().formUserResponse(user))
                                } else call.respond(HttpStatusCode.NotFound)
                            } catch (e: Exception) {
                                log.error("Bad request ", e.printStackTrace())
                                call.respond(HttpStatusCode.BadRequest)
                            }
                        }
                    }
                }
            }.start(wait = true)
        }
    }
}
