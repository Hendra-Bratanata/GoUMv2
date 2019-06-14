package go.id.kominfo.PRESENTER

import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ApiRepository.PromoAPI
import go.id.kominfo.INTERFACE.KatagoriView
import go.id.kominfo.INTERFACE.PesananView
import go.id.kominfo.POJO.KatagoriResponse
import go.id.kominfo.POJO.PenjualanResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PenjualanPresenter(val view: PesananView, val gson: Gson, val apiReposirtory: ApiReposirtory){
    fun getPenjualan(kdUmkm:String){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getDataPenjualan(kdUmkm)),
                    PenjualanResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
        }
    }
    fun getPenjualanFilter(kdUmkm:String,status:String){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getDataPenjualanFilter(kdUmkm,status)),
                    PenjualanResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
        }
    }
}