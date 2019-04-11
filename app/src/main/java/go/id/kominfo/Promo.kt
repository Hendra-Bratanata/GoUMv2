package go.id.kominfo

import com.google.gson.annotations.SerializedName

data class Promo(

        @SerializedName("kd_umkm")
        val kd_umkm: String? = null,
        @SerializedName("kd_produk")
        val kd_produk: String? = null,
        @SerializedName("nm_produk")
        val nm_produk: String? = null,
        @SerializedName("gambar")
        val gambar: String? = null,
        @SerializedName("jenis")
        val jenis: String? = null,
        @SerializedName("harga")
        val harga: String? = null,
        @SerializedName("diskon")
        val diskon: String? = null,
        @SerializedName("masa_diskon")
        val masa_diskon: String? = null,
        @SerializedName("deskripsi")
        val deskripsi: String? = null


        )