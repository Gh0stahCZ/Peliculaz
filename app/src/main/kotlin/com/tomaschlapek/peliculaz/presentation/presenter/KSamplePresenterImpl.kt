package com.tomaschlapek.peliculaz.presentation.presenter

import android.content.Intent
import android.os.Bundle
import com.tomaschlapek.peliculaz.R
import com.tomaschlapek.peliculaz.presentation.presenter.base.KActivityPresenter
import com.tomaschlapek.peliculaz.presentation.presenter.interfaces.presenter.KISamplePresenter
import com.tomaschlapek.peliculaz.presentation.presenter.interfaces.view.KISampleActivityView
import com.tomaschlapek.peliculaz.util.str
import io.realm.Realm
import timber.log.Timber


/**
 * Sample presenter.
 */
class KSamplePresenterImpl : KActivityPresenter<KISampleActivityView>(), KISamplePresenter {

  /* Public Constants *****************************************************************************/

  /**
   * Extra identifiers.
   */
  companion object Argument {
    const val GAME_ID = "game_id"
  }

  /* Private Attributes ***************************************************************************/

  var mGameId: String? = null


  /* Public Methods *******************************************************************************/

  override fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?) {
    super.onCreate(arguments, savedInstanceState)
    val state = savedInstanceState ?: arguments
    loadArguments(state) // Load arguments.
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
  }

  override fun onBindView(view: KISampleActivityView) {
    super.onBindView(view)
    init()
  }

  override fun registerSubscribers() {
  }

  override fun unregisterSubscribers() {
  }

  override fun onButtonClick() {
    Timber.i("onButtonClick()")
    view?.showToast("Button clicked")
  }

  override fun getSharingText(): String {
    return str(R.string.me)
  }

  fun getRealm(): Realm? {
    return super.mRealm
  }

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    loadArguments(intent.extras)
  }

  /* Private Methods ******************************************************************************/

  // Load arguments.
  private fun loadArguments(state: Bundle?) {
    if (state?.containsKey(Argument.GAME_ID) == true) {
      mGameId = state.let { state.getString(Argument.GAME_ID) }
    }
  }

  private fun init() {
    Timber.d("init()")

  }

  /* Inner classes ********************************************************************************/

}