package go.id.diskominfo.PRESENTER

import com.google.gson.Gson
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.ApiRepository.PromoAPI
import go.id.diskominfo.INTERFACE.KatagoriView
import go.id.diskominfo.POJO.KatagoriResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class KatagoriPresenter(val view: KatagoriView,val gson: Gson, val apiReposirtory: ApiReposirtory){
    fun getKatagori(){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getKatagori()),KatagoriResponse::class.java)
            uiThread {
                view.addKatagori(data.data)
            }
        }
    }
    fun tambahProduk(){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getKatagori()),KatagoriResponse::class.java)
            uiThread {
                view.addKatagori(data.data)
            }
        }
    }
}