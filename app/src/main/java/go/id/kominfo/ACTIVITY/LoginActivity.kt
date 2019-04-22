package go.id.kominfo.ACTIVITY

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Window
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import kotlin.random.Random


class LoginActivity : AppCompatActivity() {

    internal lateinit var window: Window

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Build.VERSION.SDK_INT >= 21) { //ganti warna status bar diatas OS lolipop
            window = this.getWindow()
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorDarkPurple)
        }



        btn_login.setOnClickListener {
            val noHp = edt_phonelogin.text.toString()
            var token = ""


            for (i in 1 until 5) {
                var number = Random.nextInt(0, 9)
                var num = number
                token += num.toString()

            }

            if (edt_phonelogin.text.isNullOrBlank()) {
                edt_phonelogin.setError("Tidak Boleh Kosong")
            } else {

                if (noHp.length <= 12 || noHp.length > 13) {
                    edt_phonelogin.setError("Nomor Tidak Valid")

                } else {
                    if (noHp.substring(0, 2).equals("62")) {
                        startActivity<VerifikasiOtpActivity>("noHp" to noHp, "Token" to token)
                    } else {
                        edt_phonelogin.setError("Harus dimulai dengan 62")
                    }


                }
            }
        }
    }
}
