package go.id.dinkop.ACTIVITY.DetailActivity


import android.database.sqlite.SQLiteConstraintException
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import go.id.dinkop.ACTIVITY.DetailToko
import go.id.dinkop.ApiRepository.ApiReposirtory
import go.id.dinkop.ApiRepository.PromoAPI
import go.id.dinkop.INTERFACE.UmkmView
import go.id.dinkop.ITEM.BukaWhatsApp
import go.id.dinkop.ITEM.SharedPreference
import go.id.dinkop.ITEM.database
import go.id.dinkop.POJO.Pesanan
import go.id.dinkop.POJO.Produk
import go.id.dinkop.POJO.Umkm
import go.id.dinkop.PRESENTER.UmkmPresenter
import go.id.dinkop.R
import kotlinx.android.synthetic.main.activity_detail_product.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.*
import java.text.NumberFormat
import java.util.*

class DetailProductActivity : AppCompatActivity(),UmkmView {
    override fun showDataUmkm(listUmkm: List<Umkm>) {
       if(listUmkm.size > 0){

           umkm = listUmkm[0]
           if(!umkm.fotoUsaha.equals("http://usahamikro.tangerangkab.go.id/media/source/",true)){
               Picasso.get().load(umkm.fotoUsaha).into(img_toko)
           }
           tv_NamaTokoDetail.setText(umkm.namaToko)
           tvAlamatTokoDetail.setText(umkm.alamatToko)
       }


    }

    lateinit var presenter: UmkmPresenter
    lateinit var listUmkm:MutableList<Umkm>
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var umkm: Umkm
    lateinit var pesanan: List<Pesanan>
    lateinit var listPesanan: MutableList<Pesanan>





    internal lateinit var window: Window
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        val pref = SharedPreference(this)


        if (Build.VERSION.SDK_INT >= 21) { //ganti warna status bar diatas OS lolipop
            window = this.getWindow()
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorDarkPurple)
        }
        //menerima inten produk startActivity inten adapter
        val produk: Produk = intent.extras.getSerializable("detail") as Produk
        println("Produk : ${produk.kd_kategori}")

        // jika kode umkm sama dengan kode umkm produk maka produk hanya dapat di edit tidak dapat dibeli
        // nama dan detail toko dihilangkan
        if (pref.getValueString("kd_umkm").equals(produk.kd_umkm)){
            lyDetailNamaToko.visibility = View.GONE
            btnAdd.visibility = View.GONE
            btnMin.visibility = View.GONE
            tv_quantity.visibility = View.GONE
            btn_editProduk.visibility = View.VISIBLE
            btn_DeleteProduk.visibility = View.VISIBLE

        }
        else{
            lyDetailNamaToko.visibility = View.VISIBLE
        }
        //memuat semua data pada produk

    // Original Code
         Picasso.get().load(produk.gambar).into(imageView)

        tv_nama_produk.text = produk.nm_produk
        tv_deskripsi.text = produk.deskripsi

        //merubah haraga dalam bentuk rupiah dengan fungsi tambah titik
        var hargaPakeTti = tambahTitik(produk.harga!!)

        listUmkm = mutableListOf()
        pesanan = mutableListOf()
        listPesanan = mutableListOf()

        gson = Gson()
        apiReposirtory = ApiReposirtory()
        presenter = UmkmPresenter(apiReposirtory,gson,this)

        //request data umkm berdasarkan pada kd umkm produk
        presenter.getUmkmDataKdUmkm(produk.kd_umkm.toString())






        tv_harga.text = "$hargaPakeTti"

        //tampilan diskon member
        val diskonMember = 15
        val hargaNormal = produk.harga!!.toInt()
        var hargaMember = hargaNormal - (( hargaNormal* diskonMember)/100)
        val mm = tambahTitik(hargaMember.toString())
        tv_member_price.text = "$mm - Harga Member"
        var jumlah = 0

        //menbah barang pada asaat diklik
        btnAdd.setOnClickListener {

                jumlah++
                tv_quantity.text = jumlah.toString()

            if(jumlah > 0 ){
                btn_add_to_cart.visibility= View.VISIBLE

            }

        }
        //megurangi barang pada saat diklik
        btnMin.setOnClickListener {
            if (jumlah > 0){
                jumlah--
                tv_quantity.text = jumlah.toString()
            }
            if(jumlah == 0){
                btn_add_to_cart.visibility= View.INVISIBLE
            }

        }
        //memasukan pesanan pada keranjang
        btn_add_to_cart.setOnClickListener {

            val qty = tv_quantity.text.toString()
            val qtyy = qty.toInt()

            tambahPesanan(produk,qtyy)





        }

        //tombol untuk merubah produk
        btn_editProduk.setOnClickListener {
            startActivity<EditProdukActivity>("produk" to produk)
            finish()
        }

        //tombol untuk menghapus produk
        btn_DeleteProduk.setOnClickListener {
            alert ("Anda yakin Ingin Menghapus Poduk ini"){
                yesButton { doAsync {
                    apiReposirtory.doRequest(PromoAPI.DeleteProduk(produk.kd_umkm.toString(),produk.kd_produk.toString()))
                finish()
                }

                }
                noButton {  }

            }.show()


        }



        btn_detailToko.setOnClickListener {
            startActivity<DetailToko>("data" to produk)
        }
        btn_chat.setOnClickListener {
            val wa = BukaWhatsApp()
            val pesan = "Saya tertarik dengan iklan anda ${produk.nm_produk} di App GOUM, bisa minta info lebih lanjut?"
            wa.kirimPesanWa(this,umkm.hp,pesan)
        }

      

    }

    fun tambahTitik( data:String):String{
        val locale  = Locale("id","ID")
        val numberFormat : NumberFormat = NumberFormat.getCurrencyInstance(locale)





        return numberFormat.format(data.toInt())

    }

    private fun tambahPesanan(produk: Produk, qty:Int) {
        try {
            database?.use {
                var ada = false
                var jumlah = 0

                val cek = select(Pesanan.Table_Pesanan)
                val hasil = cek.parseList(classParser<Pesanan>())

                for (i in hasil.indices) {
                    val kd = hasil[i].id

                    if (kd == produk.kd_produk) {
                        ada = true
                        jumlah = hasil[i].jumlah!!
                    }
                }


                if (ada) {
                    toast("pesanan sudah ada")
                    update(
                            Pesanan.Table_Pesanan, Pesanan.jumlah to jumlah.plus(qty)
                    )
                            .whereArgs("${Pesanan.Id} = ${produk.kd_produk}")
                            .exec()
                }
                if (!ada) {
                    insert(

                            Pesanan.Table_Pesanan,
                            Pesanan.Id to produk.kd_produk,
                            Pesanan.kdUmkm to produk.kd_umkm,
                            Pesanan.Nama to produk.nm_produk,
                            Pesanan.Harga to produk.harga,
                            Pesanan.Gambar to produk.gambar,
                            Pesanan.jumlah to jumlah.plus(qty)
                    )
                    toast("Pesanan Sudah ditambahkan")

                }


            }


        } catch (e: SQLiteConstraintException) {
            e.localizedMessage
            Log.d("Tag", "" + e.message)


        }


    }

//
}

