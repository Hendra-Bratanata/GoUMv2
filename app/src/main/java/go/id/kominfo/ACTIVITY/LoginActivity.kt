package go.id.kominfo.ACTIVITY

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.INTERFACE.UserView
import go.id.kominfo.ITEM.MD5
import go.id.kominfo.ITEM.SharedPreference
import go.id.kominfo.POJO.User
import go.id.kominfo.PRESENTER.UserPresenter
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity


class LoginActivity : AppCompatActivity(), UserView {
    lateinit var hp: String
    lateinit var pass: String
    lateinit var confirm: String


    override fun showData(listUser: List<User>) {
        var ada = true

        for (i in listUser.indices) {

            val user: User? = listUser[i]
            println(user?.hp.equals(hp))

            if (user?.hp == hp) {
                ada = false


                val MD5 = MD5()
                pass = MD5.EncriptMD5(edt_password_login.text.toString())

                //Password
                if (user.pass == pass) {
                    println("USER: ${user.konfirmasi}")

                    if (user.konfirmasi.equals("y", true)) {
                        pref.save("LOGIN", true)
                        pref.save("noHP", "$hp")
                        pref.save("kd_umkm", "${user.kdUmkm.toString()}")
                        startActivity<MainActivity>()
                        this.finish()
                    } else {
                        tv_pesan_login.setText("Pendaftaran anda Belum dikonfirmasi")
                        tv_pesan_login.visibility = View.VISIBLE
                    }
                } else if (user.pass != pass) {
                    tv_pesan_login.text = "Password Salah"
                    tv_pesan_login.visibility = View.VISIBLE

                }

            }
        }


        if (ada) {
            tv_pesan_login.setText("Nomor tidak terdaftar ")
            tv_pesan_login.visibility = View.VISIBLE
        }

        progressBar.visibility = View.GONE
    }

    internal lateinit var window: Window
    lateinit var presenter: UserPresenter
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var pref: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        pref = SharedPreference(this)
        val loading = progressBar
        loading.visibility = View.INVISIBLE


        if (Build.VERSION.SDK_INT >= 21) { //ganti warna status bar diatas OS lolipop
            window = this.getWindow()
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorDarkPurple)
        }
        gson = Gson()
        apiReposirtory = ApiReposirtory()
        presenter = UserPresenter(apiReposirtory, gson, this)




        btn_login.setOnClickListener {
            loading.visibility = View.VISIBLE

            hp = edt_phonelogin.text.toString()

            if (edt_phonelogin.text.isNullOrBlank()) {
                edt_phonelogin.setError("Tidak Boleh Kosong")
            } else {

                if (hp.length < 12 || hp.length > 13) {
                    edt_phonelogin.setError("Nomor Tidak Valid")

                } else {

                    presenter.ambilDataUser()

                }
            }
        }




        btn_register_login.setOnClickListener {
            startActivity<RegisterActivity>()
        }


    }
}
