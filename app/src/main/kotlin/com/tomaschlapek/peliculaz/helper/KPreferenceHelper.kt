package com.tomaschlapek.peliculaz.helper

import android.content.Context
import com.tomaschlapek.peliculaz.R
import com.tomaschlapek.peliculaz.util.bindSharedPreference

const val INVALID_STRING = ""

/**
 * Simple accessor to preferences items.
 */
class KPreferenceHelper(context: Context) {

  /* Private Attributes ***************************************************************************/

  /**
   * Determine if application runs first time.
   */
  var firstRun: Boolean by bindSharedPreference(context, R.string.pref_key_first_run, false)

  //////////////////////////////////////////////////////////////////////////////////////////////////

}