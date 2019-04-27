package go.id.kominfo.PRESENTER

import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ApiRepository.PromoAPI
import go.id.kominfo.INTERFACE.UserView
import go.id.kominfo.POJO.UserResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class UserPresenter(val api:ApiReposirtory,val gson: Gson,val view:UserView){
    fun ambilDataUser(){
        doAsync {
            val data = gson.fromJson(api.doRequest(PromoAPI.getUser()),UserResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
        }
    }
}