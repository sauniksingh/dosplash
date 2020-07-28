package com.drbindra.badabusiness.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Saunik Singh on 5/16/2020.
 * Bada Business
 */
abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}