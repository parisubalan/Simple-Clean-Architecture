package com.dev.cleanarchitecture.preference

import android.content.Context
import android.content.SharedPreferences

class MySharedPreference(context: Context) {

    companion object {
        private lateinit var preferences: SharedPreferences
        private var mySession: MySharedPreference? = null
        private lateinit var editor: SharedPreferences.Editor
        private const val PREFERENCE_TAG = "my_pref"
        private const val INTRO_STATUS = "intro_status"
        private const val LOGIN_STATUS = "login_status"
        private const val FCM_TOKEN = "fcm_token"
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"

        fun getInstance(context: Context): MySharedPreference? {
            if (mySession == null) {
                mySession = MySharedPreference(context)
            }
            return mySession
        }
    }

    init {
        preferences = context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    var fcmToken: String?
        get() = preferences.getString(FCM_TOKEN, "")
        set(fcmToken) {
            editor.putString(FCM_TOKEN, fcmToken)
            editor.apply()
        }
    var introStatus: Boolean
        get() = preferences.getBoolean(INTRO_STATUS, false)
        set(isFirstTime) {
            editor.putBoolean(INTRO_STATUS, isFirstTime)
            editor.apply()
        }
    var loginStatus: Boolean
        get() = preferences.getBoolean(LOGIN_STATUS, false)
        set(isLogin) {
            editor.putBoolean(LOGIN_STATUS, isLogin)
            editor.apply()
        }
    var accessToken: String?
        get() = preferences.getString(ACCESS_TOKEN, "")
        set(accessToken) {
            editor.putString(ACCESS_TOKEN, accessToken)
            editor.apply()
        }
    var refreshToken: String?
        get() = preferences.getString(REFRESH_TOKEN, "")
        set(refreshToken) {
            editor.putString(REFRESH_TOKEN, refreshToken)
            editor.apply()
        }

    fun clearMySession() {
        editor.clear()
        editor.apply()
    }
}