package com.test.dosplash.ui.image

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drbindra.badabusiness.ui.viewholder.BaseViewHolder
import com.test.dosplash.model.Image

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
class ImageAdapter(private var items: ArrayList<Image>, val context: Context) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    companion object {
        private const val TYPE_EVEN = 0
        private const val TYPE_ODD = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        TODO("Not yet implemented")
    }
}