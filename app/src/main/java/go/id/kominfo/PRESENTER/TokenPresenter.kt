package go.id.kominfo.PRESENTER


import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ApiRepository.PromoAPI
import go.id.kominfo.INTERFACE.MainView
import go.id.kominfo.POJO.PromoResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TokenPresenter( val apiReposirtory: ApiReposirtory){

    fun kirimToken (token :String,noHp:String){
        doAsync {
           apiReposirtory.doRequest(PromoAPI.kirimToken(token,noHp))

        }

    }
}
