package go.id.diskominfo.ADAPTER

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import go.id.diskominfo.POJO.Produk
import go.id.diskominfo.R
import org.jetbrains.anko.find

class ProdukAdapter(val listProduk: List<Produk>,val klik:(Produk) -> Unit): RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
    val view = LayoutInflater.from(p0.context).inflate(R.layout.item_grid,p0,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int = listProduk.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
   p0.bindViewProduk(listProduk[p1],klik)
    }

    class ViewHolder(v : View):RecyclerView.ViewHolder(v) {
        val gambarMakanan: ImageView = v.find(R.id.img_grid)
        val tvHargaNormal: TextView = v.find(R.id.tv_harga_kategori_grid)
        val tvNamaProduk: TextView = v.find(R.id.tv_nama_kategori_grid)


        fun bindViewProduk(produk: Produk,klik:(Produk) -> Unit){
        Picasso.get().load(produk.gambar).into(gambarMakanan)
            tvHargaNormal.text = produk.harga
            tvNamaProduk.text = produk.nm_produk
            itemView.setOnClickListener {
                klik(produk)
            }
        }

    }
}