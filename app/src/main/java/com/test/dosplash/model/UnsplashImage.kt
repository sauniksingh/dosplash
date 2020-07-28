package com.test.dosplash.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
@Parcelize
data class UnsplashImage(
    var urls: Urls? = Urls(),

    @SerializedName("alt_description")
    var description: String? = "",
    var user: User? = User(),
    var type: String = ""
) : Parcelable