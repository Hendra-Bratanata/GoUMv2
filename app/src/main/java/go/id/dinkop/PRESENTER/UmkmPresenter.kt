package go.id.dinkop.PRESENTER

import com.google.gson.Gson
import go.id.dinkop.ApiRepository.ApiReposirtory
import go.id.dinkop.ApiRepository.PromoAPI
import go.id.dinkop.INTERFACE.UmkmView
import go.id.dinkop.POJO.UmkmResponse
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