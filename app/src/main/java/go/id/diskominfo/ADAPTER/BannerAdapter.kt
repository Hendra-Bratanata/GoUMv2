package go.id.diskominfo.ADAPTER

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import go.id.diskominfo.POJO.Banner
import go.id.diskominfo.R
import org.jetbrains.anko.find


class BannerAdapter(val listProduk: List<Banner>,val detail:(Banner)-> Unit) : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.banner_iklan, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listProduk.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindItem(listProduk[p1],detail)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gambarMakanan: ImageView = view.find(R.id.imgBanner)

        fun bindItem(banner: Banner,detail: (Banner) -> Unit) {


            Picasso.get().load(banner.benner).into(gambarMakanan)
            gambarMakanan.setOnClickListener {
                detail(banner)

            }

        }

    }
}
