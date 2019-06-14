package go.id.kominfo.ACTIVITY

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import go.id.kominfo.ADAPTER.DataPesananAdapter
import go.id.kominfo.ADAPTER.DataPesananListAdapter
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.INTERFACE.PesananView
import go.id.kominfo.POJO.Penjualan
import go.id.kominfo.PRESENTER.PenjualanPresenter
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_sales_order_detail.*
import org.jetbrains.anko.allCaps
import org.jetbrains.anko.textAppearance
import org.jetbrains.anko.textColor

class SalesOrderDetail : AppCompatActivity(),PesananView {
    override fun showData(list: List<Penjualan>) {
        var listBaru: MutableList<Penjualan> = mutableListOf()
        for (i in list.indices){
            var data = list[i]

            if(data.no_trans == noTras){
                listBaru.add(list[i])
            }
        }
        listPembelian.clear()
        listPembelian.addAll(listBaru)
        adapter.notifyDataSetChanged()

    }

    lateinit var apiReposirtory: ApiReposirtory
    lateinit var adapter: DataPesananAdapter
    lateinit var gson: Gson
    lateinit var listPembelian : MutableList<Penjualan>
    lateinit var presenter: PenjualanPresenter
    var noTras ="";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_order_detail)
        var penjualan = intent.extras.get("data") as Penjualan
        noTras = penjualan.no_trans.toString()
        listPembelian = mutableListOf()
        adapter = DataPesananAdapter(listPembelian,{

        })
        gson = Gson()
        apiReposirtory = ApiReposirtory()
        presenter = PenjualanPresenter(this,gson,apiReposirtory)
        presenter.getPenjualan(penjualan.kd_umkm.toString())

        tvnama_pembeli_salesOrder.text = penjualan.nm_pembeli
        tvalamat_pembeli_salesOrder.text = penjualan.alamat_pembeli
        tvInv_pembeli_salesOrder.text = penjualan.no_trans
        tvTanggal_pembeli_salesOrder.text = penjualan.tgl_trans
        tvTotal_pembeli_salesOrder.text = penjualan.total
        //filter untuk warna status pesanan
        if (penjualan.status == "progres"){
            tvStatus_salesOrder.textColor = Color.GREEN
        }
        if (penjualan.status == "tolak"){
            tvStatus_salesOrder.textColor = Color.RED
        }
        else{
            tvStatus_salesOrder.textColor = Color.BLUE
        }
        tvStatus_salesOrder.text = penjualan.status
        tvStatus_salesOrder.allCaps = true



        rvDetailPembelian.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rvDetailPembelian.adapter = adapter


    }
}
