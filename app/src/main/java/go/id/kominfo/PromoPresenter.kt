package go.id.kominfo

import android.util.Log
import android.view.View
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PromoPresenter(val view: MainView,val gson: Gson,val apiReposirtory: ApiReposirtory){

    fun getPromo(){
       doAsync {
           val data =gson.fromJson(apiReposirtory.doRequest(PromoAPI.getPromo())
                   ,PromoResponse::class.java)
           uiThread {
               view.showData(data.data)

           }

       }
    }
}