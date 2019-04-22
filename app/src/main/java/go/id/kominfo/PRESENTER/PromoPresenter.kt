package go.id.kominfo.PRESENTER


import com.google.gson.Gson
import go.id.kominfo.ADAPTER.BannerAdapter
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ApiRepository.PromoAPI
import go.id.kominfo.INTERFACE.MainView
import go.id.kominfo.POJO.PromoResponse
import go.id.kominfo.POJO.bannerResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PromoPresenter(val view: MainView, val gson: Gson, val apiReposirtory: ApiReposirtory){

    fun getPromo(){
       doAsync {
           val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getPromo())
                   , PromoResponse::class.java)
           uiThread {
               view.showData(data.data)

           }

       }
    }
    fun getFashionPria (){
        doAsync {
            val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getfashionPria())
                    , PromoResponse::class.java)
            uiThread {
                view.showDataPria(data.data)

            }

        }
    }
    fun getFashionWanita (){
        doAsync {
            val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getfashionWanita())
                    , PromoResponse::class.java)
            uiThread {
                view.showDataWanita(data.data)

            }

        }
    }

    fun getMinuman (){
        doAsync {
            val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getMinuman())
                    , PromoResponse::class.java)
            uiThread {
                view.showDataMinuman(data.data)

            }

        }
    }
    fun kirimToken (token :String,noHp :String){
        doAsync {
           apiReposirtory.doRequest(PromoAPI.kirimToken(token,noHp))

        }

    }
    fun getBenner (){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getBenner())
                    , bannerResponse::class.java)
            uiThread {
                view.showDataBanner(data.data)

            }
        }

    }
}
