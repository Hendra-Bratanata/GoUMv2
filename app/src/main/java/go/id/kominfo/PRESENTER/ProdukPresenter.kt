package go.id.kominfo.PRESENTER


import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ApiRepository.PromoAPI
import go.id.kominfo.INTERFACE.MainView
import go.id.kominfo.INTERFACE.ProdukView
import go.id.kominfo.POJO.PromoResponse
import go.id.kominfo.POJO.bannerResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ProdukPresenter(val view: ProdukView, val gson: Gson, val apiReposirtory: ApiReposirtory){

    fun getProduk(){
       doAsync {
           val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getProduk())
                   , PromoResponse::class.java)
           uiThread {
               view.showDataProduk(data.data)

           }

       }
    }

}
