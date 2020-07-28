package com.test.dosplash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
@Parcelize
data class Urls (
    var full:String? = "",
    var thumb:String? = ""
):Parcelable