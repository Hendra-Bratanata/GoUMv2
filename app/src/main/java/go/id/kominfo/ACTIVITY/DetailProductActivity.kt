package go.id.kominfo.ACTIVITY


import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import com.squareup.picasso.Picasso
import go.id.kominfo.POJO.Produk
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_detail_product.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class DetailProductActivity : AppCompatActivity() {

    internal lateinit var window: Window
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        if (Build.VERSION.SDK_INT >= 21) { //ganti warna status bar diatas OS lolipop
            window = this.getWindow()
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorDarkPurple)
        }
        val produk: Produk = intent.extras.getSerializable("detail") as Produk
        Picasso.get().load(produk.gambar).into(imageView)
        tv_nama_produk.text = produk.nm_produk
        tv_deskripsi.text = produk.deskripsi
        var hargaPakeTti = tambahTitik(produk.harga!!)

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
      

    }

    fun tambahTitik( data:String):String{
        val locale : Locale = Locale("id","ID")
        val numberFormat : NumberFormat = NumberFormat.getCurrencyInstance(locale)





        return numberFormat.format(data.toInt())

    }


}

