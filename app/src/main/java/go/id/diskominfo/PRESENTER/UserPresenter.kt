package go.id.diskominfo.PRESENTER

import com.google.gson.Gson
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.ApiRepository.PromoAPI
import go.id.diskominfo.INTERFACE.UserView
import go.id.diskominfo.POJO.UserResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class UserPresenter(val api:ApiReposirtory,val gson: Gson,val view:UserView){
    fun ambilDataUser(){
        doAsync {
            val data = gson.fromJson(api.doRequest(PromoAPI.getUser()),UserResponse::class.java)
            uiThread {
                view.showDataUser(data.data)
            }
        }
    }
}