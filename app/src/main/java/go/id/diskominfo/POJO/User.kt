package go.id.diskominfo.POJO

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("kd_umkm")
        val kdUmkm: String?= null,
        @SerializedName("nm_pemilik")
        val nm_pemilik: String?= null,
        @SerializedName("alamat")
        val alamat: String?= null,
        @SerializedName("no_hp")
        val hp: String?= null,
        @SerializedName("password")
        val pass: String?= null,
        @SerializedName("konfirmasi")
        val konfirmasi: String?= null

)