package go.id.kominfo.ACTIVITY

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.google.gson.Gson
import go.id.kominfo.ACTIVITY.DetailActivity.DetailProductActivity
import go.id.kominfo.ADAPTER.KatagoryAdapter
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.INTERFACE.LihatSemuaView
import go.id.kominfo.POJO.Produk
import go.id.kominfo.PRESENTER.LihatSemuaPresenter
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_cari.*
import org.jetbrains.anko.startActivity

class CariActivity : AppCompatActivity(), LihatSemuaView {
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
        setContentView(R.layout.activity_cari)

        listProduk = mutableListOf()
        gson = Gson()
        apiReposirtory = ApiReposirtory()
        adapter = KatagoryAdapter(listProduk,{
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
