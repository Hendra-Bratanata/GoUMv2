package go.id.diskominfo.ACTIVITY

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View.*
import android.view.Window
import com.google.gson.Gson
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.INTERFACE.UserView
import go.id.diskominfo.ITEM.MD5
import go.id.diskominfo.ITEM.SharedPreference
import go.id.diskominfo.POJO.User
import go.id.diskominfo.PRESENTER.UserPresenter
import go.id.diskominfo.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity


class LoginActivity : AppCompatActivity(), UserView {
    lateinit var hp: String
    lateinit var pass: String
    lateinit var confirm: String


    override fun showDataUser(listUser: List<User>) {
        var ada = true

        for (i in listUser.indices) {

            val user: User? = listUser[i]
            println(user?.hp.equals(hp))

            if (user?.hp == hp) {
                ada = false


                val MD5 = MD5()
                pass = edt_password_login.text.toString()

                //Password
                if (user.pass == pass) {
                    println("USER: ${user.konfirmasi}")

                    if (user.konfirmasi.equals("y", true)) {
                        pref.save("LOGIN", true)
                        pref.save("noHP", "$hp")
                        pref.save("kd_umkm", "${user.kdUmkm.toString()}")
                        pref.save("namaPembeli",user.nm_pemilik.toString())
                        pref.save("noHpPembeli",user.hp)
                        pref.save("alamatPembeli",user.alamat.toString())
                        startActivity<MainActivity>()
                        this.finish()
                    } else {
                        tv_pesan_login.setText("Pendaftaran anda Belum dikonfirmasi")
                        tv_pesan_login.visibility = VISIBLE
                    }
                } else if (user.pass != pass) {
                    tv_pesan_login.text = "Password Salah"
                    tv_pesan_login.visibility = VISIBLE

                }

            }
        }


        if (ada) {
            tv_pesan_login.setText("Nomor tidak terdaftar ")
            tv_pesan_login.visibility = VISIBLE
        }

        progressBar.visibility = GONE
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
        loading.visibility = INVISIBLE


        if (Build.VERSION.SDK_INT >= 21) { //ganti warna status bar diatas OS lolipop
            window = this.getWindow()
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorDarkPurple)
        }
        gson = Gson()
        apiReposirtory = ApiReposirtory()
        presenter = UserPresenter(apiReposirtory, gson, this)




        btn_login.setOnClickListener {
            loading.visibility = VISIBLE

            hp = edt_phonelogin.text.toString()

            if (edt_phonelogin.text.isNullOrBlank()) {
                edt_phonelogin.setError("Tidak Boleh Kosong")
                loading.visibility = GONE
            } else {

                if (hp.length < 10 || hp.length > 13) {
                    edt_phonelogin.setError("Nomor Tidak Valid")
                    loading.visibility = GONE

                } else {

                    presenter.ambilDataUser()

                }
            }
        }




        btn_register_login.setOnClickListener {
            startActivity<RegisterActivity>()
            finish()
        }


    }
}
