package go.id.kominfo.ACTIVITY

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import go.id.kominfo.ADAPTER.PesananAdapter
import go.id.kominfo.ADAPTER.tambahTitik
import go.id.kominfo.ITEM.database
import go.id.kominfo.POJO.Pesanan
import go.id.kominfo.POJO.Produk
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_keranjang.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


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
            hapusPesananJumlah(it, 1)
            startActivity<KeranjangActivity>()
            finish()



        }, {
            tambahPesanan(it)
            startActivity<KeranjangActivity>()
            finish()
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

                tvHargaTotalKeranjang.text = tambahTitik(hargaTotal.toString())
            }
            adapterKanan.notifyDataSetChanged()


        }
    }

    private fun hapusPesananJumlah(it: Pesanan, i: Int) {
        try {
            database?.use {
                var jumlah = 0

                val cek = select(Pesanan.Table_Pesanan)
                val hasil = cek.parseList(classParser<Pesanan>())
                for (i in hasil.indices) {
                    val id = hasil[i].id

                    if (id == it.id) {
                        jumlah = hasil[i].jumlah!!
                    }
                }
                if(jumlah == 1) {
                    println("Dalam jumlah 1")
                    delete(Pesanan.Table_Pesanan, "Id_Pesanan ={id}", "id" to it.idPesana.toString())
                }
                update(
                        Pesanan.Table_Pesanan, Pesanan.jumlah to jumlah.minus(i)
                )
                        .whereArgs("${Pesanan.Id} = ${it.id}")
                        .exec()

                listPesanan.clear()
            }
        } catch (e: SQLiteConstraintException) {
            e.localizedMessage
        }
        adapterKanan.notifyDataSetChanged()
    }

    private fun tambahPesanan(it: Pesanan) {
        try {
            database?.use {
                var ada = false
                var jumlah = 0

                val cek = select(Pesanan.Table_Pesanan)
                val hasil = cek.parseList(classParser<Pesanan>())

                for (i in hasil.indices) {
                    val id = hasil[i].id

                    if (id == it.id) {
                        ada = true
                        jumlah = hasil[i].jumlah!!
                    }
                }


                if (ada) {
                    toast("pesanan sudah ada")
                    jumlah.plus(1)
                    update(
                            Pesanan.Table_Pesanan, Pesanan.jumlah to jumlah.plus(1)
                    )
                            .whereArgs("${Pesanan.Id} = ${it.id}")
                            .exec()
                }
                if (!ada) {
                    insert(

                            Pesanan.Table_Pesanan,
                            Pesanan.Id to it.id,
                            Pesanan.Nama to it.nama,
                            Pesanan.Harga to it.harga,
                            Pesanan.Gambar to it.gambar,
                            Pesanan.jumlah to jumlah.plus(1)
                    )
                    toast("Pesanan Sudah ditambahkan")

                }


            }


        } catch (e: SQLiteConstraintException) {
            e.localizedMessage
            Log.d("Tag", "" + e.message)


        }
    }


}
