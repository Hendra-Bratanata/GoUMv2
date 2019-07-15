package go.id.diskominfo.ACTIVITY

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.google.gson.Gson
import go.id.diskominfo.ACTIVITY.DetailActivity.DetailProductActivity
import go.id.diskominfo.ADAPTER.KatagoryAdapter
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.INTERFACE.LihatSemuaView
import go.id.diskominfo.POJO.Produk
import go.id.diskominfo.PRESENTER.LihatSemuaPresenter
import go.id.diskominfo.R
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
            presenter.getFashion()
        }
        else if(data.equals("craft",false)){
            tv_judul_Lihat_semua.text = "Craft"
            presenter.getCraft()
        }
        else if(data.equals("kuliner",false)){
            tv_judul_Lihat_semua.text = "Kuliner"
            presenter.getKuliner()
        }
        else if(data.equals("minuman",false)){
            tv_judul_Lihat_semua.text = "Makanan dan Minuman"
            presenter.getMinuman()
        }
        else if(data.equals("rumah",false)){
            tv_judul_Lihat_semua.text = "Rumah Tangga"
            presenter.getRumahTangga()
        }
        else if(data.equals("jasa",false)){
            tv_judul_Lihat_semua.text = "Jasa"
            presenter.getJasa()
        }
    }
}
