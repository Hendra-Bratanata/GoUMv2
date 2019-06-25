package go.id.kominfo.ACTIVITY.DetailActivity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import go.id.kominfo.ACTIVITY.TokoActivity
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ApiRepository.RetrofitClient
import go.id.kominfo.INTERFACE.KatagoriView
import go.id.kominfo.ITEM.SharedPreference
import go.id.kominfo.POJO.DataRespon
import go.id.kominfo.POJO.Katagori
import go.id.kominfo.POJO.Produk
import go.id.kominfo.PRESENTER.KatagoriPresenter
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_new_product.*
import kotlinx.android.synthetic.main.edit_produk.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditProdukActivity : AppCompatActivity(),KatagoriView {
    override fun addKatagori(listKatagory: List<Katagori>) {

        for (i in listKatagory.indices) {
            listKatagoryId.add(listKatagory[i].kodeKatagori.toString())
            listItem.add(listKatagory[i].namaKatagori.toString())
        }
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, listItem)
        spinnerTambahProdukedit.adapter = spinnerAdapter
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
    var kdProduk =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_produk)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)

        val produk = intent.extras.getSerializable("produk") as Produk

        pref = SharedPreference(this)
        listKatagory = mutableListOf()
        listKatagoryId = mutableListOf()
        gson = Gson()
        apiReposirtory = ApiReposirtory()

        edt_edit_harga_produk.setText(produk.harga.toString())
        edt_edit_nama_produk.setText(produk.nm_produk.toString())
        edt_promoE.setText(produk.diskon)
        edt_bulan_akhirE.setText(produk.masa_diskon?.substring(4,6))
        edt_tahun_akhirE.setText(produk.masa_diskon?.substring(0,3))
        edt_tangga_akhirE.setText(produk.masa_diskon?.substring(7))
        edt_edit_deskripsi_produk.setText(produk.deskripsi)
        kdProduk = produk.kd_produk.toString()

        Glide.with(this).load(produk.gambar).into(img_add_produkE)



//        spinner = spinner()
        listItem = mutableListOf()



        presenter = KatagoriPresenter(this, gson, apiReposirtory)

        presenter.getKatagori()

        checkbox_promoEdit.setOnClickListener {
            if(checkbox_promoEdit.isChecked){
                edt_promoE.visibility= View.VISIBLE
                edt_tangga_akhirE.visibility= View.VISIBLE
                edt_bulan_akhirE.visibility= View.VISIBLE
                edt_tahun_akhirE.visibility= View.VISIBLE
            }else{
                edt_promoE.visibility= View.GONE
                edt_tangga_akhirE.visibility= View.GONE
                edt_bulan_akhirE.visibility= View.GONE
                edt_tahun_akhirE.visibility= View.GONE
            }
        }

        img_add_produkE.setOnClickListener {
            getImage()
        }

        btn_TambahProdukBaruEdit.setOnClickListener {

            val kd_umkm = pref.getValueString("kd_umkm")
            val katagoriItem= listKatagoryId[spinnerTambahProdukedit.selectedItemPosition]
            val nm_produk = edt_edit_nama_produk.text.toString()
            val harga = edt_edit_harga_produk.text.toString()
            var diskon:String? = edt_promoE.text.toString()
            var tanggalAkhir:String? = edt_tangga_akhirE.text.toString()
            val bulanAkhir:String? = edt_bulan_akhirE.text.toString()
            val tahunAkhir:String?= edt_tahun_akhirE.text.toString()


            var exp_diskon = "20$tahunAkhir-$bulanAkhir-$tanggalAkhir"
            val deskripsi = edt_edit_deskripsi_produk.text.toString()

            if(nm_produk.isEmpty()){
                edt_edit_nama_produk.setError("Nama Produk Tidak Boleh Kosong")
            }
            if(harga.isEmpty()){
                edt_edit_harga_produk.setError("Harga Produk Tidak Boleh Kosong")

            }


            else{
                uploadImanges(kd_umkm,katagoriItem,nm_produk,harga,diskon,exp_diskon,deskripsi)
                progressBarE.visibility = View.VISIBLE
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
                img_add_produkE.setImageBitmap(bitmap)

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
        val kd_produk = RequestBody.create(MultipartBody.FORM,kdProduk)
        val ktgoriData = RequestBody.create(MultipartBody.FORM,katagoriItem)
        val namaProdukData = RequestBody.create(MultipartBody.FORM,nm_produk)
        val hargaData = RequestBody.create(MultipartBody.FORM,harga)
        var diskonData = RequestBody.create(MultipartBody.FORM,dataDiskon)
        var exp_diskonData = RequestBody.create(MultipartBody.FORM,dataExp)
        val deskripsiData = RequestBody.create(MultipartBody.FORM,deskripsi)
        val regBody = RequestBody.create(MediaType.parse("multipart/form-file"),file)
        var multipartBody = MultipartBody.Part.createFormData("gambar", file.name, regBody)





        val apiServices = RetrofitClient.getApiServices()

        call = apiServices.editProduk(multipartBody,kd_umkmData,kd_produk,ktgoriData,namaProdukData,hargaData,diskonData,exp_diskonData,deskripsiData)
        println("CALL ${call}")


        call.enqueue(object : Callback<DataRespon> {

            override fun onFailure(call: Call<DataRespon>, t: Throwable) {
                println("ERRROO ${t}")
                progressBarE.visibility = View.GONE

                toast("Gagal Mengupload data").show()


            }

            override fun onResponse(call: Call<DataRespon>, response: Response<DataRespon>) {
                println("Ada Di Onresponse")
                println("response $response")
                println("Call $call")
                val data: DataRespon? = response.body()
                toast("Produk Telah Ditambahkan").duration = Toast.LENGTH_LONG

                progressBarE.visibility = View.GONE
                startActivity<TokoActivity>()
                finish()

            }


        })
    }
}
