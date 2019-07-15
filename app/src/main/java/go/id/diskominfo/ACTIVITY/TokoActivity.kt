package go.id.diskominfo.ACTIVITY

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import go.id.diskominfo.ACTIVITY.DetailActivity.DetailProductActivity
import go.id.diskominfo.ADAPTER.ProdukAdapter
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.INTERFACE.ProdukView
import go.id.diskominfo.INTERFACE.UmkmView
import go.id.diskominfo.ITEM.SharedPreference
import go.id.diskominfo.POJO.Produk
import go.id.diskominfo.POJO.Umkm
import go.id.diskominfo.PRESENTER.ProdukPresenter
import go.id.diskominfo.PRESENTER.UmkmPresenter
import go.id.diskominfo.R
import kotlinx.android.synthetic.main.toko.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class TokoActivity : AppCompatActivity() ,UmkmView,ProdukView{
    override fun showDataProduk(listProduk: List<Produk>) {
        swProduk.isRefreshing = false
       this.listProduk.clear()
        this.listProduk.addAll(listProduk)
        produkAdapter.notifyDataSetChanged()
        if(!listProduk.isEmpty()){
            rv_produk_terjual_toko.visibility = View.VISIBLE
            tv_toko_kosong.visibility =View.GONE
        }else{
            tv_toko_kosong.visibility = View.VISIBLE
            rv_produk_terjual_toko.visibility = View.GONE
        }
    }

    override fun showDataUmkm(listUmkm: List<Umkm>) {
        umkm = listUmkm[0]

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
    var kodeRequest = 0
    lateinit var umkm :Umkm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toko)
        pref = SharedPreference(this)
        val hp = pref.getValueString("noHP")

        kodeRequest = intent.getIntExtra("kodeRequest",0)


        listProduk = mutableListOf()
        produkAdapter = ProdukAdapter(listProduk,{
            startActivity<DetailProductActivity>("detail" to it)
        })
        rv_produk_terjual_toko.layoutManager = GridLayoutManager(this,2)
        rv_produk_terjual_toko.adapter = produkAdapter

        gson = Gson()
        apiReposirtory = ApiReposirtory()

        presenter = UmkmPresenter(apiReposirtory,gson,this)

        if(kodeRequest == 1001){
            presenter.getUmkmDataHp(hp!!)
        }
        if(kodeRequest == 1002){
            presenter.getUmkmDataKdUmkm(umkm.kdUmkm)
        }
        if(kodeRequest == 1003){
            pref.getValueString("kd_umkm")?.let { presenter.getUmkmDataKdUmkm(it) }
        }

        presenterProduk = ProdukPresenter(this,gson,apiReposirtory)
        pref.getValueString("kd_umkm")?.let { presenterProduk.getProdukByKdUmkm(it) }

        btn_TambahProduk.setOnClickListener {
      startActivity<TambahProdukActivity>()
            finish()

        }
        swProduk.onRefresh {
            pref.getValueString("kd_umkm")?.let { presenterProduk.getProdukByKdUmkm(it) }

        }
        btn_aturToko.setOnClickListener {

            startActivity<EditTokoActivity>()
        }

    }
}
