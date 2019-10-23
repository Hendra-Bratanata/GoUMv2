package go.id.dinkop.ACTIVITY

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.google.gson.Gson
import go.id.dinkop.ACTIVITY.DetailActivity.DetailProductActivity
import go.id.dinkop.ADAPTER.KatagoryAdapter
import go.id.dinkop.ApiRepository.ApiReposirtory
import go.id.dinkop.INTERFACE.LihatSemuaView
import go.id.dinkop.POJO.Produk
import go.id.dinkop.PRESENTER.LihatSemuaPresenter
import go.id.dinkop.R
import kotlinx.android.synthetic.main.detail_fashion.*
import org.jetbrains.anko.startActivity

class LihatSemuaActivity : AppCompatActivity(),LihatSemuaView {
    override fun showData(list: List<Produk>) {
        listProduk.clear()
        listProduk.addAll(list)
        adapter.notifyDataSetChanged()

    }




    lateinit var listProduk:MutableList<Produk>
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var presenter: LihatSemuaPresenter
    lateinit var adapter: KatagoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_fashion)

        listProduk = mutableListOf()
        gson = Gson()
        apiReposirtory = ApiReposirtory()
        adapter = KatagoryAdapter(listProduk,{
            startActivity<DetailProductActivity>("detail" to it)
        })
        presenter = LihatSemuaPresenter(this,gson,apiReposirtory)

        rv_detail_fashion.layoutManager = GridLayoutManager(this,2)
        rv_detail_fashion.adapter = adapter

        val data = intent.getStringExtra("kode")

        if(data.equals("fashion",true)){
            tv_judul_Lihat_semua.text = "Fashion"
            presenter.getAllFashion()
        }
        else if(data.equals("craft",false)){
            tv_judul_Lihat_semua.text = "Craft"
            presenter.getAllCraft()
        }
        else if(data.equals("kuliner",false)){
            tv_judul_Lihat_semua.text = "Kuliner"
            presenter.getAllKuliner()
        }
        else if(data.equals("minuman",false)){
            tv_judul_Lihat_semua.text = "Makanan dan Minuman"
            presenter.getAllMakanan()
        }
        else if(data.equals("rumah",false)){
            tv_judul_Lihat_semua.text = "Rumah Tangga"
            presenter.getAllRumahTangga()
        }
        else if(data.equals("jasa",false)){
            tv_judul_Lihat_semua.text = "Jasa"
            presenter.getAllJasa()
        }
        else if(data.equals("promo",false)){
            tv_judul_Lihat_semua.text = "Promo"
            presenter.getAllPromo()
        }
    }
}
