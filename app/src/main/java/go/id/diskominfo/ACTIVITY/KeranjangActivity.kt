package go.id.diskominfo.ACTIVITY

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import go.id.diskominfo.ADAPTER.PesananAdapter
import go.id.diskominfo.ADAPTER.tambahTitik
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.INTERFACE.DaftarView
import go.id.diskominfo.ITEM.AmbilTanggal
import go.id.diskominfo.ITEM.database
import go.id.diskominfo.POJO.Daftar
import go.id.diskominfo.POJO.Pesanan
import go.id.diskominfo.PRESENTER.DaftarPresenter
import go.id.diskominfo.R
import kotlinx.android.synthetic.main.activity_keranjang.*
import kotlinx.coroutines.delay
import org.jetbrains.anko.db.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.sql.Time
import java.util.*


class KeranjangActivity : AppCompatActivity(),DaftarView {
    override fun showData(list: List<Daftar>) {
         println(list[0].pesan)

        if (list[0].pesan.equals("Sukses",true)){
            flushDatabase()
            startActivity<PembelianActivity>()
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
        //ambil data dari local database
        ambilDataDatabase()

        //jika pesanan data lebih besar dari 0 maka tampilkan semua
        if(pesanan.size > 0){
            tvKeranjang.visibility =View.VISIBLE
            tvKeranjangKosong.visibility = View.GONE
            btn_proses.visibility = View.VISIBLE
            vKeranjang.visibility = View.VISIBLE
            tvTotalKeranjang.visibility = View.VISIBLE
            tvHargaTotalKeranjang.visibility = View.VISIBLE


        }else{
            tvKeranjang.visibility =View.GONE
            tvKeranjangKosong.visibility = View.VISIBLE
            btn_proses.visibility = View.GONE
            vKeranjang.visibility = View.GONE
            tvTotalKeranjang.visibility = View.GONE
            tvHargaTotalKeranjang.visibility = View.GONE

        }

        btn_proses.setOnClickListener {
            val inten = Intent(this,IsiDataPembeliActivity::class.java)
            startActivityForResult(inten,3000)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 3000){

                //inisialisai variable tanggal dengan object dari AmbilTanggal
                val tanggal = AmbilTanggal()
                //ambil data dari result yang dikirim dari dataPembeliActivity
                val nama = data?.getStringExtra("nama")
                val tlp = data?.getStringExtra("tlp")
                val alamat = data?.getStringExtra("alamat")


                //variable penampung data sementara
                var dataKd = ""
                var  dataKdTemp = ""
                var total = 0
                var jumlah = 0
                var harga = 0
                var totalTemp = 0
                var pertama = true
                // ambil waktu dan tanggal sebagai no transaksi
                var noTransaksi = tanggal.ambilTanggalLengkap()

                //cek jumlah list pesanan
                for (i in listPesanan.indices){
                    //ambil data kdUmkm dari listpesanan
                     dataKd = listPesanan[i].kdUmkm.toString()
                    //jika list baru pertama di periksa atau kdumkm list sama dengan kdumkm sebelumnya jadikan 1 list pemesanan
                    if(pertama || dataKd == dataKdTemp){

                        //ambil jumlah dan harga dan hitung total semuanya
                        jumlah = listPesanan[i].jumlah!!.toInt()
                        harga = listPesanan[i].harga!!.toInt()
                        total = jumlah * harga
                        totalTemp+=total
                        //ambil id peroduk dari list
                        val kdProduk = listPesanan[i].id
                        //ambil tanggal transaksi sesuia waktu gadget
                        val tanggalTransaksi = tanggal.ambilTanggal()
                        //ambil jumlah qty dari list
                        val qty = listPesanan[i].jumlah
                        //ambil harga dari list
                        val harga = listPesanan[i].harga

                        //kirim data data yang telah di ambil keserver
                        presenter.kirimDataPembelian(noTransaksi,kdProduk.toString(),tanggalTransaksi,nama.toString(),tlp.toString(),alamat.toString(),qty.toString(),harga.toString(),totalTemp)
                       //isi data dataKdTemp dengan dataKd umkm sekarang untuk perbandingan
                        dataKdTemp = dataKd
                        //rubah exekusi pertama menjadi false agar tidak terexekusi lagi sebagai yang pertama
                        pertama = false
                    }
                    //jika dataKdTemp tidak sama dengan dataKd dari list sekarang
                    if(dataKdTemp != dataKd){

                        //kosong kan total untuk di hitung ulang
                        totalTemp = 0
                        //ambil jumlah dan harga dari list sekarang kemudian hitung dan masukan ke total kemudian tambahkan totalTemp
                        // dengan total sekanrng
                        jumlah = listPesanan[i].jumlah!!.toInt()
                        harga = listPesanan[i].harga!!.toInt()
                        total = jumlah * harga
                        totalTemp+=total
                        //tunggu selama 1 detik untuk merubah noinvoice agar tidak sama
                        //setiap kdUmkm tidak sama denga kode yang sekarang maka akan digenerate dengan kode inv yang baru
                        Thread.sleep(1000)
                        val noTransaksi =tanggal.ambilTanggalLengkap()

                        // ambil semua data pada list sekarang dan kirim ke server
                        val kdProduk = listPesanan[i].id
                        val tanggalTransaksi = tanggal.ambilTanggal()
                        val qty = listPesanan[i].jumlah
                        val harga = listPesanan[i].harga
                        presenter.kirimDataPembelian(noTransaksi,kdProduk.toString(),tanggalTransaksi,nama.toString(),tlp.toString(),alamat.toString(),qty.toString(),harga.toString(),totalTemp)
                        // isi data kdUmkm sekarang pada dataKdTemp untuk perbandingan nantinya
                        dataKdTemp = dataKd
                    }//akhir dari if(dataKdTemp != dataKd)

                }// akhir dari for
            }// akhri dari if(request Code)
        }//akhir dari if(result code)

    }//akhir dari fungsi onActivityResault

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
                            Pesanan.kdUmkm to it.kdUmkm,
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
