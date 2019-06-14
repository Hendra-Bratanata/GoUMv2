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

    fun getProdukbyKode(id: String): String {
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
    fun getMinuman(): String {
        val Url = BuildConfig.BASE_URL + "kategori/makanan_minuman.php"
        Log.d("data", Url)
        return Url
    }
    fun getRumahTangga(): String {
        val Url = BuildConfig.BASE_URL + "kategori/rumah_tangga.php"
        Log.d("data", Url)
        return Url
    }
    fun getJasa(): String {
        val Url = BuildConfig.BASE_URL + "kategori/jasa.php"
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
        val namaUser = URLEncoder.encode(nama, "ASCII")
        val namaToko = URLEncoder.encode(nmToko, "ASCII")
        val alamatToko = URLEncoder.encode(almToko, "ASCII")
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

    fun kirimPembelian(no_trans: String,
                      kd_produk: String,
                      tgl_trans: String,
                      nm_pembeli: String,
                      no_hp_pembeli: String,
                      alamat: String,
                      qty: String,
                      harga: String,
                      total: String
    ): String {
        val nm_pembeli = URLEncoder.encode(nm_pembeli, "ASCII")
        val no_hp_pembeli = URLEncoder.encode(no_hp_pembeli, "ASCII")
        val alamat = URLEncoder.encode(alamat, "ASCII")
        val Url = BuildConfig.BASE_URL + "transaksi.php?" +
                "no_trans=$no_trans" +
                "&kd_produk=$kd_produk" +
                "&tgl_trans=$tgl_trans" +
                "&nm_pembeli=$nm_pembeli" +
                "&no_hp_pembeli=$no_hp_pembeli" +
                "&alamat=$alamat" +
                "&qty=$qty" +
                "&harga=$harga" +
                "&total=$total"

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

    fun getUmkm(kdUmkm: String): String {
        val Url = BuildConfig.BASE_URL + "user_umkm_hp.php?hp=$kdUmkm"
        Log.d("data", Url)
        return Url
    }

    fun getUmkmKd(kd: String): String {
        val Url = BuildConfig.BASE_URL + "user_umkm_kdUmkm.php?kdUmkm=$kd"
        Log.d("data", Url)
        return Url
    }
    fun getAllProduk(cari: String): String {
        val Url = BuildConfig.BASE_URL + "Cari_Produk.php?cari=$cari"
        Log.d("data", Url)
        return Url
    }
    fun getDataPenjualan(kdUmkm: String): String {
        val Url = BuildConfig.BASE_URL + "transaksi/filter_transaksi_toko.php?kd_umkm=$kdUmkm"
        Log.d("data", Url)
        return Url
    }
    fun getDataPenjualanFilter(kdUmkm: String,status:String): String {
        val Url = BuildConfig.BASE_URL + "transaksi/filter_transaksi_toko.php?kd_umkm=$kdUmkm&status=$status"
        Log.d("data", Url)
        return Url
    }
}