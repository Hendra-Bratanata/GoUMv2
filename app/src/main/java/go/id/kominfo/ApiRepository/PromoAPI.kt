package go.id.kominfo.ApiRepository

import android.util.Log
import go.id.kominfo.BuildConfig
import java.net.URLEncoder

object PromoAPI {
    fun getPromo(): String {
        val Url = BuildConfig.BASE_URL + "promo.php"
        Log.d("data", Url)
        return Url
    }

    fun getfashionPria(): String {
        val Url = BuildConfig.BASE_URL + "kategori/fashion_pria.php"
        Log.d("data", Url)
        return Url
    }

    fun getfashionWanita(): String {
        val Url = BuildConfig.BASE_URL + "kategori/fashion_wanita.php"
        Log.d("data", Url)
        return Url
    }

    fun getMinuman(): String {
        val Url = BuildConfig.BASE_URL + "kategori/minuman.php"
        Log.d("data", Url)
        return Url
    }

    fun kirimRegister(ktp: String,
                      npwp: String,
                      nama: String,
                      email: String,
                      hp: String,
                      pass: String,
                      nmToko: String,
                      almToko: String
                   ): String {
        val namaUser = URLEncoder.encode(nama,"ASCII")
        val namaToko = URLEncoder.encode(nmToko,"ASCII")
        val alamatToko = URLEncoder.encode(almToko,"ASCII")
        val Url = BuildConfig.BASE_URL + "register.php?ktp_pemilik=$ktp" +
                "&npwp_pemilik=$npwp" +
                "&nm_pemilik=$namaUser" +
                "&email=$email" +
                "&no_hp=$hp" +
                "&password=$pass" +
                "&nm_umkm=$namaToko" +
                "&alamat=$alamatToko"

        Log.d("data", Url)
        return Url
    }

    fun getBenner(): String {
        val Url = BuildConfig.BASE_URL + "banner.php"
        Log.d("data", Url)
        return Url
    }

    fun kirimFoto(data: String): String {
        val Url = BuildConfig.BASE_URLI + "addImage.php?imageUpload=$data"
        Log.d("data", "foto dikirim")
        return Url
    }
    fun getUser(): String {
        val Url = BuildConfig.BASE_URL + "login.php"
        Log.d("data", Url)
        return Url
    }
}