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
    fun kirimToken (token:String,noHp:String):String{
        val Url = BuildConfig.BASE_URLI +"send-sms.php?token=$token&noHp=$noHp"
        Log.d("data",Url)
        return  Url
}
    fun getBenner ():String{
        val Url = BuildConfig.BASE_URL +"banner.php"
        Log.d("data",Url)
        return  Url
    }
}