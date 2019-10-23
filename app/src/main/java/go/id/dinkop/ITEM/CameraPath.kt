package go.id.dinkop.ITEM

import android.os.Environment

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

object CameraPath {
 var cameraFilePath: String? = null
    @Throws(IOException::class)
     fun createImageFile(): File {

        val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"GOUM")
        if(!dir.exists()){
            println("Buat Folder baru didalam picture")
            dir.mkdirs()
        }


       println("dalam create File")
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        //This is the directory in which the file will be created. This is the default location of Camera photos
        val storageDir = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),"GOUM")
        println(storageDir)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )
        println(image.absolutePath)
        // Save a file: path for using again
        cameraFilePath = "file://" + image.absolutePath
        println(cameraFilePath)
        return image
    }

}
