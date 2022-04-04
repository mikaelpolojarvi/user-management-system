package scim.util

class AuthUtil {

    companion object {
        private var BEARER: String? = null

        fun initBearer(bearer: String) {
            BEARER = bearer
        }

        fun checkAuth(bearer: String?): Boolean {
            return BEARER == bearer
        }
    }
}