package com.cin.ufpe.br.aque.data

import com.cin.ufpe.br.aque.data.model.LoggedInUser
import java.io.IOException
import java.security.MessageDigest

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    private val HEX_CHARS = "0123456789ABCDEF".toCharArray()

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
            val hashedPassword = printHexBinary(bytes).toUpperCase()
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }

    private fun printHexBinary(data: ByteArray): String {
        val r = StringBuilder(data.size * 2)
        data.forEach { b ->
            val i = b.toInt()
            r.append(HEX_CHARS[i shr 4 and 0xF])
            r.append(HEX_CHARS[i and 0xF])
        }
        return r.toString()
    }
}

