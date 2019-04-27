package go.id.kominfo.ACTIVITY

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import go.id.kominfo.ITEM.SharedPreference
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import kotlin.random.Random


class LoginActivity : AppCompatActivity() {

    internal lateinit var window: Window

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val pref = SharedPreference(this)
        val loading  = progressBar
        loading.visibility =View.INVISIBLE


        if (Build.VERSION.SDK_INT >= 21) { //ganti warna status bar diatas OS lolipop
            window = this.getWindow()
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorDarkPurple)
        }



        btn_login.setOnClickListener {
            loading.visibility =View.VISIBLE
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

                if (noHp.length < 12 || noHp.length > 13) {
                    edt_phonelogin.setError("Nomor Tidak Valid")

                } else {
                    if (noHp.substring(0, 2).equals("62")) {
                        val nomorHp = "6285750077049"
                        val pass = "test123"
                        val password = edt_password_login.text.toString()
                        println("62 ok")
                        println("$noHp == $nomorHp" )
                        println("$pass == ${edt_password_login.text}" )



                        if(noHp == nomorHp && password == pass){
                            println("sama")
                            loading.visibility =View.INVISIBLE
                            pref.save("LOGIN",true)
                            pref.save("noHP","$noHp")
                            startActivity<MainActivity>()
                            this.finish()
                        }else{
                            toast("nomor belum terdaftar atau passwaord salah")
                        }


                    } else {
                        edt_phonelogin.setError("Harus dimulai dengan 62")
                    }


                }
                loading.visibility =View.INVISIBLE
            }
        }
    }
}
