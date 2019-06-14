package go.id.kominfo.ADAPTER

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import go.id.kominfo.POJO.Penjualan
import go.id.kominfo.R
import org.jetbrains.anko.find

class DataPesananAdapter(val listPenjualan: List<Penjualan>,val detail:(Penjualan)->Unit):RecyclerView.Adapter<DataPesananAdapter.ViewHolder>(){


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view:View = LayoutInflater.from(p0.context).inflate(R.layout.item_data_pesanan_detail,p0,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = listPenjualan.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0.bindItem(listPenjualan[p1],detail)
}
    class ViewHolder(v: View):RecyclerView.ViewHolder(v){

        val img:ImageView = v.find(R.id.img_item_data_pesanan)
        val nama:TextView = v.find(R.id.tv_nama_produk)
        val harga:TextView = v.find(R.id.tv_harga_data_pesanan)
        val qty:TextView = v.find(R.id.tv_qty_data_pesanan)
        val total:TextView = v.find(R.id.tv_total_harga_data_pesanan)



        fun bindItem(penjualan: Penjualan,detail: (Penjualan) -> Unit){

            Picasso.get().load(penjualan.gambar_produk).into(img)

            nama.text = penjualan.nm_produk.toString()
            harga.text = penjualan.harga.toString()
            qty.text = penjualan.qty.toString()
            val totalBelanja = penjualan.qty!!.toInt() * penjualan.harga!!.toInt()
            total.text = totalBelanja.toString()



        itemView.setOnClickListener {
            detail(penjualan)
        }

        }
    }
}

