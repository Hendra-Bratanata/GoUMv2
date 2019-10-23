package go.id.dinkop.PRESENTER

import com.google.gson.Gson
import go.id.dinkop.ApiRepository.ApiReposirtory
import go.id.dinkop.ApiRepository.PromoAPI
import go.id.dinkop.INTERFACE.KatagoriView
import go.id.dinkop.POJO.KatagoriResponse
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