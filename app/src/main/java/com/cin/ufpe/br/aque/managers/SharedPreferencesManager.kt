package com.cin.ufpe.br.aque.managers

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.cin.ufpe.br.aque.models.Class

class SharedPreferencesManager {

    private val TAG = SharedPreferencesManager::class.simpleName
    private val PREFS_FILENAME = "com.cin.ufpe.br.aque"
    private val USER_TYPE = "user_type"
    private val USER_ID = "user_id"
    private val CURRENT_CLASS_ID = "current_class_id"
    private val CURRENT_CLASS_NAME = "current_class_name"
    private val CURRENT_CLASS_DAY = "current_class_day"

    private var sharedPreferences: SharedPreferences

    constructor(ctx: Context){
        sharedPreferences = ctx.getSharedPreferences(PREFS_FILENAME, 0)
    }

    fun setUserType(isStudent: Boolean) {
        Log.i(TAG, "Saving userStudent type in the shared preferences")
        var editor = sharedPreferences.edit()
        editor.putBoolean(USER_TYPE, isStudent)
        editor.apply()
    }

    fun isStudent() : Boolean {
        Log.i(TAG, "Retreiving if the userStudent is student")
        return sharedPreferences.getBoolean(USER_TYPE, false)
    }

    fun getUserId() : String {
        Log.i(TAG, "Retrieving user id")
        return sharedPreferences.getString(USER_ID, "")!!
    }

    fun setUserId(userId: String) {
        Log.i(TAG, "Retrieving user id")
        val editor = sharedPreferences.edit()
        editor.putString(USER_ID, userId)
        editor.apply()
    }

    fun setCurentClass(currentClass: Class) {
        Log.i(TAG, "Setting current class -> $currentClass")
        var editor = sharedPreferences.edit()
        editor.putString(CURRENT_CLASS_ID, currentClass.classId)
        editor.putString(CURRENT_CLASS_NAME, currentClass.className)
        editor.putInt(CURRENT_CLASS_DAY, currentClass.day)
        editor.apply()
    }

    fun getCurentClass() : Class {
        Log.i(TAG, "Retrieving current class")
        var classId = sharedPreferences.getString(CURRENT_CLASS_ID, "")
        var className = sharedPreferences.getString(CURRENT_CLASS_NAME, "")
        var day = sharedPreferences.getInt(CURRENT_CLASS_DAY, 0)

        return Class(0, classId!!, className!!, day, 0,0)
    }
}