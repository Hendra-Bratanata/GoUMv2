package go.id.kominfo

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import java.lang.Integer.valueOf
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG



class PromoAdapter(val listPromo: List<Promo>) : RecyclerView.Adapter<PromoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.promo, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listPromo.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(listPromo[p1])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gambarMakanan: ImageView = view.find(R.id.img_promo)
        val tvHargaNormal: TextView = view.find(R.id.tv_hargaNormal)
        val tvHargaPromo: TextView = view.find(R.id.tv_hargaPromo)
        val tvPromo: TextView = view.find(R.id.tv_promo)

        fun bindItem(promo: Promo) {


                Picasso.get().load(promo.gambar).into(gambarMakanan)
                tvHargaNormal.text = "Rp${promo.harga}"
            tvHargaNormal.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            var hargaNorma = valueOf(promo.harga)
            var diskon = valueOf(promo.diskon)
            var hargaDiskon = diskon * hargaNorma / 100
            var hargaNet = hargaNorma - hargaDiskon

                tvPromo.text = """${promo.diskon}%"""
            tvHargaPromo.text = "Rp${hargaNet}"

        }

    }
}
