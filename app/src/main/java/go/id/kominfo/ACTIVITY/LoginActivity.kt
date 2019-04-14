package go.id.kominfo.ACTIVITY

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Window
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity


class LoginActivity : AppCompatActivity() {

    internal lateinit var window: Window

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Build.VERSION.SDK_INT >= 21) { //ganti warna status bar diatas OS lolipop
            window = this.getWindow()
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorDarkPurple)
        }

        btn_register.setOnClickListener {
            startActivity<RegisterActivity>()
        }


    }
}
