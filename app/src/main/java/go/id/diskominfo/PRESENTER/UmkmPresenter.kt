package go.id.diskominfo.PRESENTER

import com.google.gson.Gson
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.ApiRepository.PromoAPI
import go.id.diskominfo.INTERFACE.UmkmView
import go.id.diskominfo.POJO.UmkmResponse
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