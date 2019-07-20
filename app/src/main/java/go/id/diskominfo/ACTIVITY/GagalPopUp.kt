package go.id.diskominfo.ACTIVITY

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import go.id.diskominfo.R
import kotlinx.android.synthetic.main.activity_gagal_pop_up.*
import org.jetbrains.anko.startActivity

class GagalPopUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gagal_pop_up)
        btn_lihat_produk_pop_up.setOnClickListener {
            startActivity<TokoActivity>("kodeRequest" to 1003 )
            finish()
        }
        btn_coba_lagi_produk_pop_up.setOnClickListener {
            startActivity<TambahProdukActivity>()
            finish()
        }
    }
}
