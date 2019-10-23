package go.id.dinkop.ACTIVITY

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import go.id.dinkop.ApiRepository.ApiReposirtory
import go.id.dinkop.ApiRepository.RetrofitClient
import go.id.dinkop.BuildConfig
import go.id.dinkop.INTERFACE.UmkmView
import go.id.dinkop.ITEM.CameraPath
import go.id.dinkop.ITEM.HendraCompress
import go.id.dinkop.POJO.DataRespon
import go.id.dinkop.POJO.Umkm
import go.id.dinkop.PRESENTER.UmkmPresenter
import go.id.dinkop.R
import kotlinx.android.synthetic.main.item_foto_iumk.*
import kotlinx.android.synthetic.main.item_foto_lainnya.*
import kotlinx.android.synthetic.main.item_foto_toko.*
import kotlinx.android.synthetic.main.item_ktp.*
import kotlinx.android.synthetic.main.item_npwp.*
import kotlinx.android.synthetic.main.register_part2.*
import okhttp3.*
import org.jetbrains.anko.alert

import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File



class Register2Activity : AppCompatActivity(), UmkmView {



    override fun showDataUmkm(listUmkm: List<Umkm>) {
        umkm = listUmkm[0]
        kode = umkm.kdUmkm
        if (kodeEdit == "edit") {
            println(umkm.fotoKtp)
            println(umkm.fotoNpwp)
            println(umkm.fotoIumk)
            println(umkm.fotoUsaha)
            cv_npwp.visibility = VISIBLE
            cv_iumk.visibility = VISIBLE

            img_item_file_ktp.visibility = VISIBLE
            img_item_file_npwp.visibility = VISIBLE
            img_item_foto_iumk.visibility = VISIBLE

            btn_pilih_ktp.visibility = VISIBLE
            btn_pilih_iumk.visibility = VISIBLE
            btn_pilih_npwp.visibility = VISIBLE

            Picasso.get().load(umkm.fotoIumk).into(img_item_foto_iumk)
            Picasso.get().load(umkm.fotoKtp).into(img_item_file_ktp)
            Picasso.get().load(umkm.fotoNpwp).into(img_item_file_npwp)

            tv_nama_file_ktp.setText("KTP")
            tv_nama_file_npwp.setText("NPWP")
            tv_nama_file_iumk.setText("IUMK")
            tv_nama_file_toko.setText("TOKO")

            btn_selesai_reg2.visibility= VISIBLE



        }


    }

    lateinit var file: File
    lateinit var options: BitmapFactory.Options
    lateinit var kode: String
    lateinit var call: Call<DataRespon>
    lateinit var umkm: Umkm
    lateinit var presenter: UmkmPresenter
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    var sukses: Boolean = false
    var kodeEdit = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_part2)

        init()
        try{
            kodeEdit = intent.extras.getString("kode")
        }catch (e: Exception){
            e.printStackTrace()
        }

        btn_pilih_ktp.setOnClickListener {
            alert ("Foto Langsung Atau Dari Galery?"){
                positiveButton("Camera"){
                    getCamera(5)

                }
                negativeButton("Galery"){
                    getImage(1)

                }
            }.show()
        }
        btn_pilih_npwp.setOnClickListener {
            alert ("Foto Langsung Atau Dari Galery?"){
                positiveButton("Camera"){
                    getCamera(6)

                }
                negativeButton("Galery"){
                    getImage(2)

                }
            }.show()
        }
        btn_pilih_iumk.setOnClickListener {
            alert ("Foto Langsung Atau Dari Galery?"){
                positiveButton("Camera"){
                    getCamera(7)

                }
                negativeButton("Galery"){
                    getImage(3)

                }
            }.show()
        }
        btn_pilih_foto.setOnClickListener {
            alert ("Foto Langsung Atau Dari Galery?"){
                positiveButton("Camera"){
                    getCamera(8)

                }
                negativeButton("Galery"){
                    getImage(4)

                }
            }.show()
        }
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
            finish()
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
            btn_selesai_reg2.visibility= VISIBLE
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
        btn_kirim_iumk.visibility = GONE
        btn_kirim_toko.visibility = GONE
        btn_selesai_reg2.visibility = GONE
        progressBarReg.visibility = GONE
    }

    //
    private fun getImage(code: Int) {
        var inten = Intent()
        inten.setType("image/*")
        inten.setAction(Intent.ACTION_PICK)
        startActivityForResult(Intent.createChooser(inten, "open gallery"), code)

    }
    private fun getCamera(code:Int){
        try {
            var inten = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            inten.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID+".fileprovider", CameraPath.createImageFile()))
            println("data :${CameraPath.cameraFilePath}")
            startActivityForResult(inten,code)
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            println("resault $resultCode")
            if (requestCode == 1) {
                getData(data, 1)
                val bitmapAsli =  BitmapFactory.decodeFile(file.absolutePath,options)
                img_item_file_ktp.setImageBitmap(bitmapAsli)
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
                getData(data, 3)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
                img_item_foto_iumk.setImageBitmap(bitmap)
                tv_nama_file_iumk.text = file.name
                btn_kirim_iumk.visibility = VISIBLE



            }
            if (requestCode == 4) {
                getData(data, 4)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
                img_item_foto_toko.setImageBitmap(bitmap)
                tv_nama_file_toko.text = file.name
                btn_kirim_toko.visibility = VISIBLE



            }
            if(requestCode == 5){
                val stringUri = Uri.parse(CameraPath.cameraFilePath).path
                val filePath = File(stringUri)
                options = BitmapFactory.Options()
                options.inSampleSize = 16
                file = HendraCompress.compress(filePath,this)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath,options)
                img_item_file_ktp.setImageBitmap(bitmap)
                tv_nama_file_ktp.setText(file.name)
                btn_kirim_ktp.visibility = VISIBLE

            }
            if(requestCode == 6){
                val stringUri = Uri.parse(CameraPath.cameraFilePath).path
                val filePath = File(stringUri)
                options = BitmapFactory.Options()
                options.inSampleSize = 16
                file = HendraCompress.compress(filePath,this)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath,options)
                img_item_file_npwp.setImageBitmap(bitmap)
                tv_nama_file_npwp.setText(file.name)
                btn_kirim_npwp.visibility = VISIBLE

            }
            if(requestCode == 7){
                val stringUri = Uri.parse(CameraPath.cameraFilePath).path
                val filePath = File(stringUri)
                options = BitmapFactory.Options()
                options.inSampleSize = 16
                file = HendraCompress.compress(filePath,this)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath,options)
                img_item_foto_iumk.setImageBitmap(bitmap)
                tv_nama_file_iumk.setText(file.name)
                btn_kirim_iumk.visibility = VISIBLE

            }
            if(requestCode == 8){
                val stringUri = Uri.parse(CameraPath.cameraFilePath).path
                val filePath = File(stringUri)
                options = BitmapFactory.Options()
                options.inSampleSize = 16
                file = HendraCompress.compress(filePath,this)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath,options)
                img_item_foto_toko.setImageBitmap(bitmap)
                tv_nama_file_toko.setText(file.name)
                btn_kirim_toko.visibility = VISIBLE
            }
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
            val fileImg = File(partImg)
            file = HendraCompress.compress(fileImg,this)
            options = BitmapFactory.Options()
            options.inSampleSize = 1
        }

    }


}