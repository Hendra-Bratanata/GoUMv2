package go.id.diskominfo.PRESENTER


import com.google.gson.Gson
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.ApiRepository.PromoAPI
import go.id.diskominfo.INTERFACE.MainView
import go.id.diskominfo.POJO.ProdukResponse
import go.id.diskominfo.POJO.bannerResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PromoPresenter(val view: MainView, val gson: Gson, val apiReposirtory: ApiReposirtory){

    fun getPromo(){
       doAsync {
           val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getPromo())
                   , ProdukResponse::class.java)
           uiThread {
               view.showData(data.data)

           }

       }
    }
    fun getFashion (){
        doAsync {
            val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getFashion())
                    , ProdukResponse::class.java)
            uiThread {
                view.showDataPria(data.data)

            }

        }
    }
    fun getCraft (){
        doAsync {
            val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getCraft())
                    , ProdukResponse::class.java)
            uiThread {
                view.showDataWanita(data.data)

            }

        }
    }

    fun getKuliner (){
        doAsync {
            val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getKuliner())
                    , ProdukResponse::class.java)
            uiThread {
                view.showDataMinuman(data.data,"kuliner")

            }

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
    fun getMinuman (){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getMinuman())
                    , ProdukResponse::class.java)
            uiThread {
                view.showDataMinuman(data.data,"minuman")

            }
        }

    }
    fun getRumahTangga (){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getRumahTangga())
                    , ProdukResponse::class.java)
            uiThread {
                view.showDataRumah(data.data)

            }
        }

    }
    fun getJasa (){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getJasa())
                    , ProdukResponse::class.java)
            uiThread {
                view.showDataJasa(data.data)

            }
        }

    }
//    fun getLainLain (){
//        doAsync {
//            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getJasa())
//                    , bannerResponse::class.java)
//            uiThread {
//                view.showDataBanner(data.data)
//
//            }
//        }
//
//    }

}
