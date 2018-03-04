package com.tomaschlapek.peliculaz.presentation.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.tomaschlapek.peliculaz.R
import com.tomaschlapek.peliculaz.databinding.ActivitySampleBinding
import com.tomaschlapek.peliculaz.presentation.presenter.KSamplePresenterImpl
import com.tomaschlapek.peliculaz.presentation.presenter.interfaces.view.KISampleActivityView
import com.tomaschlapek.peliculaz.presentation.ui.activity.base.KToolbarActivity
import com.tomaschlapek.peliculaz.util.ActivityBinder
import com.tomaschlapek.peliculaz.util.str


/**
 * Sample Activity
 */
class KSampleActivity : KToolbarActivity<KISampleActivityView, KSamplePresenterImpl>(), KISampleActivityView {

  /* Private Attributes ***************************************************************************/

  private val mViews: ActivitySampleBinding by ActivityBinder(R.layout.activity_sample)

  /* Public Methods *******************************************************************************/


  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val menuRes: Int = R.menu.menu_share
    menuInflater.inflate(menuRes, menu)

    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val itemId = item.itemId
    if (itemId == R.id.share_item) {
      presenter.onShareDialog()
      return true
    }
    return false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    onCreatePresenter(savedInstanceState)
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    init()
  }

  override fun showToast(text: String) {
    mViews.sampleTextView.text = presenter.getSharingText()
    super.showToast(text)
  }

  override fun getPresenterClass(): Class<KSamplePresenterImpl> {
    return KSamplePresenterImpl::class.java
  }

  override fun getToolbarTitle(): String {
    return str(R.string.toolbar_sample)
  }

  /* Private Methods ******************************************************************************/

  private fun init() {
    mViews.sampleButton.setOnClickListener { view -> presenter.onButtonClick() }
  }
}