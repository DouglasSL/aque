package com.cin.ufpe.br.aque.data

import android.util.Log
import com.cin.ufpe.br.aque.data.model.StudentLoggedInUser
import com.cin.ufpe.br.aque.models.Student
import java.io.IOException
import java.security.MessageDigest

/**
 * Class that handles authentication w/ login credentials and retrieves userStudent information.
 */
class StudentLoginDataSource {
    private val HEX_CHARS = "0123456789ABCDEF".toCharArray()

    fun login(password: String, student: Student): Result<StudentLoggedInUser> {
        try {

            val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
            val hashedPassword = printHexBinary(bytes).toUpperCase()

            if(student.name == null) {
                Log.i("login", "Este usuário não está cadastrado")
                return Result.Error(IOException("Este usuário não está cadastrado"))
            }
            if(hashedPassword != student.password) {
                Log.i("login", "Senha ou Usuário inválido")
                return Result.Error(IOException("Senha ou Usuário inválido"))
            }

            Log.i("login", "Usuário logado")
            val user = StudentLoggedInUser(java.util.UUID.randomUUID().toString(), student.name!!, student.email!!)
            return Result.Success(user)
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

