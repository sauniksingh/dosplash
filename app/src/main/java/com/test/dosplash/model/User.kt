package com.test.dosplash.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
@Parcelize
data class User (
    var name: String? = "",
    var location: String? = "",

    @SerializedName("profile_image")
    var profileImage: ProfileImage? = ProfileImage()
):Parcelable