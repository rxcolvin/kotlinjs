package httprpc

import org.w3c.xhr.XMLHttpRequest
import kotlin.js.Promise

/**
 * Created by richard on 10/09/2017.
 */

//executor: (resolve: (T) -> kotlin.Unit, reject: (kotlin.Throwable) -> kotlin.Unit) -> kotlin.Unit


data class HttpReq(
    val body: String,
    val headers: Map<String, List<String>> = emptyMap()
)

data class HttpResp(
    val body: String
)

fun get(
    url: String,
    headers: List<Pair<String, String>>  = emptyList()
) =
    Promise<HttpResp> { resolve, reject ->
      val xhr = XMLHttpRequest()
      xhr.open("GET",  url)
      headers.forEach {
        xhr.setRequestHeader(it.first, it.second)
      }

      xhr.onload = { resolve(HttpResp(xhr.responseText)) }
      xhr.onerror = {
        reject(RuntimeException(xhr.responseText))
      }
      xhr.send()
    }


fun main(args: Array<String>) {





}