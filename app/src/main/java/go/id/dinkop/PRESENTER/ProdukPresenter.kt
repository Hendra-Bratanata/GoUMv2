package go.id.dinkop.PRESENTER


import com.google.gson.Gson
import go.id.dinkop.ApiRepository.ApiReposirtory
import go.id.dinkop.ApiRepository.PromoAPI
import go.id.dinkop.INTERFACE.ProdukView
import go.id.dinkop.POJO.ProdukResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ProdukPresenter(val view: ProdukView, val gson: Gson, val apiReposirtory: ApiReposirtory){

    fun getProduk(){
       doAsync {
           val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getProduk())
                   , ProdukResponse::class.java)
           uiThread {
               view.showDataProduk(data.data)

           }

       }
    }
    fun getProdukByKdUmkm(id:String){
        doAsync {
            val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getProdukbyKode(id))
                    , ProdukResponse::class.java)
            uiThread {
                view.showDataProduk(data.data)

            }

        }
    }

}
