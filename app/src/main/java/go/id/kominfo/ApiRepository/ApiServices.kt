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
            @Part foto: MultipartBody.Part,
            @Part("kd_umkm") kdumkm: RequestBody)

            : Call<DataRespon>

    @Multipart
    @POST("register_part2.php")
    fun addProduk(
            @Part foto: MultipartBody.Part,
            @Part("kd_umkm") kdumkm: RequestBody,
            @Part("kd_kategori") kd_kategori: RequestBody,
            @Part("nm_produk") nm_produk: RequestBody,
            @Part("harga") harga: RequestBody,
            @Part("diskon") diskon: RequestBody,
            @Part("exp_diskon") exp_diskon: RequestBody,
            @Part("deskripsi") deskripsi: RequestBody)


            : Call<DataRespon>


}