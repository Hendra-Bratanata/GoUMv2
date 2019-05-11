package go.id.kominfo.ADAPTER

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import go.id.kominfo.POJO.Pesanan
import go.id.kominfo.R
import org.jetbrains.anko.find

class PesananAdapter(val menus: List<Pesanan>, val Min:(Pesanan)->Unit, val Plus:(Pesanan)->Unit) :
    RecyclerView.Adapter<PesananAdapter.ViewHolder>() {



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

//        return ViewHolder(MakananUi().createView(AnkoContext.create(p0.context,p0)))
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.item_keranjang,p0,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int = menus.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(menus[p1],Min,Plus)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mPlus : ImageView = view.find(R.id.img_add_keranjang)
        val mMin : ImageView = view.find(R.id.img_min_keranjang)
        val gambar: ImageView = view.find(R.id.img_Gambar_keranjang)
        val nama: TextView = view.find(R.id.tv_nama_barang_keranjang)
        val harga: TextView = view.find(R.id.tv_harga_kerjang)
        val jumlah: TextView = view.find(R.id.tv_quantity_keranjang)

        fun bindItem(menus: Pesanan, Min: (Pesanan) -> Unit, Plus: (Pesanan) -> Unit) {
            Picasso.get().load(menus.gambar).into(gambar)
            jumlah.text = menus.jumlah.toString()
            nama.text = menus.nama.toString()
            harga.text = "Rp${menus.harga}"
            Log.d("Tag", "adapter " + menus.harga)

            mMin.setOnClickListener {
                Min(menus)
            }
            mPlus.setOnClickListener {
                Plus(menus)
            }

        }

    }
}