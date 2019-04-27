package go.id.kominfo.PRESENTER


import android.graphics.Bitmap
import android.util.Base64
import com.google.gson.Gson
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.ApiRepository.PromoAPI
import go.id.kominfo.INTERFACE.DaftarView
import go.id.kominfo.INTERFACE.UserView
import go.id.kominfo.POJO.DaftarResponse
import go.id.kominfo.POJO.UserResponse
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.ByteArrayOutputStream

class DaftarPresenter(val apiReposirtory:ApiReposirtory, val gson: Gson, val view: DaftarView){

lateinit var data:DaftarResponse
    fun kirimToken (ktp: String,
                    npwp: String,
                    nama: String,
                    email: String,
                    hp: String,
                    pass: String,
                    nmToko: String,
                    almToko: String){

        doAsync {
             data = gson.fromJson(apiReposirtory.doRequest(PromoAPI.kirimRegister(ktp,
                    npwp,
                    nama,
                    email,
                    hp,
                    pass,
                    nmToko,
                    almToko)),DaftarResponse::class.java)
            uiThread {
                view.showData(data.data)
            }
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

