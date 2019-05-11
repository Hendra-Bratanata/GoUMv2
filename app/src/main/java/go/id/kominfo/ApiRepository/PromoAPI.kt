package go.id.kominfo.ApiRepository

import android.util.Log
import go.id.kominfo.BuildConfig
import java.net.URLEncoder

object PromoAPI {
    fun getKatagori(): String {
        val Url = BuildConfig.BASE_URL + "index_kategori.php"
        Log.d("data", Url)
        return Url
    }
    fun getProdukbyKode(id:String): String {
        val Url = BuildConfig.BASE_URL + "produk_by_id.php?kd_umkm=$id"
        Log.d("data", Url)
        return Url
    }
    fun getProduk(): String {
        val Url = BuildConfig.BASE_URL + "produk.php"
        Log.d("data", Url)
        return Url
    }
    fun getPromo(): String {
        val Url = BuildConfig.BASE_URL + "promo.php"
        Log.d("data", Url)
        return Url
    }

    fun getFashion(): String {
        val Url = BuildConfig.BASE_URL + "kategori/fashion.php"
        Log.d("data", Url)
        return Url
    }

    fun getCraft(): String {
        val Url = BuildConfig.BASE_URL + "kategori/craft.php"
        Log.d("data", Url)
        return Url
    }

    fun getKuliner(): String {
        val Url = BuildConfig.BASE_URL + "kategori/kuliner.php"
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
    fun getUmkm(kdUmkm:String):String{
        val Url = BuildConfig.BASE_URL+"user_umkm_hp.php?hp=$kdUmkm"
        Log.d("data",Url)
        return  Url
    }
    fun getUmkmKd(kd:String):String{
        val Url = BuildConfig.BASE_URL+"user_umkm_kdUmkm.php?kdUmkm=$kd"
        Log.d("data",Url)
        return  Url
    }
}