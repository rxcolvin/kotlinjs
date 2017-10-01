package httprpc

import org.w3c.xhr.XMLHttpRequest
import kotlin.js.Promise

/**
 * Created by richard on 10/09/2017.
 */

//executor: (resolve: (T) -> kotlin.Unit, reject: (kotlin.Throwable) -> kotlin.Unit) -> kotlin.Unit


data class HttpResp(
    val body: String,
    val status: Int = 200,
    val headers: List<Pair<String, String>>  = emptyList()
)

fun get(
    url: String,
    headers: List<Pair<String, String>>  = emptyList()
) =
    invoke("GET", url, null, headers)

fun put(
    url: String,
    body: String,
    headers: List<Pair<String, String>>  = emptyList()
) =  invoke("PUT", url, null, headers)

fun post(
    url: String,
    body: String,
    headers: List<Pair<String, String>>  = emptyList()
) =  invoke("POST", url, null, headers)


private fun invoke(
    type: String,
    url: String,
    body: String? = null,
    headers: List<Pair<String, String>>  = emptyList(),
    respHeaders: List<String> = emptyList()
) =
    Promise<HttpResp> { resolve, reject ->
      val xhr = XMLHttpRequest()
      xhr.open(type,  url)
      headers.forEach {
        xhr.setRequestHeader(it.first, it.second)
      }

      xhr.onload = { resolve(
          HttpResp(
              body = xhr.responseText,
              status = xhr.status.toInt(),
              headers = respHeaders.map {
                it to xhr.getResponseHeader(it)
              }
                  .filter{it.second != null}
                  .map {
                     it.first to it.second as String
                  }
              )
      ) }
      xhr.onerror = {
        reject(RuntimeException(xhr.responseText))
      }
      if (body != null) xhr.send(body) else (xhr.send())
    }

