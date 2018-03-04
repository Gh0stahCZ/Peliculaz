package com.tomaschlapek.peliculaz.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Class holds information about certain user.
 */
open class RUser() : RealmObject() {

  /* Attributes ***********************************************************************************/

  @PrimaryKey
  var id: String? = null

  var email: String? = null
  var nickName: String? = null
  var verified: Boolean = false
}