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
import java.text.NumberFormat
import java.util.*


class KatagoryAdapter(val listProduk: List<Produk>,val detail:(Produk)-> Unit) : RecyclerView.Adapter<KatagoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.kategori1, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listProduk.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(listProduk[p1],detail)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gambarMakanan: ImageView = view.find(R.id.img_katagori1)
        val tvHargaNormal: TextView = view.find(R.id.tv_hargaKatagori)
        val tvNamaProduk: TextView = view.find(R.id.tv_namaKatagori)
        fun bindItem(produk: Produk,detail: (Produk) -> Unit) {


            Picasso.get().load(produk.gambar).into(gambarMakanan)
            tvHargaNormal.text = tambahTitik(produk.harga.toString())
            tvNamaProduk.text = produk.nm_produk.toString()
            itemView.setOnClickListener {
                detail(produk)
            }

        }

    }

fun tambahTitik( data:String):String{
    val locale : Locale = Locale("id","ID")
    val numberFormat : NumberFormat = NumberFormat.getCurrencyInstance(locale)





    return numberFormat.format(data.toInt())

}
}