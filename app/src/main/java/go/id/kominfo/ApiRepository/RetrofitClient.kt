package go.id.kominfo.ApiRepository

import go.id.kominfo.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit:Retrofit? = null
    private var  Url : String = "http://35.196.161.29/goumkm/api/"

    fun getClient(): Retrofit?{
        if(retrofit == null){

            retrofit = Retrofit.Builder()
                    .baseUrl(Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        return retrofit
    }

    fun getApiServices():ApiServices{
        return getClient()!!.create(ApiServices::class.java)

    }
}