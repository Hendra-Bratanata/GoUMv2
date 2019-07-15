package go.id.diskominfo.ITEM

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

class BukaWhatsApp {

    fun BukaWhatsApp(context: Context, hp: String) {
        try {
            val kodeAndNumber = "+62" + hp.substring(1)


            val sendIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$kodeAndNumber?body="))
            sendIntent.setPackage("com.whatsapp")
            context.startActivity(sendIntent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "it may be you dont have whats app", Toast.LENGTH_LONG).show()

        }

    }

    fun kirimPesanWa(context: Context,hp: String,pesan:String){
        try {
            val kodeAndNumber = "+62" + hp.substring(1)


            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+kodeAndNumber +"&text="+pesan))

            context.startActivity(sendIntent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "it may be you dont have whats app", Toast.LENGTH_LONG).show()

        }
    }
}


