package go.id.kominfo.ADAPTER

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import go.id.kominfo.POJO.Produk
import go.id.kominfo.R
import org.jetbrains.anko.find


class KatagoryAdapter(val listProduk: List<Produk>) : RecyclerView.Adapter<KatagoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.kategori1, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listProduk.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(listProduk[p1])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gambarMakanan: ImageView = view.find(R.id.img_katagori1)
        val tvHargaNormal: TextView = view.find(R.id.tv_hargaKatagori)
        val tvNamaProduk: TextView = view.find(R.id.tv_namaKatagori)
        fun bindItem(produk: Produk) {


            Picasso.get().load(produk.gambar).into(gambarMakanan)
            tvHargaNormal.text = produk.harga.toString()
            tvNamaProduk.text = produk.nm_produk.toString()
        }

    }
}
