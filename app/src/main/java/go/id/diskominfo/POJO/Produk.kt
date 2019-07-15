package go.id.diskominfo.POJO

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Produk(

        @SerializedName("kd_umkm")
        var kd_umkm: String? = null,
        @SerializedName("kd_produk")
        var kd_produk: String? = null,
        @SerializedName("nm_produk")
        var nm_produk: String? = null,
        @SerializedName("gambar")
        var gambar: String? = null,
        @SerializedName("jenis")
        var jenis: String? = null,
        @SerializedName("harga")
        var harga: String? = null,
        @SerializedName("diskon")
        var diskon: String? = null,
        @SerializedName("masa_diskon")
        var masa_diskon: String? = null,
        @SerializedName("deskripsi")
        var deskripsi: String? = null


        ):Serializable