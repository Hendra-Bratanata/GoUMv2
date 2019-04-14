package go.id.kominfo.ApiRepository

import android.util.Log
import go.id.kominfo.BuildConfig

object PromoAPI{
fun getPromo():String{
    val Url = BuildConfig.BASE_URL +"promo.php"
    Log.d("data",Url)
    return  Url
}
    fun getfashionPria ():String{
        val Url = BuildConfig.BASE_URL +"kategori/fashion_pria.php"
        Log.d("data",Url)
        return  Url
    }
    fun getfashionWanita ():String{
        val Url = BuildConfig.BASE_URL +"kategori/fashion_wanita.php"
        Log.d("data",Url)
        return  Url
    }
    fun getMinuman ():String{
        val Url = BuildConfig.BASE_URL +"kategori/minuman.php"
        Log.d("data",Url)
        return  Url
    }
}