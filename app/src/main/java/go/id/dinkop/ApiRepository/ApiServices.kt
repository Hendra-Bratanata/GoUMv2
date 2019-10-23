package go.id.dinkop.ApiRepository

import go.id.dinkop.POJO.DataRespon
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
    @POST("tambah_produk_user.php")
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

    @Multipart
    @POST("edit_produk_user.php")
    fun editProduk(
            @Part foto: MultipartBody.Part,
            @Part("kd_umkm") kdumkm: RequestBody,
            @Part("kd_produk") kdproduk: RequestBody,
            @Part("kd_kategori") kd_kategori: RequestBody,
            @Part("nm_produk") nm_produk: RequestBody,
            @Part("harga") harga: RequestBody,
            @Part("diskon") diskon: RequestBody,
            @Part("exp_diskon") exp_diskon: RequestBody,
            @Part("deskripsi") deskripsi: RequestBody)


            : Call<DataRespon>

    @Multipart
    @POST("edit_produk_user.php")
    fun editProdukNoGambar(
            @Part("kd_umkm") kdumkm: RequestBody,
            @Part("kd_produk") kdproduk: RequestBody,
            @Part("kd_kategori") kd_kategori: RequestBody,
            @Part("nm_produk") nm_produk: RequestBody,
            @Part("harga") harga: RequestBody,
            @Part("diskon") diskon: RequestBody,
            @Part("exp_diskon") exp_diskon: RequestBody,
            @Part("deskripsi") deskripsi: RequestBody)


            : Call<DataRespon>

    @Multipart
    @POST("tambah_produk_user.php")
    fun addProdukNoGambar(
            @Part("kd_umkm") kdumkm: RequestBody,
            @Part("kd_kategori") kd_kategori: RequestBody,
            @Part("nm_produk") nm_produk: RequestBody,
            @Part("harga") harga: RequestBody,
            @Part("diskon") diskon: RequestBody,
            @Part("exp_diskon") exp_diskon: RequestBody,
            @Part("deskripsi") deskripsi: RequestBody)


            : Call<DataRespon>


    @Multipart
    @POST("edit_user_umkm.php")
    fun editTokoFoto (
            @Part foto: MultipartBody.Part,
            @Part("kd_umkm") kd_umkm: RequestBody,
            @Part("nm_pemilik") nm_pemilik: RequestBody,
            @Part("alamat") alamat : RequestBody,
            @Part("nm_umkm") nm_umkm: RequestBody,
            @Part("kecamatan") kecamatan: RequestBody,
            @Part("kode_pos") kode_pos: RequestBody,
            @Part("keterangan") keterangan: RequestBody,
            @Part("catatan") catatan: RequestBody)


            : Call<DataRespon>

    @Multipart
    @POST("edit_user_umkm.php")
    fun editToko(
            @Part("kd_umkm") kd_umkm: RequestBody,
            @Part("nm_pemilik") nm_pemilik: RequestBody,
            @Part("alamat") alamat : RequestBody,
            @Part("nm_umkm") nm_umkm: RequestBody,
            @Part("kecamatan") kecamatan: RequestBody,
            @Part("kode_pos") kode_pos: RequestBody,
            @Part("keterangan") keterangan: RequestBody,
            @Part("catatan") catatan: RequestBody)


            : Call<DataRespon>

}