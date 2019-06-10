package go.id.kominfo.ACTIVITY

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.INTERFACE.UmkmView
import go.id.kominfo.POJO.Produk
import go.id.kominfo.POJO.Umkm
import go.id.kominfo.PRESENTER.UmkmPresenter
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_profil_toko.*

class ProfilTokoActivity : AppCompatActivity(),UmkmView {
    override fun showDataUmkm(listUmkm: List<Umkm>) {
        if(listUmkm.size > 0){
            umkm = listUmkm[0]
            tvNamaTokoProfil.text = umkm.namaToko

        }

    }
    lateinit var presenter: UmkmPresenter
    lateinit var listUmkm:MutableList<Umkm>
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var umkm: Umkm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_toko)

        var produk = intent.extras.getSerializable("data") as Produk

        gson = Gson()
        apiReposirtory = ApiReposirtory()
        listUmkm = mutableListOf()

        presenter = UmkmPresenter(apiReposirtory,gson,this)
        presenter.getUmkmDataKdUmkm(produk.kd_umkm.toString())
    }
}
