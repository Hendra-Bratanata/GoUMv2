package go.id.dinkop.ACTIVITY.DetailActivity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.Gson
import go.id.dinkop.ADAPTER.DataPesananAdapter
import go.id.dinkop.ApiRepository.ApiReposirtory
import go.id.dinkop.ApiRepository.PromoAPI
import go.id.dinkop.INTERFACE.PesananView
import go.id.dinkop.POJO.Penjualan
import go.id.dinkop.PRESENTER.PenjualanPresenter
import go.id.dinkop.R
import kotlinx.android.synthetic.main.activity_data_penjualan_detail.btnSelesaiOrder
import kotlinx.android.synthetic.main.activity_data_penjualan_detail.rvDetailPembelian
import kotlinx.android.synthetic.main.activity_data_penjualan_detail.tvInv_pembeli_salesOrder
import kotlinx.android.synthetic.main.activity_data_penjualan_detail.tvStatus_salesOrder
import kotlinx.android.synthetic.main.activity_data_penjualan_detail.tvTanggal_pembeli_salesOrder
import kotlinx.android.synthetic.main.activity_data_penjualan_detail.tvTotal_pembeli_salesOrder
import kotlinx.android.synthetic.main.activity_data_pembelian_detail.*
import org.jetbrains.anko.*

class DetailDataPembelian : AppCompatActivity(),PesananView {
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
    var noTras =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_pembelian_detail)
        var penjualan = intent.extras.get("data") as Penjualan
        noTras = penjualan.no_trans.toString()
        listPembelian = mutableListOf()
        adapter = DataPesananAdapter(listPembelian,{

        })
        gson = Gson()
        apiReposirtory = ApiReposirtory()
        presenter = PenjualanPresenter(this,gson,apiReposirtory)
        presenter.getPenjualan(penjualan.kd_umkm.toString())

        tvnama_toko_salesOrder.text = penjualan.nm_umkm
        tvalamat_toko_salesOrder.text = penjualan.alamat
        tvInv_pembeli_salesOrder.text = penjualan.no_trans
        tvTanggal_pembeli_salesOrder.text = penjualan.tgl_trans
        tvTotal_pembeli_salesOrder.text = penjualan.total
        //filter untuk warna status pesanan dan button
        if (penjualan.status == "konfirmasi" ){
            tvStatus_salesOrder.textColor = Color.LTGRAY
            btnSelesaiOrder.visibility = View.GONE
        }
        if (penjualan.status == "proses"){
            tvStatus_salesOrder.textColor = Color.GREEN
            btnSelesaiOrder.visibility = View.VISIBLE
        }
        if (penjualan.status == "tolak"){
            tvStatus_salesOrder.textColor = Color.RED
            btnSelesaiOrder.visibility = View.GONE
        }
        if (penjualan.status == "selesai" ){
            tvStatus_salesOrder.textColor = Color.DKGRAY
            btnSelesaiOrder.visibility = View.GONE

        }


        tvStatus_salesOrder.text = penjualan.status
        tvStatus_salesOrder.allCaps = true



        rvDetailPembelian.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rvDetailPembelian.adapter = adapter

        btnSelesaiOrder.setOnClickListener {
            alert ("Apakah Pensanan Anda Sudah Selesai?"){
                positiveButton("Ya"){
                    doAsync {
                        apiReposirtory.doRequest(PromoAPI.setDataPenjualanStatus(penjualan.kd_umkm.toString(),"selesai",penjualan.no_trans.toString()))
                        finish()
                    }
                    it.dismiss()
                }
                negativeButton("Belum"){
                    it.dismiss()
                }
            }.show()

        }


    }
}
