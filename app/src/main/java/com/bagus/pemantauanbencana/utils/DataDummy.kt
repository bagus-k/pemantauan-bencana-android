package com.bagus.pemantauanbencana.utils

import com.bagus.pemantauanbencana.source.local.DisasterEntity
import com.bagus.pemantauanbencana.source.local.UserEntity

object DataDummy {
    fun generateDummyDisaster():ArrayList<DisasterEntity> {
        val disaster = ArrayList<DisasterEntity>()

        disaster.add(DisasterEntity(
        "28 km BaratDaya JEMBER-JATIM",
        "",
        "RENDAH",
        "tangguhdcm@gmail.com",
        "Jember Kabupaten",
        "113.47",
        "17768",
        "Info Gempa Mag:2.5, 16-May-22 17:47:16 WIB, Lok:8.39 LS,113.47 BT (28 km BaratDaya JEMBER-JATIM), Kedlmn:17 Km ::BMKG-KRK",
        "0",
        "0",
        "BMKG-KRK",
        "0",
        "[{\"name\":\"files\\/WhatsApp Image 2022-05-16 at 18.10.42_sdzwq8zz.jpeg\",\"usrName\":\"WhatsApp Image 2022-05-16 at 18.10.42.jpeg\",\"size\":24581,\"type\":\"image\\/jpeg\",\"thumbnail\":\"files\\/thWhatsApp Image 2022-05-16 at 18.10.42_e5j0lbiv.jpeg\",\"thumbnail_type\":\"image\\/jpeg\",\"thumbnail_size\":5167,\"searchStr\":\"WhatsApp Image 2022-05-16 at 18.10.42.jpeg,!:sStrEnd\"}]",
        "2022-05-16 17:47:38",
        "0",
        "17768",
        "Jember Kabupaten",
        "Gempa Bumi",
        "menyebarluaskan informasi gempa dimoda yang ada",
        "",
        "0",
        "EARTHQUAKE",
        "-8.39",
        "BELUM",
        ))

        return disaster
    }

    fun generateDummyUser(): UserEntity {
        val user = UserEntity("email@gmail.com",
            "firstname",
            "lastname",
            "avatar")

//        user.add(
//            UserEntity(
//            "email@gmail.com",
//            "firstname",
//            "lastname",
//            "avatar"
//        )
//        )

        return user
    }
}