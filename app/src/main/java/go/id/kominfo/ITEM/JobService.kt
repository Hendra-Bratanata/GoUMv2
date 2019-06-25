package go.id.kominfo.ITEM

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.INTERFACE.PesananView
import go.id.kominfo.POJO.Penjualan
import go.id.kominfo.PRESENTER.PenjualanPresenter

class JobService : JobService(),PesananView{
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var presenter: PenjualanPresenter
    lateinit var sharedPreferences: SharedPreference
    var KDUMKM =""
    var invSekarang = ""
    lateinit var notifikasi: Notifikasi

    override fun showData(list: List<Penjualan>) {

        print("show data \n")

        for (i in list.indices) {
            val penjualan = list[i]

            if (penjualan.notif.equals("new", true)) {
                if (invSekarang == "" || invSekarang != penjualan.no_trans) {
                    invSekarang = penjualan.no_trans.toString()
                    notifikasi = Notifikasi()
                    print("ada data baru \n")
                    notifikasi.notif(this, "Pesanan Baru", "Anda Mendapat pesanan baru dari ${penjualan.nm_pembeli}", penjualan.id_trans.toString(), i)
                    presenter.setNotifikasi(penjualan.kd_umkm.toString(), penjualan.no_trans.toString(), "past")
                }
            }
        }
    }

    override fun onStopJob(job: JobParameters?): Boolean {

        return true

    }

    override fun onStartJob(job: JobParameters?): Boolean {
        print("on start job \n")
        cekPesananBaru(job)
        return true
    }

    fun cekPesananBaru(job: JobParameters?){
        print("cek pesanan baru \n")
        sharedPreferences = SharedPreference(this)
        KDUMKM = sharedPreferences.getValueString("kd_umkm").toString()

        gson = Gson()
        apiReposirtory = ApiReposirtory()
        presenter = PenjualanPresenter(this,gson,apiReposirtory)
        presenter.getPenjualan(KDUMKM)
        jobFinished(job!!,true)
    }

}