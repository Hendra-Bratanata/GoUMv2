package go.id.kominfo.ACTIVITY

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ApiRepository.RetrofitClient
import go.id.kominfo.INTERFACE.UmkmView
import go.id.kominfo.ITEM.CommpressImages
import go.id.kominfo.ITEM.SharedPreference
import go.id.kominfo.POJO.DataRespon
import go.id.kominfo.POJO.Umkm
import go.id.kominfo.PRESENTER.UmkmPresenter
import go.id.kominfo.R
import kotlinx.android.synthetic.main.item_foto_iumk.*
import kotlinx.android.synthetic.main.item_foto_lainnya.*
import kotlinx.android.synthetic.main.item_foto_toko.*
import kotlinx.android.synthetic.main.item_ktp.*
import kotlinx.android.synthetic.main.item_npwp.*
import kotlinx.android.synthetic.main.register_part2.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.time.Duration

class Register2 : AppCompatActivity(),UmkmView {
    override fun showData(listUmkm: List<Umkm>) {
        umkm = listUmkm[0]

        kode = umkm.kdUmkm

    }

    lateinit var file: File
    lateinit var file2: File
    lateinit var file3: File
    lateinit var file4: File
    lateinit var file5: File
    lateinit var options: BitmapFactory.Options
    lateinit var pref: SharedPreference
    lateinit var kode: String
    var jumlahData = 1
    lateinit var call: Call<DataRespon>
    lateinit var umkm: Umkm
    lateinit var presenter: UmkmPresenter
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_part2)
//        pref = SharedPreference(this)
////        kode = pref.getValueString("kd_umkm").toString()
//        kode = "105"
//        init()
//
//        btn_pilih_ktp.setOnClickListener {
//            getImage(1)
//        }
//        btn_pilih_npwp.setOnClickListener {
//            getImage(2)
//        }
////        btn_pilih_reg3.setOnClickListener {
////            getImage(3)
////        }
////        btn_pilih_reg4.setOnClickListener {
////            getImage(4)
////        }
////        btn_pilih_reg5.setOnClickListener {
////            getImage(5)
////        }
////        btn_upload_reg2.setOnClickListener {
////            progressBarReg2.visibility = VISIBLE
////            uploadImanges()
////        }
//
//
//    }
//
//    private fun init() {
//        //Hilangkan semua view Extra file
//        val hp = intent.getStringExtra("noHp")
//        gson = Gson()
//        apiReposirtory = ApiReposirtory()
//        presenter = UmkmPresenter(apiReposirtory,gson,this)
//        presenter.getUmkmData(hp)
//       cv_npwp.visibility = GONE
//        cv_iumk.visibility = GONE
//        cv_lainya.visibility = GONE
//        cv_foto_toko.visibility = GONE
//
//        progressBarReg.visibility = GONE
//    }
//
//    private fun getImage(code: Int) {
//        println("GetImages $code")
//        var inten = Intent()
//        inten.setType("image/*")
//        inten.setAction(Intent.ACTION_PICK)
//        startActivityForResult(Intent.createChooser(inten, "open gallery"), code)
//
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == Activity.RESULT_OK) {
//            println("resault $resultCode")
//            if (requestCode == 1) {
//                println("code Request $requestCode")
//                getData(data, 1)
//                val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
//                println(bitmap.byteCount)
//                img_item_file_ktp.setImageBitmap(bitmap)
//                tv_nama_file_ktp.text = file.name
//
//                cv_npwp.visibility = VISIBLE
//
//
//            }
//            if (requestCode == 2) {
//                println("code $requestCode")
//                getData(data, 2)
//                val bitmap = BitmapFactory.decodeFile(file2.absolutePath, options)
//                img_item_file_npwp.setImageBitmap(bitmap)
//                tv_nama_file_npwp.text = file2.name
//                cv_npwp.visibility = VISIBLE
//                jumlahData = 2
//
//
//            }
////            if (requestCode == 3) {
////                println("code $requestCode")
////                getData(data, 3)
////                val bitmap = BitmapFactory.decodeFile(file3.absolutePath, options)
////                img_item_file3.setImageBitmap(bitmap)
////                tv_nama_file3.text = file3.name
////
////                item_upload_file4.visibility = VISIBLE
////                jumlahData = 3
////
////
////            }
////            if (requestCode == 4) {
////                println("code $requestCode")
////                getData(data, 4)
////                val bitmap = BitmapFactory.decodeFile(file4.absolutePath, options)
////                img_item_file4.setImageBitmap(bitmap)
////                tv_nama_file4.text = file4.name
////
////                item_upload_file5.visibility = VISIBLE
////
////                jumlahData = 4
////
////
////            }
////            if (requestCode == 5) {
////                println("code $requestCode")
////                getData(data, 5)
////                val bitmap = BitmapFactory.decodeFile(file5.absolutePath, options)
////                img_item_file5.setImageBitmap(bitmap)
////                tv_nama_file5.text = file5.name
////
////                jumlahData = 5
////
////            }
//        }
//    }
//
//    fun uploadImanges() {
////        val regBody = RequestBody.create(MediaType.parse("multipart/form-file"), file)
//
//        val kd_umkm = RequestBody.create(MultipartBody.FORM, kode)
//
//        val regBody = RequestBody.create(MediaType.parse("multipart/form-file"), file)
//        val regBody2 = RequestBody.create(MediaType.parse("multipart/form-file"), file2)
//        val regBody3 = RequestBody.create(MediaType.parse("multipart/form-file"), file3)
//        val regBody4 = RequestBody.create(MediaType.parse("multipart/form-file"), file4)
//        val regBody5 = RequestBody.create(MediaType.parse("multipart/form-file"), file5)
//
//
//        val multipartBody = MultipartBody.Part.createFormData("ktp", file.name, regBody)
//        val multipartBody2 = MultipartBody.Part.createFormData("npwp", file2.name, regBody2)
//        val multipartBody3 = MultipartBody.Part.createFormData("toko", file3.name, regBody3)
//        val multipartBody4 = MultipartBody.Part.createFormData("iumk", file4.name, regBody4)
//        val multipartBody5 = MultipartBody.Part.createFormData("lainnya", file5.name, regBody5)
//
//
//        val apiServices = RetrofitClient.getApiServices()
//
//
//        if (jumlahData == 1) {
//            call = apiServices.addImages1(multipartBody, kd_umkm)
//        } else if (jumlahData == 2) {
//            call = apiServices.addImages2(multipartBody, multipartBody2, kd_umkm)
//        } else if (jumlahData == 3) {
//            call = apiServices.addImages3(multipartBody, multipartBody2, multipartBody3, kd_umkm)
//        } else if (jumlahData == 4) {
//            call = apiServices.addImages4(multipartBody, multipartBody2, multipartBody3, multipartBody4, kd_umkm)
//        } else if (jumlahData == 5) {
//            call = apiServices.addImages5(multipartBody, multipartBody2, multipartBody3, multipartBody4, multipartBody5, kd_umkm)
//        }
//
//        call.enqueue(object : Callback<DataRespon> {
//
//            override fun onFailure(call: Call<DataRespon>, t: Throwable) {
//                println("ERRROO ${t.message}")
//                progressBarReg.visibility = GONE
//
//                toast("Gagal Mengupload data").show()
//
//
//            }
//
//            override fun onResponse(call: Call<DataRespon>, response: Response<DataRespon>) {
//                println("Ada Di Onresponse")
//                val data: DataRespon? = response.body()
//                toast("Permintaan anda Akan segera dikonfirmasi, Terima Kasih").duration = Toast.LENGTH_LONG
//
//
//
//                println("Upload ${data?.pesan0} ${data?.pesan1}  ${data?.pesan2}  ${data?.pesan2} ${data?.pesan4}")
//                println("Upload ${data?.kode0} ${data?.kode1}  ${data?.kode2}  ${data?.kode3} ${data?.kode4}")
//                progressBarReg.visibility = GONE
//                startActivity<MainActivity>()
//
//            }
//
//
//        })
//    }
//
//    private fun getData(data: Intent?, kode: Int) {
//
//        if (data != null) {
//            val dataImg: Uri? = data.data
//            val imgProjection = arrayOf(MediaStore.Images.Media.DATA)
//
//
//            val cursor: Cursor = contentResolver.query(dataImg!!, imgProjection, null, null, null)
//
//            cursor.moveToFirst()
//            val indexImg = cursor.getColumnIndex(imgProjection[0])
//            Log.d("Log", cursor.getString(indexImg))
//            val partImg = cursor.getString(indexImg)
//            file = File(partImg)
//            file2 = File(partImg)
//            file3 = File(partImg)
//            file4 = File(partImg)
//            file5 = File(partImg)
//
//
//
//            when (kode) {
//                1 -> file = File(partImg)
//                2 -> file2 = File(partImg)
//                3 -> file3 = File(partImg)
//                4 -> file4 = File(partImg)
//                5 -> file5 = File(partImg)
//            }
//
//
//
//
//            options = BitmapFactory.Options()
//            options.inSampleSize = 16
//        }
//
//    }

    }
}