package go.id.kominfo.ACTIVITY

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ApiRepository.RetrofitClient
import go.id.kominfo.ITEM.ScalingUtilities
import go.id.kominfo.ITEM.ScalingUtilities.createScaledBitmap
import go.id.kominfo.ITEM.ScalingUtilities.decodeFile
import go.id.kominfo.POJO.DataRespon
import go.id.kominfo.PRESENTER.TokenPresenter
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.alertDialogLayout
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import kotlin.random.Random


class RegisterActivity : AppCompatActivity() {
    lateinit var namaDepan: String
    lateinit var namaBelakang: String
    lateinit var email: String
    lateinit var alamat: String
    lateinit var noHp: String
    lateinit var token: String
    lateinit var gson: Gson
    lateinit var presenter: TokenPresenter
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var file: File
    lateinit var file1: File
    lateinit var file2: File
    lateinit var options: BitmapFactory.Options
    lateinit var progressBar: ProgressBar
    lateinit var bitmap: Bitmap
    lateinit var byteArray: ByteArray
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
        progressBar = progressRegister
        progressBar.visibility = View.GONE
        apiReposirtory = ApiReposirtory()

        presenter = TokenPresenter(apiReposirtory)
        edt_attachment2_register.visibility = View.GONE
        edt_attachment3_register_register.visibility = View.GONE
        img_add_attachment2.visibility = View.GONE
        img_add_attachment3.visibility = View.GONE

        edt_attachment1_register.setOnClickListener {
            getImage(1)
        }
        edt_attachment2_register.setOnClickListener {
            getImage(2)
        }
        edt_attachment3_register_register.setOnClickListener {
            getImage(3)
        }


        btn_register.setOnClickListener {
            //            namaDepan = edt_nama_depan.text.toString()
//            namaBelakang = edt_nama_belakang.text.toString()
//            email = edt_email.text.toString()
//            alamat = edt_alamat.text.toString()
//            noHp = edt_noHp.text.toString()
//            token = ""
//
//
//            for (i in 1 until 5) {
//                var number = Random.nextInt(0, 9)
//                var num = number
//                token += num.toString()
//
//            }
//            println(token)
//            presenter.kirimToken(token, noHp)
            uploadImanges()


        }
    }

    private fun getImage(code: Int) {
        println("GetImages $code")
        var inten = Intent()
        inten.setType("image/*")
        inten.setAction(Intent.ACTION_PICK)
        startActivityForResult(Intent.createChooser(inten, "open gallery"), code)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            println("resault $resultCode")
            if (requestCode == 1) {
                println("code Request $requestCode")
                getData(data,1)
                img_add_attachment.setImageBitmap(BitmapFactory.decodeFile(file.absolutePath, options))
                edt_attachment1_register.setText(file.name)
                edt_attachment2_register.visibility = View.VISIBLE
                img_add_attachment2.visibility =View.VISIBLE


            }
            if (requestCode == 2) {
                println("code $requestCode")
                getData(data,2)
                img_add_attachment2.setImageBitmap(BitmapFactory.decodeFile(file1.absolutePath, options))
                edt_attachment2_register.setText(file1.name)
                edt_attachment3_register_register.visibility = View.VISIBLE
                img_add_attachment3.visibility = View.VISIBLE


            }
            if (requestCode == 3) {
                println("code $requestCode")
                getData(data,3)
                img_add_attachment3.setImageBitmap(BitmapFactory.decodeFile(file2.absolutePath, options))
                edt_attachment3_register_register.setText(file2.name)


            }
        }
    }

    fun uploadImanges() {
//        val regBody = RequestBody.create(MediaType.parse("multipart/form-file"), file)
        val regBody = RequestBody.create(MediaType.parse("multipart/form-file"),file)
        val regBody1 = RequestBody.create(MediaType.parse("multipart/form-file"),file1)
        val regBody2 = RequestBody.create(MediaType.parse("multipart/form-file"),file2)

        val multipartBody = MultipartBody.Part.createFormData("imageUpload", file.name, regBody)
        val multipartBody1 = MultipartBody.Part.createFormData("imageUpload", file1.name, regBody1)
        val multipartBody2 = MultipartBody.Part.createFormData("imageUpload", file2.name, regBody2)

        val apiServices = RetrofitClient.getApiServices()
        var upload: Call<DataRespon> = apiServices.addImages(multipartBody,multipartBody1,multipartBody2)
        progressBar.visibility = View.VISIBLE
        upload.enqueue(object : Callback<DataRespon> {

            override fun onFailure(call: Call<DataRespon>, t: Throwable) {
                println("ERRROO ${t.message}")
                progressBar.visibility = View.GONE
                toast("Gagal Mengupload data").show()


            }

            override fun onResponse(call: Call<DataRespon>, response: Response<DataRespon>) {
                println("Ada Di Onresponse")
                var data: DataRespon? = response.body()
                println("${data?.pesan}")
                println("${data?.kode}")
                toast("Sukses")


                if (data?.kode.equals("1",false)) {
                    progressBar.visibility = View.GONE


                }
            }


        })
    }

    private fun getData(data: Intent?,kode:Int) {

        if (data != null) {
            val dataImg: Uri? = data.data
            val imgProjection = arrayOf(MediaStore.Images.Media.DATA)

            val cursor: Cursor = contentResolver.query(dataImg!!, imgProjection, null, null, null)

            cursor.moveToFirst()
            val indexImg = cursor.getColumnIndex(imgProjection[0])
            Log.d("Log", cursor.getString(indexImg))
            val part_img = cursor.getString(indexImg)

            if(kode == 1){
                file = File(part_img)
            }
            if (kode == 2){
                file1 = File(part_img)
            }
            if (kode == 3){
                file2 = File(part_img)
            }

            options = BitmapFactory.Options()
            options.inSampleSize = 16
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
