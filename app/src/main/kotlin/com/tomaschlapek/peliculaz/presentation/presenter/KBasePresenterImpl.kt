package com.tomaschlapek.peliculaz.presentation.presenter

import com.tomaschlapek.peliculaz.presentation.presenter.base.KActivityPresenter
import com.tomaschlapek.peliculaz.presentation.presenter.interfaces.view.KIBaseView

/**
 * Created by tomaschlapek on 15/9/17.
 */
class KBasePresenterImpl : KActivityPresenter<KIBaseView>() {

  override fun registerSubscribers() {
  }

  override fun unregisterSubscribers() {
  }

  /* Public Constants *****************************************************************************/
  /* Public Methods *******************************************************************************/

  override fun onBindView(view: KIBaseView) {
    super.onBindView(view)
  }

}