package go.id.kominfo.ACTIVITY

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.Gson
import go.id.kominfo.ADAPTER.PesananAdapter
import go.id.kominfo.ADAPTER.tambahTitik
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.INTERFACE.DaftarView
import go.id.kominfo.ITEM.AmbilTanggal
import go.id.kominfo.ITEM.database
import go.id.kominfo.POJO.Daftar
import go.id.kominfo.POJO.Pesanan
import go.id.kominfo.POJO.Produk
import go.id.kominfo.PRESENTER.DaftarPresenter
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_keranjang.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class KeranjangActivity : AppCompatActivity(),DaftarView {
    override fun showData(list: List<Daftar>) {
println(list[0].pesan)

        if (list[0].pesan.equals("berhasil",true)){
            flushDatabase()
            startActivity<KeranjangActivity>()
            finish()
        }
    }

    lateinit var pesanan: List<Pesanan>
    lateinit var listPesanan: MutableList<Pesanan>
    var hargaTotal: Int = 0
    lateinit var adapterKanan: PesananAdapter
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var presenter: DaftarPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        gson = Gson()
        apiReposirtory = ApiReposirtory()
        presenter = DaftarPresenter(apiReposirtory,gson,this)


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

        btn_proses.setOnClickListener {
            val inten = Intent(this,PembeliDataActivity::class.java)
            startActivityForResult(inten,3000)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 3000){
                val tanggal = AmbilTanggal()
                val nama = data?.getStringExtra("nama")
                val tlp = data?.getStringExtra("tlp")
                val alamat = data?.getStringExtra("alamat")
                val total = tvHargaTotalKeranjang.text.toString()
                for (i in listPesanan.indices){
                    val noTransaksi =tanggal.ambilTanggalLengkap()
                    val kdProduk = listPesanan[i].id
                    val tanggalTransaksi = tanggal.ambilTanggal()
                    val qty = listPesanan[i].jumlah
                    val harga = listPesanan[i].harga
                    presenter.kirimDataPembelian(noTransaksi,kdProduk.toString(),tanggalTransaksi,nama.toString(),tlp.toString(),alamat.toString(),qty.toString(),harga.toString(),total)
                }

                println(nama)
                println(tlp)
                println(alamat)
            }
        }

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

    private fun flushDatabase() {
        try {
            database.use {
                val resault = select(Pesanan.Table_Pesanan)
                pesanan = resault.parseList(classParser())
                if (pesanan.size > 0) {
                    listPesanan.clear()
                    for (i in pesanan.indices) {
                        delete(Pesanan.Table_Pesanan, "Id_Pesanan ={id}", "id" to pesanan[i].idPesana)
                    }
                }
            }
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }
    }
}
