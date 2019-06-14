package go.id.kominfo.ADAPTER

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import go.id.kominfo.POJO.Penjualan
import go.id.kominfo.R
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find

class DataPesananListAdapter(var listPenjualan: List<Penjualan>, val detail:(Penjualan)->Unit):RecyclerView.Adapter<DataPesananListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view:View = LayoutInflater.from(p0.context).inflate(R.layout.item_data_pesanan_list,p0,false)
        return ViewHolder(view,listPenjualan)

    }

    override fun getItemCount(): Int = listPenjualan.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(listPenjualan[p1],detail)
}
    class ViewHolder(v: View,listPenjualan: List<Penjualan>):RecyclerView.ViewHolder(v){


        val nama:TextView = v.find(R.id.tv_nama_pembeli)
        val statusB:Button= v.find(R.id.btnStatusBarOrder)
        val noHp:TextView = v.find(R.id.tv_noHp)
        val invoice:TextView = v.find(R.id.tv_noInvoice)
        val jumlah:TextView = v.find(R.id.tv_qty_data_pesanan)
        val total:TextView = v.find(R.id.tv_total_harga_data_pesanan)
        val jumlahItem = listPenjualan.size


        fun bindItem(penjualan: Penjualan,detail: (Penjualan) -> Unit){


                nama.text = penjualan.nm_pembeli.toString()
                noHp.text = penjualan.no_hp_pembeli.toString()
                invoice.text = "NO/INV/"+penjualan.no_trans.toString()
                jumlah.text = jumlahItem.toString()
                total.text = penjualan.total.toString()
                statusB.text = penjualan.status
            if (penjualan.status == "terima"){
                statusB.backgroundColor = Color.GREEN
            }
            if(penjualan.status == "tolak"){
                statusB.backgroundColor = Color.RED
            }
            else{
                statusB.backgroundColor = Color.BLUE
            }

                itemView.setOnClickListener {
                    detail(penjualan)
            }

        }
    }
}

