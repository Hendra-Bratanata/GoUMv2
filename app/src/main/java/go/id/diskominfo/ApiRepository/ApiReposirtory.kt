package go.id.diskominfo.ApiRepository

import java.net.URL

class ApiReposirtory{
    fun doRequest(url: String):String{
        val data = URL(url).readText()

        return data
    }
}