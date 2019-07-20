package go.id.diskominfo.POJO

import com.google.gson.annotations.SerializedName

data class Umkm(
        @SerializedName("kd_umkm")
        val kdUmkm:String,
        @SerializedName("ktp_pemilik")
        val ktp :String,
        @SerializedName("npwp_pemilik")
        val npwp: String,
        @SerializedName("nm_pemilik")
        val nama :String,
        @SerializedName("email")
        val email :String,
        @SerializedName("no_hp")
        val hp: String,
        @SerializedName("password")
        val pass: String,
        @SerializedName("foto_usaha")
        val fotoUsaha :String,
        @SerializedName("nm_umkm")
        val namaToko:String,
        @SerializedName("alamat")
        val alamatToko :String,
        @SerializedName("ket_toko")
        val ket_toko :String,
        @SerializedName("kecamatan")
        val kecamatan :String,
        @SerializedName("kode_pos")
        val kode_pos :String,
        @SerializedName("catatan")
        val catatan	 :String
)
