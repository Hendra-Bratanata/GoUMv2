package go.id.kominfo.PRESENTER

import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ApiRepository.PromoAPI
import go.id.kominfo.INTERFACE.KatagoriView
import go.id.kominfo.POJO.KatagoriResponse
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