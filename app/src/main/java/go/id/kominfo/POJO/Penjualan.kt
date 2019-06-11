package go.id.kominfo.POJO

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Penjualan(

        @SerializedName("kd_umkm")
        val kd_umkm: String? = null,
        @SerializedName("nm_umkm")
        val nm_umkm : String? = null,
        @SerializedName("id_trans")
        val id_trans : String? = null,
        @SerializedName("no_trans")
        val no_trans: String? = null,
        @SerializedName("tgl_trans")
        val tgl_trans: String? = null,
        @SerializedName("kd_produk")
        val kd_produk : String? = null,
        @SerializedName("nm_produk")
        val nm_produk : String? = null,
        @SerializedName("gambar_produk")
        val gambar_produk: String? = null,
        @SerializedName("nm_pembeli")
        val nm_pembeli: String? = null,
        @SerializedName("no_hp_pembeli")
        val no_hp_pembeli : String? = null,
        @SerializedName("alamat_pembeli")
        val alamat_pembeli : String? = null,
        @SerializedName("qty")
        val qty: String? = null,
        @SerializedName("harga")
        val harga: String? = null,
        @SerializedName("total")
        val total: String? = null,
        @SerializedName("status")
        val status : String? = null

        ):Serializable