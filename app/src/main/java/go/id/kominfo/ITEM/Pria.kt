package go.id.kominfo.ITEM


import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import go.id.kominfo.POJO.Produk
import go.id.kominfo.R
import kotlinx.android.synthetic.main.kategori1.view.*
import java.text.NumberFormat
import java.util.*

class Pria(val produk: Produk,val detail:(Produk)->Unit): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        Picasso.get().load(produk.gambar).into(viewHolder.itemView.img_katagori1)
        viewHolder.itemView.setOnClickListener {
            detail(produk)
        }
        viewHolder.itemView.tv_namaKatagori.text = produk.nm_produk
        viewHolder.itemView.tv_hargaKatagori.text = tambahTitik(produk.harga.toString())

    }

    override fun getLayout(): Int = R.layout.kategori1
    fun tambahTitik(data: String): String {
        val locale: Locale = Locale("id", "ID")
        val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(locale)





        return numberFormat.format(data.toInt())


    }
}