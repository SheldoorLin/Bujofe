package com.sheldon.bujofe.login

import android.content.Context
import com.sheldon.bujofe.BujofeApplication

object UserManager {

    private const val USER_DATA = "userData"
    private const val USER_ID = "uid"
    private const val USER_NAME = "userName"
    private const val USER_EMAIL = "userEmail"
    private const val USER_PHOTOURL = "userPhotoUrl"

    var userId: String? = null
        get() = BujofeApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            ?.getString(USER_ID, null)
        set(value) {
            field = when (value) {
                null -> {
                    BujofeApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_ID)
                        .apply()
                    null
                }
                else -> {
                    BujofeApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_ID, value)
                        .apply()
                    value
                }
            }
        }

    var userName: String? = null
        get() = BujofeApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            ?.getString(USER_NAME, null)
        set(value) {
            field = when (value) {
                null -> {
                    BujofeApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_NAME)
                        .apply()
                    null
                }
                else -> {
                    BujofeApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_NAME, value)
                        .apply()
                    value
                }
            }
        }

    var userEmail: String? = null
        get() = BujofeApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            ?.getString(USER_EMAIL, null)
        set(value) {
            field = when (value) {
                null -> {
                    BujofeApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_EMAIL)
                        .apply()
                    null
                }
                else -> {
                    BujofeApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_EMAIL, value)
                        .apply()
                    value
                }
            }
        }

    var userPhotoUrl: String? = null
        get() = BujofeApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            ?.getString(USER_PHOTOURL, null)
        set(value) {
            field = when (value) {
                null -> {
                    BujofeApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_PHOTOURL)
                        .apply()
                    null
                }
                else -> {
                    BujofeApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_PHOTOURL, value)
                        .apply()
                    value
                }
            }
        }

    val isLoggedIn: Boolean
        get() = !(userId == null || userName == null)

    fun clear() {
        userId = null
        userName = null
        userEmail = null
    }
}