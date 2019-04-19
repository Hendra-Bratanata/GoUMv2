package go.id.kominfo.ACTIVITY

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import go.id.kominfo.POJO.Banner
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_detail_banner.*
import kotlinx.android.synthetic.main.detail_gambar_iklan.*
import kotlinx.android.synthetic.main.syarat_ketentuan.*
import org.jetbrains.anko.toast

class DetailBannerActivity : AppCompatActivity() {
    lateinit var detailBanner : Banner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_banner)
   detailBanner  = intent.extras.getSerializable("banner") as Banner

        Picasso.get().load(detailBanner.benner).into(img_banner_iklan)
        tv_banner_iklan.text = detailBanner.judul
        tv_footer_login.setOnClickListener {
            toast("${detailBanner.url}")
        }
        tv_deskripsi_iklan.text = detailBanner.des
        item_judul_iklan.text = "Deskripsi"


    }
}
