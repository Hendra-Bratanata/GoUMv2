package go.id.dinkop.POJO

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Banner(

        @SerializedName("id_banner")
        val idBenner: String? = null,
        @SerializedName("judul")
        val judul : String? = null,
        @SerializedName("url")
        val url : String? = null,
        @SerializedName("banner")
        val benner: String? = null,
        @SerializedName("deskripsi")
        val des: String? = null


        ):Serializable