package go.id.kominfo.ACTIVITY

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import go.id.kominfo.ADAPTER.DataPesananAdapter
import go.id.kominfo.ADAPTER.DataPesananListAdapter
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.INTERFACE.PesananView
import go.id.kominfo.ITEM.SharedPreference
import go.id.kominfo.POJO.Penjualan
import go.id.kominfo.PRESENTER.PenjualanPresenter

import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_sales_order.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh


class SalesOrderActivity : AppCompatActivity(),PesananView{
    override fun showData(list: List<Penjualan>) {
        swDataPesanan.isRefreshing = false
        var invSekarang = ""
        var listBaru : MutableList<Penjualan> = mutableListOf()
        listPenjualan.clear()
        for (i in list.indices){
            val penjualan = list[i]

            if (invSekarang == "" || invSekarang != penjualan.no_trans){
                invSekarang = penjualan.no_trans.toString()
                listBaru.add(penjualan)
            }
        }

        listPenjualan.addAll(listBaru)
        adapter.notifyDataSetChanged()

    }
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var presenter: PenjualanPresenter
    lateinit var listPenjualan: MutableList<Penjualan>
    lateinit var sharedPreferences: SharedPreference
    lateinit var adapter: DataPesananListAdapter
    var KDUMKM =""
    var kode = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_order)
        sharedPreferences = SharedPreference(this)
        KDUMKM = sharedPreferences.getValueString("kd_umkm").toString()

        listPenjualan = mutableListOf()
        gson = Gson()
        apiReposirtory = ApiReposirtory()
        adapter = DataPesananListAdapter(listPenjualan,{
                startActivity<SalesOrderDetail>("data" to it )
        })
        presenter = PenjualanPresenter(this,gson,apiReposirtory)

        rv_data_pesanan.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv_data_pesanan.adapter = adapter
        presenter.getPenjualan(KDUMKM)

        swDataPesanan.onRefresh {
            presenter.getPenjualan(KDUMKM)
        }
        btnAllPesananFilter.setOnClickListener {
            presenter.getPenjualan(KDUMKM)
        }
        btnProsesPesananFilter.setOnClickListener {
            presenter.getPenjualanFilter(KDUMKM,"proses")
        }
        btnSelesaiPesananFilter.setOnClickListener {
            presenter.getPenjualanFilter(KDUMKM,"selesai")
        }
        btnTerimaPesananFilter.setOnClickListener {
            presenter.getPenjualanFilter(KDUMKM,"terima")
        }
        btnTolakPesananFilter.setOnClickListener {
            presenter.getPenjualanFilter(KDUMKM,"tolak")
        }
    }
}
