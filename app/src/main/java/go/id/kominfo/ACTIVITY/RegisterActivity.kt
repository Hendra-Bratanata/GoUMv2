package go.id.kominfo.ACTIVITY

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Window
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.random.Random


class RegisterActivity : AppCompatActivity() {
    lateinit var namaDepan:String
    lateinit var namaBelakang:String
    lateinit var email:String
    lateinit var alamat:String
    lateinit var noHp:String
    lateinit var token:String


    internal lateinit var window: Window
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        if (Build.VERSION.SDK_INT >= 21) { //ganti warna status bar diatas OS lolipop
            window = this.getWindow()
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorDarkPurple)
        }

        btn_register.setOnClickListener {
            namaDepan = edt_nama_depan.text.toString()
            namaBelakang = edt_nama_belakang.text.toString()
            email = edt_email.text.toString()
            alamat = edt_alamat.text.toString()
            noHp = edt_noHp.text.toString()
            token = ""


            for (i in 1 until 5){
                var number  = Random.nextInt(0,9)
                var num = number
                token += num.toString()

            }
            println(token)


        }
    }
}
