package com.bagus.pemantauanbencana.ui.pushnotification

import android.content.Context
import android.preference.PreferenceManager
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object CounterNotif {
    fun capitalizeFirstLetter(original: String?): String? {
        return if (original == null || original.length == 0) {
            original
        } else original.substring(0, 1).toUpperCase() + original.substring(1)
    }

    fun getNotificationID(context: Context?): Int {
        val SP = PreferenceManager.getDefaultSharedPreferences(context)
        val counter = SP.getInt("IDcounter", 0)
        val editor = SP.edit()
        editor.putInt("IDcounter", counter + 1)
        editor.apply()
        return counter
    }

    fun md5(s: String): String {
        val MD5 = "MD5"
        try {
            // Create MD5 Hash
            val digest = MessageDigest
                .getInstance(MD5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }
}
