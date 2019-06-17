package go.id.kominfo.ACTIVITY

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
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.INTERFACE.DaftarView
import go.id.kominfo.ITEM.MD5
import go.id.kominfo.ITEM.ScalingUtilities
import go.id.kominfo.ITEM.ScalingUtilities.createScaledBitmap
import go.id.kominfo.ITEM.ScalingUtilities.decodeFile
import go.id.kominfo.POJO.Daftar
import go.id.kominfo.PRESENTER.DaftarPresenter
import go.id.kominfo.R
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
                    val Md5 = MD5()
                    val newPass = Md5.EncriptMD5(pass)
                    presenter.kirimToken(ktp,npwp,nama,email,hp,newPass,namaToko,alamatToko)
                }
            }



        }
    }



    fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val width: Int = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix: Matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)

        // "RECREATE" THE NEW BITMAP
        val resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false)
        bm.recycle()
        return resizedBitmap
    }


    fun decodeFile(path: String, DESIREDWIDTH: Int, DESIREDHEIGHT: Int): String {
        var strMyImagePath: String? = null
        var scaledBitmap: Bitmap? = null

        try {
            // Part 1: Decode image
            val unscaledBitmap: Bitmap = decodeFile(path, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT)

            if (!(unscaledBitmap.width <= DESIREDWIDTH && unscaledBitmap.height <= DESIREDHEIGHT)) {
                // Part 2: Scale image
                scaledBitmap = createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT)
            } else {
                unscaledBitmap.recycle()
                return path
            }

            // Store to tmp file

            val extr: String = Environment.getExternalStorageDirectory().toString()
            val mFolder = File("$extr/TMMFOLDER")
            if (!mFolder.exists()) {
                mFolder.mkdir()
            }

            val s = "tmp.png"

            val f = File(mFolder.absolutePath, s)

            strMyImagePath = f.absolutePath
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(f)
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos)
                fos?.flush()
                fos?.close()
            } catch (e: FileNotFoundException) {

                e.printStackTrace()
            } catch (e: Exception) {

                e.printStackTrace()
            }

            scaledBitmap.recycle()
        } catch (e: Throwable) {
        }

        if (strMyImagePath == null) {
            return path
        }
        return strMyImagePath

    }

    fun saveBitmapToFile(file: File): File? {
        try {

            // BitmapFactory options to downsize the image
            val o: BitmapFactory.Options = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image

            var inputStream = FileInputStream(file)
            //Bitmap selectedBitmap = null
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val rqsize = 75

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= rqsize &&
                    o.outHeight / scale / 2 >= rqsize) {
                scale *= 2
            }

            val o2: BitmapFactory.Options = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)

            var selectedBitmap: Bitmap? = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

            // here i override the original image file
            file.createNewFile()
            var outputStream = FileOutputStream(file)

            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

            return file
        } catch (e: Exception) {
            return null
        }
    }
    fun showToastBerhasil(pesan: String){
        toast("$pesan").show()
    }

}
