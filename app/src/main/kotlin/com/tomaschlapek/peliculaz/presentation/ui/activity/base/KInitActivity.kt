package com.tomaschlapek.peliculaz.presentation.ui.activity.base

import android.os.Bundle
import com.tomaschlapek.peliculaz.presentation.presenter.KBasePresenterImpl
import com.tomaschlapek.peliculaz.presentation.presenter.interfaces.view.KIBaseView

class KInitActivity : KBaseActivity<KIBaseView, KBasePresenterImpl>(), KIBaseView {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun getPresenterClass(): Class<KBasePresenterImpl>? {
    return null
  }

}