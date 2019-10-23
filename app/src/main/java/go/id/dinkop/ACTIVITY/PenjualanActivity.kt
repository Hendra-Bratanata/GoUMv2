package go.id.dinkop.ACTIVITY

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.Gson
import go.id.dinkop.ACTIVITY.DetailActivity.DetailDataPenjualan
import go.id.dinkop.ADAPTER.DataPesananListAdapter
import go.id.dinkop.ApiRepository.ApiReposirtory
import go.id.dinkop.INTERFACE.PesananView
import go.id.dinkop.ITEM.SharedPreference
import go.id.dinkop.POJO.Penjualan
import go.id.dinkop.PRESENTER.PenjualanPresenter

import go.id.dinkop.R
import kotlinx.android.synthetic.main.activity_data_pembelian.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.textColor


class PenjualanActivity : AppCompatActivity(),PesananView{
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
        if(listPenjualan.isEmpty()){
         tvTidakAdatransaksi.visibility =  View.VISIBLE
        }
        else{
            tvTidakAdatransaksi.visibility =  View.GONE
        }
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
        setContentView(R.layout.activity_data_penjualan)
        sharedPreferences = SharedPreference(this)
        KDUMKM = sharedPreferences.getValueString("kd_umkm").toString()

        listPenjualan = mutableListOf()
        gson = Gson()
        apiReposirtory = ApiReposirtory()
        adapter = DataPesananListAdapter(listPenjualan,{
                startActivity<DetailDataPenjualan>("data" to it )
        })
        presenter = PenjualanPresenter(this,gson,apiReposirtory)

        rv_data_pesanan.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv_data_pesanan.adapter = adapter
        presenter.getPenjualan(KDUMKM)
        colorBtnState(1)
        swDataPesanan.onRefresh {
            presenter.getPenjualan(KDUMKM)
            colorBtnState(1)
        }

        btnAllPesananFilter.setOnClickListener {
            presenter.getPenjualan(KDUMKM)
            colorBtnState(1)

        }
        btnProsesPesananFilter.setOnClickListener {
            presenter.getPenjualanFilter(KDUMKM,"proses")
            colorBtnState(2)

        }
        btnTolakPesananFilter.setOnClickListener {
            presenter.getPenjualanFilter(KDUMKM,"tolak")
            colorBtnState(3)
        }
        btnSelesaiPesananFilter.setOnClickListener {
            presenter.getPenjualanFilter(KDUMKM,"selesai")
            colorBtnState(4)
        }

    }

    override fun onResume() {
        super.onResume()
       presenter.getPenjualan(KDUMKM)
    }
    fun colorBtnState(code:Int){
        when(code){
            1 ->{
                btnAllPesananFilter.backgroundColor = ContextCompat.getColor(this,R.color.colorPrimaryDark)
                btnAllPesananFilter.textColor = Color.WHITE
                btnProsesPesananFilter.backgroundColor = Color.LTGRAY
                btnProsesPesananFilter.textColor = Color.BLACK
                btnTolakPesananFilter.backgroundColor = Color.LTGRAY
                btnTolakPesananFilter.textColor = Color.BLACK
                btnSelesaiPesananFilter.backgroundColor = Color.LTGRAY
                btnSelesaiPesananFilter.textColor = Color.BLACK


            }
            2 ->{
                btnAllPesananFilter.backgroundColor = Color.LTGRAY
                btnAllPesananFilter.textColor = Color.BLACK
                btnProsesPesananFilter.backgroundColor = ContextCompat.getColor(this,R.color.colorPrimaryDark)
                btnProsesPesananFilter.textColor = Color.WHITE
                btnTolakPesananFilter.backgroundColor = Color.LTGRAY
                btnTolakPesananFilter.textColor = Color.BLACK
                btnSelesaiPesananFilter.backgroundColor = Color.LTGRAY
                btnSelesaiPesananFilter.textColor = Color.BLACK

            }
            3 ->{
                btnAllPesananFilter.backgroundColor = Color.LTGRAY
                btnAllPesananFilter.textColor = Color.BLACK
                btnProsesPesananFilter.backgroundColor = Color.LTGRAY
                btnProsesPesananFilter.textColor = Color.BLACK
                btnTolakPesananFilter.backgroundColor = ContextCompat.getColor(this,R.color.colorPrimaryDark)
                btnTolakPesananFilter.textColor = Color.WHITE
                btnSelesaiPesananFilter.backgroundColor = Color.LTGRAY

            }
            4->{
                btnAllPesananFilter.backgroundColor = Color.LTGRAY
                btnAllPesananFilter.textColor = Color.BLACK
                btnProsesPesananFilter.backgroundColor = Color.LTGRAY
                btnProsesPesananFilter.textColor = Color.BLACK
                btnTolakPesananFilter.backgroundColor = Color.LTGRAY
                btnTolakPesananFilter.textColor = Color.BLACK
                btnSelesaiPesananFilter.backgroundColor = ContextCompat.getColor(this,R.color.colorPrimaryDark)
                btnSelesaiPesananFilter.textColor = Color.WHITE

            }

        }
    }
}
