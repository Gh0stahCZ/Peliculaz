package com.tomaschlapek.peliculaz.presentation.presenter.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.tomaschlapek.peliculaz.App
import com.tomaschlapek.peliculaz.engine.UserEngine
import com.tomaschlapek.peliculaz.helper.KPreferenceHelper
import com.tomaschlapek.peliculaz.helper.KRealmHelper
import com.tomaschlapek.peliculaz.presentation.presenter.interfaces.view.KIBaseView
import eu.inloop.viewmodel.AbstractViewModel
import io.realm.Realm
import timber.log.Timber

/**
 * Base class for view models of [PresenterActivity]
 */
abstract class KActivityPresenter<TView : KIBaseView> : KBasePresenter<TView>() {

  /* Protected Attributes *************************************************************************/

  /**
   * Link to view (FragmentActivity).
   */
  lateinit var mActivity: FragmentActivity

  /**
   * Holds default instance of realm.
   */
  protected var mRealm: Realm? = null
  private var mState: Bundle? = null

  lateinit var userEngine: UserEngine
  lateinit var preferenceHelper: KPreferenceHelper
  lateinit var realmHelper: KRealmHelper

  abstract fun registerSubscribers()
  abstract fun unregisterSubscribers()

  /* Public Methods *******************************************************************************/

  override fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?) {
    super.onCreate(arguments, savedInstanceState)
    mState = savedInstanceState ?: arguments

    userEngine = App.getAppComponent().provideUserEngine()
    preferenceHelper = App.getAppComponent().provideKPreferenceHelper()
    realmHelper = App.getAppComponent().provideRealmHelper()

  }

  /**
   * @see AbstractViewModel.onBindView
   */
  override fun onBindView(view: TView) {
    super.onBindView(view)

    // Remember the link to activity.
    mActivity = view as FragmentActivity

    mRealm = Realm.getDefaultInstance()

    if (mState != null) {
      loadArguments(mState)
    }

    registerSubscribers()
  }

  override fun clearView() {
    super.clearView()

    unregisterSubscribers()

    // Close the default realm instance.
    if (mRealm != null && !mRealm!!.isClosed) {
      mRealm!!.close()
      mRealm = null
    }
  }

  open fun onNewIntent(intent: Intent) {
    Timber.d("onNewIntent() : " + intent)
  }


  override fun onSaveInstanceState(bundle: Bundle) {
    super.onSaveInstanceState(bundle)
  }

  /**
   * Loads arguments from SavedInstance or passed bundle.
   * @param state Bundle with data.
   */
  private fun loadArguments(state: Bundle?) {
    // Load arguments.
    if (state != null) {
      //      if (state.containsKey(Argument.EXTRA_GAME_ID)) {
      //        gameId = state.getString(Argument.EXTRA_GAME_ID);
      //      }
    }
  }
}
