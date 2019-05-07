package go.id.kominfo.PRESENTER

import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ApiRepository.PromoAPI
import go.id.kominfo.INTERFACE.UmkmView
import go.id.kominfo.POJO.UmkmResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class UmkmPresenter(val apiReposirtory: ApiReposirtory,val gson: Gson,val umkmView: UmkmView){
    fun getUmkmDataHp(hp:String){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getUmkm(hp)),UmkmResponse::class.java)

            uiThread {
                umkmView.showDataUmkm(data.data)
            }
        }
    }
    fun getUmkmDataKdUmkm(kdUmkm:String){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getUmkmKd(kdUmkm)),UmkmResponse::class.java)

            uiThread {
                umkmView.showDataUmkm(data.data)
            }
        }
    }
}