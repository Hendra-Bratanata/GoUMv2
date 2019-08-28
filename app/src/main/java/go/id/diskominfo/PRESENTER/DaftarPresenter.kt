package go.id.diskominfo.PRESENTER



import com.google.gson.Gson
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.ApiRepository.PromoAPI
import go.id.diskominfo.INTERFACE.DaftarView
import go.id.diskominfo.POJO.DaftarResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class DaftarPresenter(val apiReposirtory:ApiReposirtory, val gson: Gson, val view: DaftarView){

lateinit var data:DaftarResponse
    fun kirimToken (ktp: String,
                    npwp: String,
                    nama: String,
                    email: String,
                    hp: String,
                    pass: String,
                    nmToko: String,
                    almToko: String){

        doAsync {
             data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.kirimRegister(ktp,
                    npwp,
                    nama,
                    email,
                    hp,
                    pass,
                    nmToko,
                    almToko)),DaftarResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
        }
    }

    fun kirimDataPembelian (no_trans: String,
                            kd_produk: String,
                            tgl_trans: String,
                            nm_pembeli: String,
                            no_hp_pembeli: String,
                            alamat: String,
                            qty: String,
                            harga: String,
                            total: Int){

        doAsync {
            data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.kirimPembelian(no_trans,
                    kd_produk,
                    tgl_trans,
                    nm_pembeli,
                    no_hp_pembeli,
                    alamat,
                    qty,
                    harga,
                    total)),DaftarResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
        }
    }
    }

