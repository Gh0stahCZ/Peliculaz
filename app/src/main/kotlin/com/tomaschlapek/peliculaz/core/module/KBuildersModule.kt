package com.tomaschlapek.peliculaz.core.module

import com.tomaschlapek.peliculaz.presentation.ui.activity.KSampleActivity
import com.tomaschlapek.peliculaz.presentation.ui.activity.base.KInitActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * This module contains all the binding to the sub component builders in the app
 */

@Module
abstract class KBuildersModule {

  @ContributesAndroidInjector
  abstract fun contributeInitActivityInjector(): KInitActivity

  @ContributesAndroidInjector
  abstract fun contributeKSampleActivityInjector(): KSampleActivity
}
