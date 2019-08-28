package go.id.diskominfo.ACTIVITY

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.Gson
import go.id.diskominfo.ACTIVITY.DetailActivity.DetailDataPembelian
import go.id.diskominfo.ADAPTER.DataPembelianListAdapter
import go.id.diskominfo.ADAPTER.DataPesananListAdapter
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.INTERFACE.PesananView
import go.id.diskominfo.ITEM.SharedPreference
import go.id.diskominfo.POJO.Penjualan
import go.id.diskominfo.PRESENTER.PenjualanPresenter

import go.id.diskominfo.R
import kotlinx.android.synthetic.main.activity_data_pembelian.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.textColor


class PembelianActivity : AppCompatActivity(),PesananView{
    override fun showData(list: List<Penjualan>) {
        swDataPesanan.isRefreshing = false
        var invSekarang = ""
        var kdToko = ""

        var listBaru : MutableList<Penjualan> = mutableListOf()
        listPenjualan.clear()
        for (i in list.indices){
            val penjualan = list[i]
            if(invSekarang == "" || invSekarang != penjualan.no_trans){
                invSekarang=penjualan.no_trans.toString()
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
    lateinit var adapter: DataPembelianListAdapter
    var noHp =""
    var kode = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_pembelian)
        sharedPreferences = SharedPreference(this)
        noHp = sharedPreferences.getValueString("noHpPembeli").toString()

        listPenjualan = mutableListOf()
        gson = Gson()
        apiReposirtory = ApiReposirtory()
        adapter = DataPembelianListAdapter(listPenjualan,{
                startActivity<DetailDataPembelian>("data" to it )
        })
        presenter = PenjualanPresenter(this,gson,apiReposirtory)

        rv_data_pesanan.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv_data_pesanan.adapter = adapter
        presenter.getPembelian(noHp)
        colorBtnState(1)

        swDataPesanan.onRefresh {
            presenter.getPembelian(noHp)
            colorBtnState(1)
        }



        btnAllPesananFilter.setOnClickListener {
            presenter.getPembelian(noHp)
            colorBtnState(1)

        }
        btnProsesPesananFilter.setOnClickListener {
            presenter.getPembelianFilter(noHp,"proses")
            colorBtnState(2)

        }
        btnTolakPesananFilter.setOnClickListener {
            presenter.getPembelianFilter(noHp,"tolak")
            colorBtnState(3)
        }

        btnSelesaiPesananFilter.setOnClickListener {
            presenter.getPembelianFilter(noHp,"selesai")
            colorBtnState(4)
        }

    }

    override fun onResume() {
        super.onResume()
       presenter.getPembelian(noHp)
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
