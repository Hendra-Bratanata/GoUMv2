package go.id.diskominfo.ACTIVITY

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.INTERFACE.UmkmView
import go.id.diskominfo.ITEM.BukaWhatsApp
import go.id.diskominfo.POJO.Produk
import go.id.diskominfo.POJO.Umkm
import go.id.diskominfo.PRESENTER.UmkmPresenter
import go.id.diskominfo.R
import kotlinx.android.synthetic.main.activity_profil_toko.*
import kotlinx.android.synthetic.main.catatan_toko.*
import kotlinx.android.synthetic.main.deskripsi_toko.*
import kotlinx.android.synthetic.main.informasi_toko.*

class DetailToko : AppCompatActivity(),UmkmView {
    override fun showDataUmkm(listUmkm: List<Umkm>) {
            umkm = listUmkm[0]
            tvNamaTokoProfil.setText(umkm.namaToko)
        tv_deskripsi_toko.setText(umkm.ket_toko)
        tv_catatan_toko.setText(umkm.email)
        tv_nama_pemilik.setText(umkm.nama)
       Picasso.get().load(umkm.fotoUsaha).into(img_toko)



    }
    lateinit var presenter: UmkmPresenter
    lateinit var listUmkm:MutableList<Umkm>
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var umkm: Umkm
    lateinit var wa :BukaWhatsApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_toko)
        wa = BukaWhatsApp()

        var produk = intent.extras.getSerializable("data") as Produk

        gson = Gson()
        apiReposirtory = ApiReposirtory()
        listUmkm = mutableListOf()

        presenter = UmkmPresenter(apiReposirtory,gson,this)
        presenter.getUmkmDataKdUmkm(produk.kd_umkm.toString())
    btn_chatWa.setOnClickListener {
        val pesan = "Saya tertarik dengan iklan anda ${produk.nm_produk} di App GOUM, bisa minta info lebih lanjut?"
        wa.kirimPesanWa(this,umkm.hp,pesan)
    }

    }
}
