package go.id.dinkop.PRESENTER


import com.google.gson.Gson
import go.id.dinkop.ApiRepository.ApiReposirtory
import go.id.dinkop.ApiRepository.PromoAPI
import go.id.dinkop.INTERFACE.LihatSemuaView
import go.id.dinkop.POJO.ProdukResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LihatSemuaPresenter(val view: LihatSemuaView, val gson: Gson, val apiReposirtory: ApiReposirtory){

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
                view.showData(data.data)

            }

        }
    }
    fun getCraft (){
        doAsync {
            val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getCraft())
                    , ProdukResponse::class.java)
            uiThread {
                view.showData(data.data)

            }

        }
    }

    fun getKuliner (){
        doAsync {
            val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getKuliner())
                    , ProdukResponse::class.java)
            uiThread {
                view.showData(data.data)

            }

        }
    }
    fun getMinuman (){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getMinuman())
                    , ProdukResponse::class.java)
            uiThread {
                view.showData(data.data)

            }
        }

    }

    fun getRumahTangga (){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getRumahTangga())
                    , ProdukResponse::class.java)
            uiThread {
                view.showData(data.data)

            }
        }

    }
    fun getJasa (){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getJasa())
                    , ProdukResponse::class.java)
            uiThread {
                view.showData(data.data)

            }
        }

    }


    fun getAllByABC(cari: String){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getAllProduk(cari)),
                    ProdukResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
        }
    }

    fun getAllFashion(){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getAllFashio()),
                    ProdukResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
        }
    }
    fun getAllCraft(){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getAllCraft()),
                    ProdukResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
        }
    }
    fun getAllKuliner(){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getAllKuliner()),
                    ProdukResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
        }
    }
    fun getAllMakanan(){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getAllMakanan()),
                    ProdukResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
        }
    }
    fun getAllRumahTangga(){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getAllRumahTangga()),
                    ProdukResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
        }
    }
    fun getAllJasa(){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getAllJasa()),
                    ProdukResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
        }
    }
    fun getAllPromo(){
        doAsync {
            val data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.getAllPromo()),
                    ProdukResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
        }
    }
}
