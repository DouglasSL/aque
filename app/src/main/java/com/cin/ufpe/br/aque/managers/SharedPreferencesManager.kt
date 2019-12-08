package com.cin.ufpe.br.aque.managers

import android.content.Context
import android.util.Log

class SharedPreferencesManager {
    companion object {
        private val TAG = SharedPreferencesManager::class.simpleName
        private val PREFS_FILENAME = "com.cin.ufpe.br.aque"
        private val USER_TYPE = "user_type"

        fun setUserType(ctx: Context, isStudent: Boolean) {
            Log.i(TAG, "Saving user type in the shared preferences")
            var sharedPreferences = ctx.getSharedPreferences(PREFS_FILENAME, 0)
            var editor = sharedPreferences.edit()
            editor.putBoolean(USER_TYPE, isStudent)
            editor.apply()
        }

        fun isStudent(ctx: Context) : Boolean {
            Log.i(TAG, "Retreiving if the user is student")
            var sharedPreferences = ctx.getSharedPreferences(PREFS_FILENAME, 0)
            return sharedPreferences.getBoolean(USER_TYPE, false)
        }
    }
}