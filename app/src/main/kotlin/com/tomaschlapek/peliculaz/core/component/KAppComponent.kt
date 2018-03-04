package com.tomaschlapek.peliculaz.core.component

import android.content.Context
import com.squareup.picasso.Picasso
import com.tomaschlapek.peliculaz.App
import com.tomaschlapek.peliculaz.core.module.KAppModule
import com.tomaschlapek.peliculaz.core.module.KBuildersModule
import com.tomaschlapek.peliculaz.core.module.KNetModule
import com.tomaschlapek.peliculaz.engine.UserEngine
import com.tomaschlapek.peliculaz.helper.KPreferenceHelper
import com.tomaschlapek.peliculaz.helper.KRealmHelper
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Base app component.
 */
@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, KAppModule::class,  KNetModule::class, KBuildersModule::class))
interface KAppComponent : AndroidInjector<App> {

  fun provideContext(): Context

  fun provideUserEngine(): UserEngine

  fun provideKPreferenceHelper(): KPreferenceHelper

  fun provideRealmHelper(): KRealmHelper

  fun providePicasso(): Picasso

  fun provideRetrofit(): Retrofit

  /**
   * Description:
   * https://proandroiddev.com/dagger-2-component-builder-1f2b91237856
   */
  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: App): Builder

    fun build(): KAppComponent
  }

  /*
  void inject(BaseFragment activity);
  ToolbarHelper provideToolbarHelper();
  KRealmHelper provideRealmHelper();*/
}

