package go.id.diskominfo.POJO

import com.google.gson.annotations.SerializedName

data class Katagori(
        @SerializedName("kd_kategori")
        val kodeKatagori: String? = null,
        @SerializedName("nama_kategori")
        val namaKatagori: String? = null
)