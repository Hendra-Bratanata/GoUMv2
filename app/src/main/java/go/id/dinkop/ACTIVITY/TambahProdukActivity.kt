package go.id.dinkop.ACTIVITY

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import go.id.dinkop.ApiRepository.ApiReposirtory
import go.id.dinkop.ApiRepository.RetrofitClient
import go.id.dinkop.BuildConfig
import go.id.dinkop.INTERFACE.KatagoriView
import go.id.dinkop.ITEM.CameraPath
import go.id.dinkop.ITEM.HendraCompress
import go.id.dinkop.ITEM.SharedPreference
import go.id.dinkop.POJO.DataRespon
import go.id.dinkop.POJO.Katagori
import go.id.dinkop.PRESENTER.KatagoriPresenter

import go.id.dinkop.R
import kotlinx.android.synthetic.main.activity_new_product.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class TambahProdukActivity : AppCompatActivity(),KatagoriView {
    override fun addKatagori(listKatagory: List<Katagori>) {

        for (i in listKatagory.indices) {
            listKatagoryId.add(listKatagory[i].kodeKatagori.toString())
            listItem.add(listKatagory[i].namaKatagori.toString())
        }
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listItem)
        spinnerTambahProduk.adapter = spinnerAdapter
    }

    //kode reques izin
    var PERMISSION_ALL = 1

    // list array izin yang dibutukan sesuikan dengan manifast
    var PERMISSIONS = arrayOf(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA)
