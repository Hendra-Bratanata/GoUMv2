package go.id.dinkop.ADAPTER

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import go.id.dinkop.POJO.Produk
import go.id.dinkop.R
import org.jetbrains.anko.find
import java.text.NumberFormat
import java.util.*


class PromoAdapter(val listProduk: List<Produk>,val detail:(Produk)->Unit) : RecyclerView.Adapter<PromoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.promo, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listProduk.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(listProduk[p1],detail )
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gambarMakanan: ImageView = view.find(R.id.img_promo)
        val tvHargaNormal: TextView = view.find(R.id.tv_hargaNormal)
        val tvHargaPromo: TextView = view.find(R.id.tv_hargaPromo)
        val tvPromo: TextView = view.find(R.id.tv_promo)

        fun bindItem(produk: Produk,detail: (Produk) -> Unit) {


                Picasso.get().load(produk.gambar).into(gambarMakanan)
                tvHargaNormal.text = tambahTitik(produk.harga.toString())
            tvHargaNormal.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            var hargaNorma = produk.harga!!.toInt()
            var diskon = produk.diskon!!.toInt()
            var hargaDiskon = diskon * hargaNorma / 100
            var hargaNet = hargaNorma - hargaDiskon

            tvPromo.text = """${produk.diskon}%"""
            tvHargaPromo.text = tambahTitik(hargaNet.toString())
itemView.setOnClickListener {
    produk.harga = hargaNet.toString()
    detail(produk)
}
        }

    }

}
fun tambahTitik( data:String):String{
    val locale : Locale = Locale("id","ID")
    val numberFormat : NumberFormat = NumberFormat.getCurrencyInstance(locale)





    return numberFormat.format(data.toInt())

}