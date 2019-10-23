package go.id.dinkop.PRESENTER

import com.google.gson.Gson
import go.id.dinkop.ApiRepository.ApiReposirtory
import go.id.dinkop.ApiRepository.PromoAPI
import go.id.dinkop.INTERFACE.UserView
import go.id.dinkop.POJO.UserResponse
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