// fungsi untuk cek izin aplikasi dan meminta izin
    fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }


    lateinit var pref: SharedPreference
    lateinit var presenter: KatagoriPresenter
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var call: Call<DataRespon>

    lateinit var listKatagory: MutableList<Katagori>
    lateinit var listKatagoryId: MutableList<String>

    //    lateinit var spinner: Spinner
    lateinit var listItem: MutableList<String>
    lateinit var file: File
    lateinit var options: BitmapFactory.Options
    var katagoriItem = ""
    var gambarAda = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)

        //cek semua izin yang diperlukan dan terdaftar di list izin
        if(!hasPermissions(this, *PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
        }

//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 1100)
        pref = SharedPreference(this)
        listKatagory = mutableListOf()
        listKatagoryId = mutableListOf()
        gson = Gson()
        apiReposirtory = ApiReposirtory()



//        spinner = spinner()
        listItem = mutableListOf()



        presenter = KatagoriPresenter(this, gson, apiReposirtory)

        presenter.getKatagori()

        checkbox_promo.setOnClickListener {
            if(checkbox_promo.isChecked){
                edt_promo.visibility= View.VISIBLE
                edt_tangga_akhir.visibility= View.VISIBLE
                edt_bulan_akhir.visibility= View.VISIBLE
                edt_tahun_akhir.visibility= View.VISIBLE
            }else{
                edt_promo.visibility= View.GONE
                edt_tangga_akhir.visibility= View.GONE
                edt_bulan_akhir.visibility= View.GONE
                edt_tahun_akhir.visibility= View.GONE
            }
        }

        img_add_produk.setOnClickListener {
            alert ("Foto Langsung Atau Dari Galery?"){
                positiveButton("Camera"){
                    getCamera()
                    gambarAda = true
                }
                negativeButton("Galery"){
                    getImage()
                    gambarAda = true
                }
            }.show()

        }

        btn_TambahProdukBaru.setOnClickListener {

            val kd_umkm = pref.getValueString("kd_umkm")

            if (listKatagoryId.isNullOrEmpty()){
                katagoriItem= "null"
            }
            else{
                katagoriItem= listKatagoryId[spinnerTambahProduk.selectedItemPosition]
            }

            val nm_produk = edt_nama_produk.text.toString()
            val harga = edt_harga_produk.text.toString()
            var diskon:String? = edt_promo.text.toString()
            var tanggalAkhir:String? = edt_tangga_akhir.text.toString()
            val bulanAkhir:String? = edt_bulan_akhir.text.toString()
            val tahunAkhir:String?= edt_tahun_akhir.text.toString()


            var exp_diskon = "20$tahunAkhir-$bulanAkhir-$tanggalAkhir"
            val deskripsi = edt_deskripsi_produk.text.toString()

            if(nm_produk.isEmpty()){
                edt_nama_produk.setError("Nama Produk Tidak Boleh Kosong")
            }
            if(harga.isEmpty()){
                edt_harga_produk.setError("Harga Produk Tidak Boleh Kosong")

            }


            else{
                uploadImanges(kd_umkm,katagoriItem,nm_produk,harga,diskon,exp_diskon,deskripsi)
                progresBarProduk.visibility = View.VISIBLE
                println("kd_umkm: $kd_umkm")
                println("katagori Id: $katagoriItem")
                println("nama Produk: $nm_produk")
                println("Harga: $harga")
                println("diskon: $diskon")
                println("exp diskon: $exp_diskon")
                println(" deskripsi: $deskripsi")
            }

        }


        println("Kode umkm ${pref.getValueString("kd_umkm")}")


    }

    private fun getImage() {
        println("GetImages")
        var inten = Intent()
        inten.setType("image/*")
        inten.setAction(Intent.ACTION_PICK)
        startActivityForResult(Intent.createChooser(inten, "open gallery"), 1000)

    }
    private fun getCamera(){
        try {
            var inten = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            inten.putExtra(MediaStore.EXTRA_OUTPUT,FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID+".fileprovider",CameraPath.createImageFile()))
            println("data :${CameraPath.cameraFilePath}")
            startActivityForResult(inten,1001)
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {
                getData(data)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
                img_add_produk.setImageBitmap(bitmap)

            }
            if(requestCode == 1001){
                val stringUri = Uri.parse(CameraPath.cameraFilePath).path
                val filePath = File(stringUri)
                options = BitmapFactory.Options()
                options.inSampleSize = 16
                file = HendraCompress.compress(filePath,this)
                println("Compress: ${file}")
                println("Compress: ${file.length()}")
                val bitmap = BitmapFactory.decodeFile(file.absolutePath,options)
                img_add_produk.setImageBitmap(bitmap)

            }

        }

    }

    private fun getData(data: Intent?) {

        if (data != null) {
            val dataImg: Uri? = data.data
            val imgProjection = arrayOf(MediaStore.Images.Media.DATA)


            val cursor: Cursor = contentResolver.query(dataImg!!, imgProjection, null, null, null)

            cursor.moveToFirst()
            val indexImg = cursor.getColumnIndex(imgProjection[0])
            Log.d("Log", cursor.getString(indexImg))
            val partImg = cursor.getString(indexImg)
            val fileImages = File(partImg)
            file = HendraCompress.compress(fileImages,this)
            options = BitmapFactory.Options()
            options.inSampleSize = 16
        }

    }

    fun uploadImanges(kd_umkm: String?,
                      katagoriItem: String,
                      nm_produk: String,
                      harga: String,
                      diskon: String?,
                      exp_diskon: String?,
                      deskripsi: String) {

//        val regBody = RequestBody.create(MediaType.parse("multipart/form-file"), file)
        var dataDiskon = diskon
        var dataExp = exp_diskon
        if(dataDiskon.isNullOrBlank()){
            dataDiskon = ""
            dataExp = ""
        }
        val kd_umkmData = RequestBody.create(MultipartBody.FORM,kd_umkm)
        val ktgoriData = RequestBody.create(MultipartBody.FORM,katagoriItem)
        val namaProdukData = RequestBody.create(MultipartBody.FORM,nm_produk)
        val hargaData = RequestBody.create(MultipartBody.FORM,harga)
        var diskonData = RequestBody.create(MultipartBody.FORM,dataDiskon)
        var exp_diskonData = RequestBody.create(MultipartBody.FORM,dataExp)
        val deskripsiData = RequestBody.create(MultipartBody.FORM,deskripsi)

        val apiServices = RetrofitClient.getApiServices()

        if(gambarAda){
            val regBody = RequestBody.create(MediaType.parse("multipart/form-file"),file)
            var multipartBody = MultipartBody.Part.createFormData("gambar", file.name, regBody)
            call = apiServices.addProduk(multipartBody,kd_umkmData,ktgoriData,namaProdukData,hargaData,diskonData,exp_diskonData,deskripsiData)
        }else{
            call = apiServices.addProdukNoGambar(kd_umkmData,ktgoriData,namaProdukData,hargaData,diskonData,exp_diskonData,deskripsiData)
        }







        println("CALL ${call}")


        call.enqueue(object : Callback<DataRespon> {

            override fun onFailure(call: Call<DataRespon>, t: Throwable) {
                println("ERRROO ${t}")
                progresBarProduk.visibility = View.GONE

                toast("Gagal Mengupload data").show()
                startActivity<GagalPopUp>()
                finish()

            }

            override fun onResponse(call: Call<DataRespon>, response: Response<DataRespon>) {
                println("Ada Di Onresponse")
                println("response $response")
                println("Call $call")
                val data: DataRespon? = response.body()
                toast("Produk Telah Ditambahkan").duration = Toast.LENGTH_LONG

                progresBarProduk.visibility = View.GONE
                startActivity<SuksesPopUp>()
                finish()

            }


        })
    }

    override fun onBackPressed() {
        alert("Anda yakin ingin membatalkan aksi ini") {
            yesButton {
                super.onBackPressed()

            }
            noButton {

            }

        }.show()
    }

}

