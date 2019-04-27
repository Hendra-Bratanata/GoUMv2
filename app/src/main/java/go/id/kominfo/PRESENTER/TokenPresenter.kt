package go.id.kominfo.PRESENTER


import android.graphics.Bitmap
import android.util.Base64
import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ApiRepository.PromoAPI
import go.id.kominfo.INTERFACE.MainView
import go.id.kominfo.POJO.PromoResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.ByteArrayOutputStream
import java.io.File

class TokenPresenter( val apiReposirtory: ApiReposirtory){

    fun kirimToken (token :String,noHp:String) {
        doAsync {
            apiReposirtory.doRequest(PromoAPI.kirimToken(token, noHp))

        }
    }
        fun kirimfoto(bitmap: Bitmap) {
            doAsync {
                var output = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
                val imgByte = output.toByteArray()
                val encodeString = Base64.encodeToString(imgByte, Base64.DEFAULT)
                apiReposirtory.doRequest(PromoAPI.kirimFoto(encodeString))

            }

        }
    }

