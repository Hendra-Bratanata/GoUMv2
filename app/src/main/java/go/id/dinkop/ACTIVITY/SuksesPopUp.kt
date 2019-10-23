package go.id.dinkop.ACTIVITY

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import go.id.dinkop.R
import kotlinx.android.synthetic.main.activity_sukses_pop_up.*
import org.jetbrains.anko.startActivity

class SuksesPopUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sukses_pop_up)
        btn_lihat_produk_pop_up.setOnClickListener {
            startActivity<TokoActivity>("kodeRequest" to 1003)
            finish()
        }
    }
}
