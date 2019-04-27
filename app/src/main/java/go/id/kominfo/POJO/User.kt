package go.id.kominfo.POJO

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("kd_umkm")
        val kdUmkm: String?= null,
        @SerializedName("no_hp")
        val hp: String?= null,
        @SerializedName("password")
        val pass: String?= null,
        @SerializedName("konfirmasi")
        val konfirmasi: String?= null

)