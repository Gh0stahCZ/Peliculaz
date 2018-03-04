package com.tomaschlapek.peliculaz.util

import com.tomaschlapek.peliculaz.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import java.util.*

/**
 * Custom network interceptor.
 */

class NetworkInterceptor : Interceptor {

  companion object {
    private val HEADER_CONTENT_TYPE_KEY = "Content-Type"
    private val HEADER_CONTENT_TYPE_VALUE = "application/json"
  }

  /* Public Methods *******************************************************************************/

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val original = chain.request()

    val url = original
      .url()
      .newBuilder()
      .addQueryParameter("apikey", BuildConfig.MOVIE_DB_API_KEY)
      .addQueryParameter("language", Locale.getDefault().isO3Language)
      .build()

    val update = original.newBuilder().url(url).build()


    Timber.d("Request: [%s] %s", update.method(), original.url())
    Timber.d("Request: %s", update.headers().toString())
    if (update.body() != null) {
      Timber.d("Request: %s", update.body()?.bodyToString())
    }

    // TODO Uncomment this line for slow connection simulation
    // expensiveOperation()

    return chain.proceed(update)
  }

  /* Private Methods ******************************************************************************/

  /**
   * Simulates time-expensive operation.
   */
  private fun expensiveOperation() {
    try {
      Thread.sleep(8000)
    } catch (e: InterruptedException) {
      // this is ok
    }

  }
}
