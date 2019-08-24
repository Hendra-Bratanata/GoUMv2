package go.id.diskominfo.ACTIVITY

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.google.gson.Gson
import go.id.diskominfo.ACTIVITY.DetailActivity.DetailProductActivity
import go.id.diskominfo.ADAPTER.KatagoryAdapter
import go.id.diskominfo.ADAPTER.KatagoryAdapterCari
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.INTERFACE.LihatSemuaView
import go.id.diskominfo.POJO.Produk
import go.id.diskominfo.PRESENTER.LihatSemuaPresenter
import go.id.diskominfo.R
import kotlinx.android.synthetic.main.activity_cari.*
import org.jetbrains.anko.startActivity

class CariActivity : AppCompatActivity(), LihatSemuaView {
    override fun showData(list: List<Produk>) {


        listProduk.clear()
        listProduk.addAll(list)
        adapter.notifyDataSetChanged()
        if(list.isEmpty()){
            tvProdukKosong.visibility = View.VISIBLE
//            val kunci = edtCariProduk.text.toString().toUpperCase()

//            tvProdukKosong.text = "Produk Dengan Kata Kunci $kunci Tidak Tersedia"
        }else{
            tvProdukKosong.visibility = View.GONE
        }

    }
    lateinit var listProduk:MutableList<Produk>
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var presenter: LihatSemuaPresenter
    lateinit var adapter: KatagoryAdapterCari

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari)

        listProduk = mutableListOf()
        gson = Gson()
        apiReposirtory = ApiReposirtory()
        adapter = KatagoryAdapterCari(listProduk,{
            startActivity<DetailProductActivity>("detail" to it)
        })
        presenter = LihatSemuaPresenter(this,gson,apiReposirtory)
        rvCariProduk.layoutManager = GridLayoutManager(this,2)
        rvCariProduk.adapter = adapter

        btnCariProduk.setOnClickListener {
            val cari = edtCariProduk.text.toString()
            presenter.getAllByABC(cari)
        }
    }
}
