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
import kotlinx.android.synthetic.main.toko.*
import okhttp3.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class Register2 : AppCompatActivity(), UmkmView {



    override fun showDataUmkm(listUmkm: List<Umkm>) {
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
    var sukses: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_part2)

        init()

        btn_pilih_ktp.setOnClickListener {
            getImage(1)
            btn_kirim_ktp.isClickable = true
        }
        btn_pilih_npwp.setOnClickListener {
            getImage(2)
            btn_kirim_npwp.isClickable = true
        }
                btn_pilih_iumk.setOnClickListener {
                    getImage(3)
                }
                btn_pilih_foto.setOnClickListener {
                    getImage(4)
                }
        ////        btn_pilih_reg5.setOnClickListener {
        ////            getImage(5)
        ////        }
        ////        btn_upload_reg2.setOnClickListener {
        ////            progressBarReg2.visibility = VISIBLE
        ////            uploadImanges()
        ////        }
        btn_kirim_ktp.setOnClickListener {
            uploadImanges(1)
            progressBarReg.visibility = VISIBLE




        }
        btn_kirim_npwp.setOnClickListener {
            uploadImanges(2)
            progressBarReg.visibility = VISIBLE

        }
        btn_kirim_iumk.setOnClickListener {
            uploadImanges(3)
            progressBarReg.visibility = VISIBLE
        }
        btn_kirim_toko.setOnClickListener {
            uploadImanges(4)
            progressBarReg.visibility = VISIBLE
        }

        btn_selesai_reg2.setOnClickListener {

            startActivity<MainActivity>()
        }
    }
    fun sukses(code: Int){
        if(sukses && code == 1){
            cv_npwp.visibility = VISIBLE
            sukses = false
            btn_kirim_ktp.setText("Terkirim")
            btn_kirim_ktp.setBackgroundColor(resources.getColor(R.color.colorHijau))
            btn_kirim_ktp.isClickable = false
        }
        if(sukses && code == 2 ){
            cv_iumk.visibility= VISIBLE
            btn_kirim_npwp.setText("Terkirim")
            btn_kirim_npwp.setBackgroundColor(resources.getColor(R.color.colorHijau))
            btn_kirim_npwp.isClickable = false
        }
        if(sukses && code == 3 ){
            cv_foto_toko.visibility = VISIBLE
            btn_kirim_iumk.setText("Terkirim")
            btn_kirim_iumk.setBackgroundColor(resources.getColor(R.color.colorHijau))
            btn_kirim_iumk.isClickable = false
        }
        if(sukses && code == 4 ){

            btn_kirim_toko.setText("Terkirim")
            btn_kirim_toko.setBackgroundColor(resources.getColor(R.color.colorHijau))
            btn_kirim_toko.isClickable = false
        }
    }

    private fun init() {
        //Hilangkan semua view Extra file

        val hp = intent.extras.getString("noHp")
        gson = Gson()
        apiReposirtory = ApiReposirtory()
        presenter = UmkmPresenter(apiReposirtory, gson, this)
        presenter.getUmkmDataHp(hp)
        cv_npwp.visibility = GONE
        cv_iumk.visibility = GONE
        cv_lainya.visibility = GONE
        cv_foto_toko.visibility = GONE
        btn_kirim_ktp.visibility = GONE
        btn_kirim_npwp.visibility = GONE

        progressBarReg.visibility = GONE
    }

    //
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
                getData(data, 1)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
                println(bitmap.byteCount)
                img_item_file_ktp.setImageBitmap(bitmap)
                tv_nama_file_ktp.text = file.name
                btn_kirim_ktp.visibility = VISIBLE


            }
            if (requestCode == 2) {
                println("code $requestCode")
                getData(data, 2)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
                img_item_file_npwp.setImageBitmap(bitmap)
                tv_nama_file_npwp.text = file.name
               btn_kirim_npwp.visibility = VISIBLE


            }
            if (requestCode == 3) {
                println("code $requestCode")
                getData(data, 3)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
                img_item_foto_iumk.setImageBitmap(bitmap)
                tv_nama_file_iumk.text = file.name
                btn_kirim_iumk.visibility = VISIBLE



            }
            if (requestCode == 4) {
                println("code $requestCode")
                getData(data, 4)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
                img_item_foto_toko.setImageBitmap(bitmap)
                tv_nama_file_toko.text = file.name
                btn_kirim_toko.visibility = VISIBLE



            }
//            if (requestCode == 5) {
//                println("code $requestCode")
//                getData(data, 5)
//                val bitmap = BitmapFactory.decodeFile(file5.absolutePath, options)
//                img_item_file5.setImageBitmap(bitmap)
//                tv_nama_file5.text = file5.name
//
//                jumlahData = 5
//
//            }
        }
    }

    fun uploadImanges(code: Int) {
//        val regBody = RequestBody.create(MediaType.parse("multipart/form-file"), file)

        val kd_umkm = RequestBody.create(MultipartBody.FORM, kode)

        val regBody = RequestBody.create(MediaType.parse("multipart/form-file"), file)

        var multipartBody = MultipartBody.Part.createFormData("ktp", file.name, regBody)

        if(code == 2){
            multipartBody = MultipartBody.Part.createFormData("npwp",file.name,regBody)
        }
        if(code == 3){
            multipartBody = MultipartBody.Part.createFormData("iumk",file.name,regBody)
        }
        if(code == 4){
            multipartBody = MultipartBody.Part.createFormData("toko",file.name,regBody)
        }


        val apiServices = RetrofitClient.getApiServices()

        call = apiServices.addImages1(multipartBody, kd_umkm)


        call.enqueue(object : Callback<DataRespon> {

            override fun onFailure(call: Call<DataRespon>, t: Throwable) {
                println("ERRROO ${t.message}")
                progressBarReg.visibility = GONE

                toast("Gagal Mengupload data").show()


            }

            override fun onResponse(call: Call<DataRespon>, response: Response<DataRespon>) {
                println("Ada Di Onresponse")
                val data: DataRespon? = response.body()
                toast("${data!!.pesan}").duration = Toast.LENGTH_LONG
                println("Upload ${data?.pesan}")
                if(data.pesan.equals("Sukses",true)){
                    sukses = true
                    sukses(code)


                }else{
                    sukses = false
                }
                println(sukses)
                progressBarReg.visibility = GONE

            }


        })
    }

    private fun getData(data: Intent?, kode: Int) {

        if (data != null) {
            val dataImg: Uri? = data.data
            val imgProjection = arrayOf(MediaStore.Images.Media.DATA)


            val cursor: Cursor = contentResolver.query(dataImg!!, imgProjection, null, null, null)

            cursor.moveToFirst()
            val indexImg = cursor.getColumnIndex(imgProjection[0])
            Log.d("Log", cursor.getString(indexImg))
            val partImg = cursor.getString(indexImg)
            file = File(partImg)
            options = BitmapFactory.Options()
            options.inSampleSize = 1
        }

    }

}