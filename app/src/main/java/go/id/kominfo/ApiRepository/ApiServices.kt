package go.id.kominfo.ApiRepository

import go.id.kominfo.POJO.DataRespon
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServices {
    @Multipart
    @POST("register_part2.php")
    fun addImages1(
            @Part foto:MultipartBody.Part,
            @Part("kd_umkm") kdumkm:RequestBody)

            :Call<DataRespon>
    @Multipart
    @POST("register_part2.php")
    fun addImages2(
            @Part foto:MultipartBody.Part,
            @Part foto2:MultipartBody.Part,
            @Part("kd_umkm") kdumkm:RequestBody)

            :Call<DataRespon>

    @Multipart
    @POST("register_part2.php")
    fun addImages3(
            @Part foto:MultipartBody.Part,
            @Part foto2:MultipartBody.Part,
            @Part foto3:MultipartBody.Part,
            @Part("kd_umkm") kdumkm:RequestBody)

            :Call<DataRespon>

    @Multipart
    @POST("register_part2.php")
    fun addImages4(
            @Part foto:MultipartBody.Part,
            @Part foto2:MultipartBody.Part,
            @Part foto3:MultipartBody.Part,
            @Part foto4:MultipartBody.Part,
            @Part("kd_umkm") kdumkm:RequestBody)

            :Call<DataRespon>

    @Multipart
    @POST("register_part2.php")
    fun addImages5(
            @Part foto:MultipartBody.Part,
            @Part foto2:MultipartBody.Part,
            @Part foto3:MultipartBody.Part,
            @Part foto4:MultipartBody.Part,
            @Part foto5:MultipartBody.Part,
            @Part("kd_umkm") kdumkm:RequestBody)

            :Call<DataRespon>

}