package go.id.kominfo.ACTIVITY

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import go.id.kominfo.ADAPTER.PesananAdapter
import go.id.kominfo.ITEM.database
import go.id.kominfo.POJO.Pesanan
import go.id.kominfo.POJO.Produk
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_keranjang.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update


class KeranjangActivity : AppCompatActivity() {

    lateinit var pesanan: List<Pesanan>
    lateinit var listPesanan: MutableList<Pesanan>
    var hargaTotal: Int = 0
    lateinit var adapterKanan: PesananAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        listPesanan = mutableListOf()
        pesanan = mutableListOf()

        rv_keranjang.layoutManager = LinearLayoutManager(this)
        adapterKanan = PesananAdapter(listPesanan, {


        }, {


        })
        rv_keranjang.adapter = adapterKanan

        ambilDataDatabase()
    }

    private fun ambilDataDatabase() {
        val hargaSemetara = tvHargaTotalKeranjang.text.toString()
        hargaTotal = hargaSemetara.toInt()
        database?.use {
            val resault = select(Pesanan.Table_Pesanan)
            pesanan = resault.parseList(classParser())
            if (pesanan.size > 0) {
                listPesanan.clear()
                for (i in pesanan.indices) {
                    var jumlah = pesanan[i].jumlah
                    var hargaSekarang: Int? = pesanan[i].harga
                    var hitungTotal = jumlah!! * hargaSekarang!!
                    var HargaTotal = hargaTotal.plus(hitungTotal!!)
                    hargaTotal = HargaTotal

                }
                listPesanan.addAll(pesanan)

                tvHargaTotalKeranjang.text = hargaTotal.toString()
            }
            adapterKanan.notifyDataSetChanged()


        }
    }

    private fun hapusPesananJumlah(it: Produk, i: Int) {
        try {
            database?.use {
                var jumlah = 0

                val cek = select(Pesanan.Table_Pesanan)
                val hasil = cek.parseList(classParser<Pesanan>())
                for (i in hasil.indices) {
                    val id = hasil[i].id

                    if (id == it.kd_produk) {
                        jumlah = hasil[i].jumlah!!
                    }
                }
                update(
                        Pesanan.Table_Pesanan, Pesanan.jumlah to jumlah.minus(i)
                )
                        .whereArgs("${Pesanan.Id} = ${it.kd_produk}")
                        .exec()

                listPesanan.clear()
                ambilDataDatabase()
            }
        } catch (e: SQLiteConstraintException) {
            e.localizedMessage
        }
        adapterKanan.notifyDataSetChanged()
    }


}
