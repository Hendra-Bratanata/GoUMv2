package go.id.kominfo

import android.util.Log

object PromoAPI{
fun getPromo():String{
    val Url = BuildConfig.BASE_URL+"promo.php?nama=hedra&namabelakang="
    Log.d("data",Url)
    return  Url
}
}