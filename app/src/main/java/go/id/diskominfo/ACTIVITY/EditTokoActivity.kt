package go.id.diskominfo.ACTIVITY

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View.*
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.ApiRepository.RetrofitClient
import go.id.diskominfo.BuildConfig
import go.id.diskominfo.INTERFACE.UmkmView
import go.id.diskominfo.ITEM.CameraPath
import go.id.diskominfo.ITEM.HendraCompress
import go.id.diskominfo.ITEM.SharedPreference
import go.id.diskominfo.POJO.DataRespon
import go.id.diskominfo.POJO.Umkm
import go.id.diskominfo.PRESENTER.UmkmPresenter
import go.id.diskominfo.R
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_new_product.*
import kotlinx.android.synthetic.main.edit_toko.*
import kotlinx.android.synthetic.main.register_part2.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.MultipartBody.FORM
import okhttp3.RequestBody
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditTokoActivity : AppCompatActivity(),UmkmView {
    override fun showDataUmkm(listUmkm: List<Umkm>) {
        umkm = listUmkm[0]
        edt_nama_toko_edit_toko.setText(umkm.namaToko)
        edt_alamat_toko_edit_toko.setText(umkm.alamatToko)
        edt_kecamatan_toko_edit_toko.setText(umkm.kecamatan)
        edt_kode_pos_toko_edit_toko.setText(umkm.kode_pos)
        edt_deskripsi_edit_toko.setText(umkm.ket_toko)
        edt_nama_pemilik_edit_toko.setText(umkm.nama)
        edt_catatan_toko_edit_toko.setText(umkm.catatan)
        Picasso.get().load(umkm.fotoUsaha).into(img_tambah_foto)
    }

    lateinit var fileOri: File
    lateinit var pref : SharedPreference
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var presenter : UmkmPresenter
    lateinit var umkm: Umkm
    lateinit var file: File
    lateinit var options: BitmapFactory.Options
    lateinit var call: Call<DataRespon>
    var gambar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_toko)
        pref = SharedPreference(this)

        gson = Gson()
        apiReposirtory = ApiReposirtory()
        presenter = UmkmPresenter(apiReposirtory,gson,this)
        pref.getValueString("kd_umkm")?.let { presenter.getUmkmDataKdUmkm(it) }


        btn_batal_edit_toko.setOnClickListener {
            alert("anda yakin ingin batal?") {
                yesButton {
                    finish()
                }
                noButton {

                }
            }.show()
        }

        btn_simpan_edit_toko.setOnClickListener {
            alert("Apakah data Sudah benar?") {
                yesButton {
                    kirimData()
                }
                noButton {

                }
            }.show()
        }
        img_tambah_foto.setOnClickListener {
            alert ("Foto Langsung Atau Dari Galery?"){
                positiveButton("Camera"){
                    getCamera()

                }
                negativeButton("Galery"){
                    getImage()

                }
            }.show()
        }
        btn__editBerkas_toko.setOnClickListener {
            startActivity<Register2Activity>("noHp" to umkm.hp,"kode" to "edit")
        }



    }

    private fun getImage() {
        val inten = Intent()
        inten.setType("image/*")
        inten.setAction(Intent.ACTION_PICK)
        startActivityForResult(Intent.createChooser(inten,"open gallery"),1000)
    }
    private fun getCamera(){
        try {
            var inten = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            inten.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID+".fileprovider", CameraPath.createImageFile()))
            println("data :${CameraPath.cameraFilePath}")
            startActivityForResult(inten,1001)
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 1000){
                getData(data)
                val bitmap = BitmapFactory.decodeFile(fileOri.absolutePath, options)
                img_tambah_foto.setImageBitmap(bitmap)
                gambar = true

            }
            if(requestCode == 1001){
                val stringUri = Uri.parse(CameraPath.cameraFilePath).path
                val filePath = File(stringUri)
                options = BitmapFactory.Options()
                options.inSampleSize = 16
                file = HendraCompress.compress(filePath,this)
                println("Compress: ${file}")
                println("Compress: ${file.length()}")
                img_tambah_foto.setImageURI(Uri.parse(CameraPath.cameraFilePath))

            }
        }
    }

    fun kirimData(){
        val namaToko = edt_nama_toko_edit_toko.text.toString()
        val namaPemilik = edt_nama_pemilik_edit_toko.text.toString()
        val alamatToko = edt_alamat_toko_edit_toko.text.toString()
        val kecamatan = edt_kecamatan_toko_edit_toko.text.toString()
        val kodePos = edt_kode_pos_toko_edit_toko.text.toString()
        val deskripsi = edt_deskripsi_edit_toko.text.toString()
        val catatan = edt_catatan_toko_edit_toko.text.toString()

        uploadData(namaToko,namaPemilik,alamatToko,kecamatan,kodePos,deskripsi,catatan)
        progressBarToko.visibility = VISIBLE



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
            fileOri = File(partImg)
            file = HendraCompress.compress(fileOri,this)
            options = BitmapFactory.Options()
            options.inSampleSize = 1
        }

    }
    fun uploadData(namaToko: String, namaPemilik: String, alamatToko: String, kecamatan: String, kodePos: String, deskripsi: String, catatan: String) {
//        val regBody = RequestBody.create(MediaType.parse("multipart/form-file"), file)

        val kd_umkm = RequestBody.create(FORM, umkm.kdUmkm)
        val namaTokos = RequestBody.create(FORM,namaToko)
        val namaPemiliks =  RequestBody.create(FORM,namaPemilik)
        val alamatTokos = RequestBody.create(FORM,alamatToko)
        val kecamatans = RequestBody.create(FORM,kecamatan)
        val kodePoss = RequestBody.create((FORM),kodePos)
        val deskripsis = RequestBody.create(FORM,deskripsi)
        val catatans =RequestBody.create(FORM,catatan)





        val apiServices = RetrofitClient.getApiServices()
        if(gambar){
            val regBody = RequestBody.create(MediaType.parse("multipart/form-file"), file)
            var multipartBody = MultipartBody.Part.createFormData("toko", file.name, regBody)
            call = apiServices.editTokoFoto(multipartBody,kd_umkm,namaPemiliks,alamatTokos,namaTokos,kecamatans,kodePoss, deskripsis,catatans)
        }else{
            call = apiServices.editToko(kd_umkm,namaPemiliks,alamatTokos,namaTokos,kecamatans,kodePoss, deskripsis,catatans)
        }

        call.enqueue(object : Callback<DataRespon> {
            override fun onFailure(call: Call<DataRespon>, t: Throwable) {
                println("ERRROO ${t.message}")
                progressBarReg.visibility = GONE
                toast("Gagal Mengupload data").show()
            }

            override fun onResponse(call: Call<DataRespon>, response: Response<DataRespon>) {
                println("Ada Di Onresponse")
                val data: DataRespon? = response.body()
                toast("${data?.pesan}").duration = Toast.LENGTH_LONG
                println("Upload ${data?.pesan}")
                progressBarToko.visibility = GONE
                finish()

            }


        })
    }


}
