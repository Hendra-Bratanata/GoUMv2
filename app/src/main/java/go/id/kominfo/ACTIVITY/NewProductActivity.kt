package go.id.kominfo.ACTIVITY

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ApiRepository.RetrofitClient
import go.id.kominfo.INTERFACE.KatagoriView
import go.id.kominfo.ITEM.SharedPreference
import go.id.kominfo.POJO.DataRespon
import go.id.kominfo.POJO.Katagori
import go.id.kominfo.PRESENTER.KatagoriPresenter

import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_new_product.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class NewProductActivity : AppCompatActivity(),KatagoriView {
    override fun addKatagori(listKatagory: List<Katagori>) {

        for (i in listKatagory.indices) {
            listKatagoryId.add(listKatagory[i].kodeKatagori.toString())
            listItem.add(listKatagory[i].namaKatagori.toString())
        }
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, listItem)
        spinnerTambahProduk.adapter = spinnerAdapter
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)
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
            getImage()
        }

        btn_TambahProdukBaru.setOnClickListener {

            val kd_umkm = pref.getValueString("kd_umkm")
            val katagoriItem= listKatagoryId[spinnerTambahProduk.selectedItemPosition]
            val nm_produk = edt_nama_produk.text.toString()
            val harga = edt_harga_produk.text.toString()
            val diskon:String? = edt_promo.text.toString()
            val tanggalAkhir:String? = edt_tangga_akhir.text.toString()
            val bulanAkhir:String? = edt_bulan_akhir.text.toString()
            val tahunAkhir:String?= edt_tahun_akhir.text.toString()

            val exp_diskon = "20$tahunAkhir-$bulanAkhir-$tanggalAkhir"
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {
                getData(data)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
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
            file = File(partImg)
            options = BitmapFactory.Options()
            options.inSampleSize = 16
        }

    }
    fun uploadImanges(kd_umkm: String?,
                      katagoriItem: String,
                      nm_produk: String,
                      harga: String,
                      diskon: String?,
                      exp_diskon: String,
                      deskripsi: String) {

//        val regBody = RequestBody.create(MediaType.parse("multipart/form-file"), file)

        val kd_umkm = RequestBody.create(MultipartBody.FORM,kd_umkm)
        val ktgori = RequestBody.create(MultipartBody.FORM,katagoriItem)
        val namaProduk = RequestBody.create(MultipartBody.FORM,nm_produk)
        val harga = RequestBody.create(MultipartBody.FORM,harga)
        val diskon = RequestBody.create(MultipartBody.FORM,diskon)
        val exp_diskon = RequestBody.create(MultipartBody.FORM,exp_diskon)
        val deskripsi = RequestBody.create(MultipartBody.FORM,deskripsi)
        val regBody = RequestBody.create(MediaType.parse("multipart/form-file"),file)
        var multipartBody = MultipartBody.Part.createFormData("gambar", file.name, regBody)



        val apiServices = RetrofitClient.getApiServices()

        call = apiServices.addProduk(multipartBody,kd_umkm,ktgori,namaProduk,harga,diskon,exp_diskon,deskripsi)
        println("CALL ${call}")


        call.enqueue(object : Callback<DataRespon> {

            override fun onFailure(call: Call<DataRespon>, t: Throwable) {
                println("ERRROO ${t}")
                progresBarProduk.visibility = View.GONE

                toast("Gagal Mengupload data").show()


            }

            override fun onResponse(call: Call<DataRespon>, response: Response<DataRespon>) {
                println("Ada Di Onresponse")
                println("response $response")
                println("Call $call")
                val data: DataRespon? = response.body()
                toast("Produk Telah Ditambahkan").duration = Toast.LENGTH_LONG

                progresBarProduk.visibility = View.GONE
                startActivity<TokoActivity>()
                finish()

            }


        })
    }
}

