package go.id.diskominfo.ACTIVITY

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Window
import com.google.gson.Gson
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.INTERFACE.DaftarView
import go.id.diskominfo.ITEM.MD5
import go.id.diskominfo.ITEM.ScalingUtilities
import go.id.diskominfo.ITEM.ScalingUtilities.createScaledBitmap
import go.id.diskominfo.ITEM.ScalingUtilities.decodeFile
import go.id.diskominfo.POJO.Daftar
import go.id.diskominfo.PRESENTER.DaftarPresenter
import go.id.diskominfo.R
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream


class RegisterActivity : AppCompatActivity(),DaftarView {
    override fun showData(list: List<Daftar>) {
        val pesan = list[0].pesan.toString()
        val kode= list[0].kode.toString()

        if (kode == "1"){
            toast("Pendaftaran $pesan")
            startActivity<Register2Activity>("noHp" to hp)
            this.finish()
        }else{
            toast("pendaftaran $pesan")
        }
    }

    lateinit var gson: Gson
    lateinit var presenter: DaftarPresenter
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var hp:String

    internal lateinit var window: Window



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        if (Build.VERSION.SDK_INT >= 21) { //ganti warna status bar diatas OS lolipop
            window = this.getWindow()
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorDarkPurple)
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)



        gson = Gson()

        apiReposirtory = ApiReposirtory()

        presenter = DaftarPresenter(apiReposirtory,gson,this)


        btn_register.setOnClickListener {
            val ktp = edt_no_ktp.text.toString()
            val npwp = edt_no_npwp.text.toString()
            val nama = edt_nama_lengkap.text.toString()
            val email = edt_email.text.toString()
             hp = edt_noHp.text.toString()
            val pass = edt_password_register.text.toString()
            val repass = edt_repassword_register.text.toString()
            val namaToko = edt_nama_toko.text.toString()
            val alamatToko = edt_alamat.text.toString()

            if(TextUtils.isEmpty(ktp)){
                edt_no_ktp.error = "Wajib di isi"
            }
            if(TextUtils.isEmpty(npwp)){
                edt_no_npwp.error = "Wajib di isi"
            }
            if(TextUtils.isEmpty(nama)){
                edt_nama_lengkap.error = "Wajib di isi"
            }
            if(TextUtils.isEmpty(email)){
                edt_email.error = "Wajib di isi"
            }
            if(TextUtils.isEmpty(hp)){
                edt_noHp.error = "Wajib di isi"
            }
            if(TextUtils.isEmpty(pass)){
                edt_password_register.error = "Wajib di isi"
            }
            if(TextUtils.isEmpty(repass)){
                edt_repassword_register.error = "Wajib di isi"
            }
            if(TextUtils.isEmpty(namaToko)){
                edt_nama_toko.error = "Wajib di isi"
            }
            if(TextUtils.isEmpty(alamatToko)){
                edt_alamat.error = "Wajib di isi"
            }
            else{
                if (repass != pass){
                    edt_password_register.error = "Password Tidak Sama"
                    edt_repassword_register.error = "Password Tidak Sama"
                }else{
//                    val Md5 = MD5()
//                    val newPass = Md5.EncriptMD5(pass)
                    presenter.kirimToken(ktp,npwp,nama,email,hp,pass,namaToko,alamatToko)
                }
            }



        }
    }
}
