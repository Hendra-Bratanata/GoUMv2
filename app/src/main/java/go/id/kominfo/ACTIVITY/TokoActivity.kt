package go.id.kominfo.ACTIVITY

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import go.id.kominfo.ADAPTER.ProdukAdapter
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.INTERFACE.ProdukView
import go.id.kominfo.INTERFACE.UmkmView
import go.id.kominfo.ITEM.SharedPreference
import go.id.kominfo.POJO.Produk
import go.id.kominfo.POJO.Umkm
import go.id.kominfo.POJO.User
import go.id.kominfo.PRESENTER.ProdukPresenter
import go.id.kominfo.PRESENTER.UmkmPresenter
import go.id.kominfo.R
import kotlinx.android.synthetic.main.toko.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class TokoActivity : AppCompatActivity() ,UmkmView,ProdukView{
    override fun showDataProduk(listProduk: List<Produk>) {
       this.listProduk.clear()
        this.listProduk.addAll(listProduk)
        produkAdapter.notifyDataSetChanged()

    }

    override fun showDataUmkm(listUmkm: List<Umkm>) {
        val umkm = listUmkm[0]

        tv_toko_toko.setText(umkm.namaToko)
        Picasso.get().load(umkm.fotoUsaha).into(img_toko)


    }






    lateinit var listProduk: MutableList<Produk>
    lateinit var presenter: UmkmPresenter
    lateinit var presenterProduk: ProdukPresenter
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var pref : SharedPreference
    lateinit var produkAdapter: ProdukAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toko)
        pref = SharedPreference(this)
        val hp = pref.getValueString("noHP")
        listProduk = mutableListOf()
        produkAdapter = ProdukAdapter(listProduk,{
            startActivity<DetailProductActivity>("detail" to it )
        })
        rv_produk_terjual_toko.layoutManager = GridLayoutManager(this,2)
        rv_produk_terjual_toko.adapter = produkAdapter






        gson = Gson()
        apiReposirtory = ApiReposirtory()

        presenter = UmkmPresenter(apiReposirtory,gson,this)
        presenter.getUmkmDataHp(hp!!)

        presenterProduk = ProdukPresenter(this,gson,apiReposirtory)
        presenterProduk.getProduk()

        btn_TambahProduk.setOnClickListener {
      startActivity<NewProductActivity>()





        }

    }
}
