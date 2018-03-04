package com.tomaschlapek.peliculaz.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import com.google.android.gms.maps.model.LatLng
import com.tomaschlapek.peliculaz.R
import com.tomaschlapek.peliculaz.presentation.ui.activity.KSampleActivity
import com.tomaschlapek.peliculaz.util.str
import org.jetbrains.anko.*
import timber.log.Timber


/**
 * Navigation helper class.
 */
class KNavigationHelper {

  /* Private Constants ****************************************************************************/
  /* Private Attributes ***************************************************************************/

  private val mTime: Long = System.currentTimeMillis()

  /* Constructor **********************************************************************************/
  /* Static Methods *******************************************************************************/

  companion object {

    @JvmStatic
    fun openKSampleActivity(context: Context, isInitActivity: Boolean) {
      val intent = context.intentFor<KSampleActivity>(/*KSamplePresenterImpl.Argument.EXTRA_GAME_ID to "game"*/)
      launchActivity(intent, context, isInitActivity)
    }


    /**
     * Make custom actions.
     */

    @JvmStatic
    fun openBrowser(context: Context, url: String) {
      context.browse(url)
    }

    @JvmStatic
    fun openBrowser(context: Context, urlResId: Int) {
      val url = str(urlResId)
      context.browse(url)
    }

    @JvmStatic
    fun openNavigation(context: Context, location: LatLng? = null) {

      val gmmIntentUri = Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia")
      val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
      mapIntent.`package` = "com.google.android.apps.maps"
      context.startActivity(mapIntent)
    }

    @JvmStatic
    fun makeCall(context: Context, phoneNumber: String) {
      val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber))
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      context.startActivity(intent)
    }

    @JvmStatic
    fun openInitActivity(context: Context, activityName: String, isFirstRun: Boolean) {
      openInitActivityWithData(context, activityName, isFirstRun)
    }

    // ******* Fragments ******* //

    // ******* Private static functions ******* //

    private fun openInitActivityWithData(context: Context, activityName: String, isFirstRun: Boolean) {
      val isInitActivity = true

      when {
        isFirstRun -> openKSampleActivity(context, isInitActivity)
      //        KCreateProfileActivity::class.java.name == activityName -> openKCreateProfileActivity(context, isInitActivity)
        else -> openKSampleActivity(context, isInitActivity)
      }

    }

    /**
     * Launches selected activity.
     */
    private fun launchActivity(intent: Intent, context: Context, isInitActivity: Boolean) {
      if (isInitActivity) {
        intent.newTask().clearTask()
      } else {
        intent.clearTop().newTask()
      }
      context.startActivity(intent)
    }


    /**
     * Replaces fragment in certain container.
     */
    private fun replaceFragment(transaction: FragmentTransaction, containerId: Int, fragment: Fragment, addToBackStack: Boolean) {
      replaceFragment(transaction, containerId, fragment, addToBackStack, -1, -1)
    }

    /**
     * Replaces fragment in certain container.
     */
    private fun replaceFragment(transaction: FragmentTransaction, containerId: Int, fragment: Fragment, addToBackStack: Boolean, enterAnim: Int, exitAnim: Int) {
      Timber.d("replaceFragment(): addToBackStack: " + addToBackStack)

      // Set transaction animations if passed.
      if (enterAnim >= 0 && exitAnim >= 0) {
        transaction.setCustomAnimations(enterAnim, R.anim.no_anim, R.anim.no_anim, exitAnim)
      }

      // Replace fragment in container.
      transaction.replace(containerId, fragment)

      // Add to back-stack.
      if (addToBackStack) {
        transaction.addToBackStack(null)
      }

      transaction.commitAllowingStateLoss()
    }

    /**
     * Commit fragment in activity.
     * @param activity Parent activity.
     * *
     * @return Fragment transaction.
     */
    @SuppressLint("CommitTransaction")
    private fun beginTransaction(activity: FragmentActivity): FragmentTransaction {
      return activity.supportFragmentManager.beginTransaction()
    }

    /**
     * Commit fragment nested in other fragment.
     * @param fragment Parent activity.
     * *
     * @return Fragment transaction.
     */
    @SuppressLint("CommitTransaction")
    private fun beginTransaction(fragment: Fragment): FragmentTransaction {
      return fragment.fragmentManager.beginTransaction()
    }
  }
}