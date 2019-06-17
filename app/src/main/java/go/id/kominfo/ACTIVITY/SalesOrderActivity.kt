package go.id.kominfo.ACTIVITY

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Button
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
import org.jetbrains.anko.textColor


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
        btnKirimPesananFilter.setOnClickListener {
            presenter.getPenjualanFilter(KDUMKM,"kirim")
            colorBtnState(3)
        }
        btnTolakPesananFilter.setOnClickListener {
            presenter.getPenjualanFilter(KDUMKM,"tolak")
            colorBtnState(4)
        }

        btnTerimaPesananFilter.setOnClickListener {
            presenter.getPenjualanFilter(KDUMKM,"terima")
            colorBtnState(5)
        }
        btnSelesaiPesananFilter.setOnClickListener {
            presenter.getPenjualanFilter(KDUMKM,"selesai")
            colorBtnState(6)
        }

        btnKomplainPesananFilter.setOnClickListener {
            presenter.getPenjualanFilter(KDUMKM,"komplain")
            colorBtnState(7)
        }
    }

    override fun onResume() {
        super.onResume()
       presenter.getPenjualan(KDUMKM)
    }
  fun colorBtnState(code:Int){
      when(code){
          1 ->{
              btnAllPesananFilter.backgroundColor = ContextCompat.getColor(this,R.color.colorDarkPurple)
              btnAllPesananFilter.textColor = Color.WHITE
              btnProsesPesananFilter.backgroundColor = Color.LTGRAY
              btnKirimPesananFilter.backgroundColor =Color.LTGRAY
              btnTolakPesananFilter.backgroundColor = Color.LTGRAY
              btnTerimaPesananFilter.backgroundColor = Color.LTGRAY
              btnSelesaiPesananFilter.backgroundColor = Color.LTGRAY
              btnKomplainPesananFilter.backgroundColor = Color.LTGRAY

          }
          2 ->{
              btnAllPesananFilter.backgroundColor = Color.LTGRAY
              btnAllPesananFilter.textColor = Color.BLACK
              btnProsesPesananFilter.backgroundColor = Color.DKGRAY
              btnKirimPesananFilter.backgroundColor =Color.LTGRAY
              btnTolakPesananFilter.backgroundColor = Color.LTGRAY
              btnTerimaPesananFilter.backgroundColor = Color.LTGRAY
              btnSelesaiPesananFilter.backgroundColor = Color.LTGRAY
              btnKomplainPesananFilter.backgroundColor = Color.LTGRAY
          }
          3 ->{
              btnAllPesananFilter.backgroundColor = Color.LTGRAY
              btnProsesPesananFilter.backgroundColor = Color.LTGRAY
              btnKirimPesananFilter.backgroundColor =Color.DKGRAY
              btnTolakPesananFilter.backgroundColor = Color.LTGRAY
              btnTerimaPesananFilter.backgroundColor = Color.LTGRAY
              btnSelesaiPesananFilter.backgroundColor = Color.LTGRAY
              btnKomplainPesananFilter.backgroundColor = Color.LTGRAY
          }
          4->{
              btnAllPesananFilter.backgroundColor = Color.LTGRAY
              btnProsesPesananFilter.backgroundColor = Color.LTGRAY
              btnKirimPesananFilter.backgroundColor =Color.LTGRAY
              btnTolakPesananFilter.backgroundColor = Color.DKGRAY
              btnTerimaPesananFilter.backgroundColor = Color.LTGRAY
              btnSelesaiPesananFilter.backgroundColor = Color.LTGRAY
              btnKomplainPesananFilter.backgroundColor = Color.LTGRAY
          }
          5 ->{
              btnAllPesananFilter.backgroundColor = Color.LTGRAY
              btnProsesPesananFilter.backgroundColor = Color.LTGRAY
              btnKirimPesananFilter.backgroundColor =Color.LTGRAY
              btnTolakPesananFilter.backgroundColor = Color.LTGRAY
              btnTerimaPesananFilter.backgroundColor = Color.DKGRAY
              btnSelesaiPesananFilter.backgroundColor = Color.LTGRAY
              btnKomplainPesananFilter.backgroundColor = Color.LTGRAY
          }
          6 ->{
              btnAllPesananFilter.backgroundColor = Color.LTGRAY
              btnProsesPesananFilter.backgroundColor = Color.LTGRAY
              btnKirimPesananFilter.backgroundColor =Color.LTGRAY
              btnTolakPesananFilter.backgroundColor = Color.LTGRAY
              btnTerimaPesananFilter.backgroundColor = Color.LTGRAY
              btnSelesaiPesananFilter.backgroundColor = Color.DKGRAY
              btnKomplainPesananFilter.backgroundColor = Color.LTGRAY
          }
          7 ->{
              btnAllPesananFilter.backgroundColor = Color.LTGRAY
              btnProsesPesananFilter.backgroundColor = Color.LTGRAY
              btnKirimPesananFilter.backgroundColor =Color.LTGRAY
              btnTolakPesananFilter.backgroundColor = Color.LTGRAY
              btnTerimaPesananFilter.backgroundColor = Color.LTGRAY
              btnSelesaiPesananFilter.backgroundColor = Color.LTGRAY
              btnKomplainPesananFilter.backgroundColor = Color.DKGRAY
          }
      }
  }
}
