package go.id.kominfo.ACTIVITY


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
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.INTERFACE.UmkmView
import go.id.kominfo.ITEM.database
import go.id.kominfo.POJO.Pesanan
import go.id.kominfo.POJO.Produk
import go.id.kominfo.POJO.Umkm
import go.id.kominfo.PRESENTER.UmkmPresenter
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_detail_product.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.NumberFormat
import java.util.*

class DetailProductActivity : AppCompatActivity(),UmkmView {
    override fun showDataUmkm(listUmkm: List<Umkm>) {
       if(listUmkm.size > 0){
           umkm = listUmkm[0]
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
        if (Build.VERSION.SDK_INT >= 21) { //ganti warna status bar diatas OS lolipop
            window = this.getWindow()
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorDarkPurple)
        }
        val produk: Produk = intent.extras.getSerializable("detail") as Produk
        val detaiCode = intent.getIntExtra("detailCode",0)

        if (detaiCode == 12){
            lyDetailNamaToko.visibility = View.GONE

        }
        else{
            lyDetailNamaToko.visibility = View.VISIBLE
        }
        Picasso.get().load(produk.gambar).into(imageView)
        tv_nama_produk.text = produk.nm_produk
        tv_deskripsi.text = produk.deskripsi
        var hargaPakeTti = tambahTitik(produk.harga!!)

        listUmkm = mutableListOf()
        pesanan = mutableListOf()
        listPesanan = mutableListOf()

        gson = Gson()
        apiReposirtory = ApiReposirtory()
        presenter = UmkmPresenter(apiReposirtory,gson,this)
        presenter.getUmkmDataKdUmkm(produk.kd_umkm.toString())






        tv_harga.text = "$hargaPakeTti"
        val diskonMember = 15
        val hargaNormal = produk.harga!!.toInt()
        var hargaMember = hargaNormal - (( hargaNormal* diskonMember)/100)
        val mm = tambahTitik(hargaMember.toString())
        tv_member_price.text = "$mm - Harga Member"
        var jumlah = 0
        btnAdd.setOnClickListener {

                jumlah++
                tv_quantity.text = jumlah.toString()

            if(jumlah > 0 ){
                btn_add_to_cart.visibility= View.VISIBLE

            }

        }
        btnMin.setOnClickListener {
            if (jumlah > 0){
                jumlah--
                tv_quantity.text = jumlah.toString()
            }
            if(jumlah == 0){
                btn_add_to_cart.visibility= View.INVISIBLE
            }

        }

        btn_add_to_cart.setOnClickListener {

            val qty = tv_quantity.text.toString()
            val qtyy = qty.toInt()

            tambahPesanan(produk,qtyy)





        }



        btn_detailToko.setOnClickListener {
            startActivity<ProfilTokoActivity>("data" to produk)
        }
      

    }

    fun tambahTitik( data:String):String{
        val locale : Locale = Locale("id","ID")
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

