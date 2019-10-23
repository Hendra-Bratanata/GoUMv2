package go.id.dinkop.ITEM

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import id.zelory.compressor.Compressor
import java.io.File

object HendraCompress {
fun compress(file: File,context: Context):File{
   val gambarCompress =  Compressor(context)
            .setMaxWidth(500)
            .setMaxHeight(500)
            .setQuality(75)
            .setCompressFormat(Bitmap.CompressFormat.WEBP)
            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES).absolutePath+File.separator+"compress")
            .compressToFile(file)

return gambarCompress
}

}