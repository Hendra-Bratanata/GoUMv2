package go.id.kominfo.POJO

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_register.*

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
        val alamatToko :String

)
