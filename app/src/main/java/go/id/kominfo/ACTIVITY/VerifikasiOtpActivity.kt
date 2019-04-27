package go.id.kominfo.ACTIVITY

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ITEM.SharedPreference
import go.id.kominfo.PRESENTER.TokenPresenter
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_verifikasi_otp.*
import org.jetbrains.anko.startActivity

class VerifikasiOtpActivity : AppCompatActivity() {
    lateinit var presenter: TokenPresenter
    lateinit var apiReposirtory: ApiReposirtory
    var d1: String = ""
    var d2: String = ""
    var d3: String = ""
    var d4: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifikasi_otp)
        val pref = SharedPreference(this)



        val noHp = intent.getStringExtra("noHp").toString()
        val token = intent.getStringExtra("Token").toString()
        tv_nomor_otp.text = noHp
        apiReposirtory = ApiReposirtory()
        presenter = TokenPresenter(apiReposirtory)
        presenter.kirimToken(token, noHp)
        println(token)

        btn_berikut.setOnClickListener {
            d1 = edDigit1.text.toString()
            d2 = edDigit2.text.toString()
            d3 = edDigit3.text.toString()
            d4 = edDigit4.text.toString()
            val DtokenVerifikasi = "$d1$d2$d3$d4"

            if (token == DtokenVerifikasi) {
                pref.save("LOGIN",true)
                pref.save("noHP","$noHp")
                startActivity<MainActivity>()
            }

        }


    }
}
