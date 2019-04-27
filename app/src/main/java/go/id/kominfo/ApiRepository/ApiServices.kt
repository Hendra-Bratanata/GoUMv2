package go.id.kominfo.ApiRepository

import go.id.kominfo.POJO.DataRespon
import okhttp3.MultipartBody
import retrofit2.Call

import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServices {
    @Multipart
    @POST("addImage.php")
    fun addImages(
            @Part foto:MultipartBody.Part,
            @Part foto2:MultipartBody.Part,
            @Part foto3:MultipartBody.Part)

            :Call<DataRespon>


}