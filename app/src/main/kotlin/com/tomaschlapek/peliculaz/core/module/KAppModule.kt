package com.tomaschlapek.peliculaz.core.module

import android.content.Context
import com.tomaschlapek.peliculaz.App
import com.tomaschlapek.peliculaz.engine.UserEngine
import com.tomaschlapek.peliculaz.helper.KPreferenceHelper
import com.tomaschlapek.peliculaz.helper.KRealmHelper
import com.tomaschlapek.peliculaz.realm.repository.KRBaseRepository
import com.tomaschlapek.peliculaz.realm.repository.KRUserRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Base app module.
 * Application module refers to sub components and provides application level dependencies.
 */
@Module
class KAppModule {

  @Provides
  @Singleton
  internal fun provideContext(application: App): Context {
    return application.applicationContext
  }

  @Provides
  @Singleton
  fun provideKPreferenceHelper(context: Context): KPreferenceHelper {
    return KPreferenceHelper(context)
  }

  @Singleton
  @Provides
  fun provideRealmHelper(rUserRepository: KRUserRepository): KRealmHelper {
    return KRealmHelper(rUserRepository)
  }

  @Singleton
  @Provides
  fun provideRBaseRepository(preferenceHelper: KPreferenceHelper): KRBaseRepository {
    return KRBaseRepository(preferenceHelper)
  }

  @Singleton
  @Provides
  fun provideUserEngine(retrofit: Retrofit, preferenceHelper: KPreferenceHelper, realmHelper: KRealmHelper): UserEngine {
    return UserEngine(retrofit, preferenceHelper, realmHelper)
  }
}
