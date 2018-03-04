package com.tomaschlapek.peliculaz.realm.repository

import com.tomaschlapek.peliculaz.helper.KPreferenceHelper
import com.tomaschlapek.peliculaz.realm.RUser
import io.realm.Realm
import javax.inject.Inject

/**
 * Created by tomaschlapek on 15/9/17.
 */
class KRUserRepository @Inject constructor(preferenceHelper: KPreferenceHelper) : KRAbstractRepository<RUser>(preferenceHelper) {

  fun getUserByToken(realm: Realm?, token: String?): RUser? {
    return getFirst(realm?.where(RUser::class.java))
  }
}