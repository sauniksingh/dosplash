package com.test.dosplash.util

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.widget.Toast

/**
 * Created by Saunik Singh on 4/30/2020.
 * Bada Business
 */
object ToastUtils {
    /**
     * Class is designed for following purposes- 1. Immediate display of new toast,
     * even if previous is being displayed. 2. Changing position of all toasts
     * through-out application.
     * <p/>
     * It will work as intended, only if all toasts in application are shown using
     * it.
     */
    /**
     * This field will be used if any positive value is assigned to it.
     */
    private val TOAST_MIN_SHOW_MILLIS = 1000

    /**
     * mSingletonToast will be Singleton in application.
     */
    private var mSingletonToast: Toast? = null

    /**
     * These fields will be used to concat messages if required.
     */
    private var mLastShownMesssage: String? = null
    private var mLastShownAtMillis: Long = 0

    /**
     * Shows the pMessage as an immediate Toast for Toast.LENGTH_LONG duration.
     *
     * @param pContext {@link Context}
     * @param pMessage {@link String}
     */
    fun showToast(pContext: Context?, pMessage: String?) {
        showToast(pContext, pMessage, false)
    }

    /**
     * Shows the pMessage as an immediate Toast for Toast.LENGTH_LONG duration.
     *
     * @param pContext [Context]
     * @param pMessage [String]
     * @param pCanAppendToLastToast boolean
     * note: If pCanAppendToLastToast is true and last shown toast is not shown
     * for TOAST_MIN_SHOW_MILLIS then pMessage will be appended to it.
     */

    fun showToast(
        pContext: Context?, pMessage: String?,
        pCanAppendToLastToast: Boolean
    ) {
        if (!pCanAppendToLastToast) {
            mLastShownAtMillis = 0
        }
        showToast(pContext, pMessage!!, Toast.LENGTH_LONG)
    }

    /**
     * Shows the pMessage as an immediate Toast for Toast.LENGTH_LONG duration.
     *
     * @param pContext [Context]
     * @param pPrefix [String]
     * @param pMessage [String]
     * @param pFallback [String]
     */
    @JvmStatic
    fun showToast(
        pContext: Context?, pPrefix: String,
        pMessage: String, pFallback: String
    ) {
        if (TextUtils.isEmpty(pMessage)) {
            showToast(pContext, pPrefix + pFallback)
        } else {
           showToast(pContext, pPrefix + pMessage)
        }
    }

    /**
     * Shows the pMessage as an immediate Toast for pDuration.
     *
     * @param pContext [Context]
     * @param pMessage [String]
     * @param duration int
     */
    @SuppressLint("ShowToast")
    fun showToast(
        pContext: Context?, pMessage: String,
        duration: Int
    ) {
        /*
         * If pMessage is null or blank, new toast will not be shown.
         */
        var pDuration = duration
        if (TextUtils.isEmpty(pMessage)) {
            return
        }
        if (pDuration != Toast.LENGTH_LONG && pDuration != Toast.LENGTH_SHORT) {
            pDuration = Toast.LENGTH_LONG
        }
        removeToast()
        if (mSingletonToast == null) {
            mSingletonToast = Toast.makeText(
                pContext,
                pMessage, pDuration
            )

            /*
      This field will be used if any positive value is assigned to it.
     */
//            int TOAST_MARGIN_BOTTOM_DP = 0;
//            if (TOAST_MARGIN_BOTTOM_DP > 0) {
//                float density = pContext.getResources().getDisplayMetrics().density;
//                int yOffSet = (int) (density * TOAST_MARGIN_BOTTOM_DP);
//                mSingletonToast.setGravity(Gravity.CENTER_HORIZONTAL
//                        | Gravity.BOTTOM, 0, yOffSet);
//            }
        }
        mSingletonToast?.duration = pDuration
        if (mLastShownAtMillis == 0L) {
            mLastShownMesssage = pMessage
        } else if (System.currentTimeMillis() - mLastShownAtMillis < TOAST_MIN_SHOW_MILLIS) {
            if (!mLastShownMesssage?.contains(pMessage)!!) {
                mLastShownMesssage =
                    mLastShownMesssage + "\n" + pMessage
            }
        } else {
            mLastShownMesssage = pMessage
        }
        mLastShownAtMillis = System.currentTimeMillis()
        mSingletonToast?.setText(mLastShownMesssage)
        mSingletonToast?.show()
    }

    /*
     * Removes the currently displaying toast.
     */
    private fun removeToast() {
        if (mSingletonToast != null) {
            mSingletonToast?.cancel()
            mSingletonToast = null
        }
    }
    /**
     * Shows the pMessage as an immediate Toast for Toast.LENGTH_LONG duration.
     *
     * @param pContext {@link Context}
     * @param pMessage {@link String}
     */
    fun showToast(pContext: Context, id: Int) {
        showToast(pContext, pContext.getString(id), false)
    }
}