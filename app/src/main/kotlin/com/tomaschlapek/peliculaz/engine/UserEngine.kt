package com.tomaschlapek.peliculaz.engine

import com.tomaschlapek.peliculaz.App
import com.tomaschlapek.peliculaz.R
import com.tomaschlapek.peliculaz.helper.KPreferenceHelper
import com.tomaschlapek.peliculaz.helper.KRealmHelper
import com.tomaschlapek.peliculaz.network.EmailPassBody
import com.tomaschlapek.peliculaz.network.UserInfoResponse
import com.tomaschlapek.peliculaz.network.UserService
import com.tomaschlapek.peliculaz.subscriber.DefaultSubscriber
import com.tomaschlapek.peliculaz.subscriber.LoadingSubscriber
import com.tomaschlapek.peliculaz.util.applyTransform
import com.tomaschlapek.peliculaz.util.createErrorResponse
import org.jetbrains.anko.toast
import retrofit2.Response
import retrofit2.Retrofit
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription


/**
 * Holds current state of user and makes network requests.
 */
class UserEngine(private var retrofit: Retrofit, private var preferenceHelper: KPreferenceHelper, private val realmHelper: KRealmHelper) {

  /* Private Constants ****************************************************************************/
  /* Private Attributes ***************************************************************************/

  /**
   * State that indicates loading progress.
   */
  private var loading = false
    set(value) {
      field = value
      propagateLoading()
    }

  private var lastMyProfileInfoRequest: CompositeSubscription? = null
  private var lastEmailLoginRequest: CompositeSubscription? = null

  // Subjects ************************** //

  private var myProfileInfoOutputSubject = PublishSubject.create<Response<UserInfoResponse>>()
  private var emailLoginOutputSubject = PublishSubject.create<Response<UserInfoResponse>>()

  private var loadingOutputSubject = BehaviorSubject.create<Boolean>()

  /* Public Methods *******************************************************************************/



  fun loadAndRequest(req: () -> Unit) {
    loading = true
    req()
  }

  fun handleError() {
    App.getAppComponent().provideContext().toast(R.string.general_error_message)
    loading = false
  }

  //******** REGISTRATIONS ************************************************************************/

  fun registerLoadingSubscriber(subscriber: LoadingSubscriber<Boolean>): CompositeSubscription {
    return CompositeSubscription(loadingOutputSubject.subscribe(subscriber))
  }

  fun registerEmailVerifiedSubscriber(subscriber: LoadingSubscriber<Boolean>): CompositeSubscription {
    return CompositeSubscription(loadingOutputSubject.subscribe(subscriber))
  }

  fun registerEmailLoginSubscriber(subscriber: DefaultSubscriber<Response<UserInfoResponse>, UserInfoResponse>): CompositeSubscription {
    return CompositeSubscription(emailLoginOutputSubject.subscribe(subscriber))
  }

  fun registerMyProfileInfoSubscriber(subscriber: DefaultSubscriber<Response<UserInfoResponse>, UserInfoResponse>): CompositeSubscription {
    return CompositeSubscription(myProfileInfoOutputSubject.subscribe(subscriber))
  }


  //******** NETWORK REQUESTS *********************************************************************/

  fun requestEmailLogin(emailPassBody: EmailPassBody) {
    loadAndRequest({
      lastEmailLoginRequest?.unsubscribe()
      lastEmailLoginRequest = getEmailLogin(emailPassBody)
    })
  }


  fun requestMyProfileInfo() {
    loadAndRequest({
      lastMyProfileInfoRequest?.unsubscribe()
      lastMyProfileInfoRequest = getMyProfileInfo()
    })
  }


  /* Private Methods ******************************************************************************/

  /**
   * Propagates change of loading state.
   */
  private fun propagateLoading() {
    loadingOutputSubject.onNext(loading)
  }

  /* Email Login */

  private fun getEmailLogin(body: EmailPassBody) = CompositeSubscription().apply {
    add(
      retrofit.create(UserService::class.java).setEmailLogin(body)
        .onErrorResumeNext { createErrorResponse(it) }
        .applyTransform({
          emailLoginOutputSubject.onNext(it as Response<UserInfoResponse>?)
          loading = false
        }, { handleError() }))
  }


  private fun getMyProfileInfo() = CompositeSubscription().apply {
    add(
      retrofit.create(UserService::class.java).getMyProfileInfo()
        .onErrorResumeNext { createErrorResponse(it) }
        .applyTransform({
          myProfileInfoOutputSubject.onNext(it as Response<UserInfoResponse>?)
          loading = false
        }, { handleError() }))
  }

  /* Getters / Setters ****************************************************************************/
  /* Inner classes ********************************************************************************/

}