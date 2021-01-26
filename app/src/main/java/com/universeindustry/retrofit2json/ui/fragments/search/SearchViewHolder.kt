package com.universeindustry.retrofit2json.ui.fragments.search


import android.util.Log.e
import androidx.recyclerview.widget.RecyclerView
import com.universeindustry.retrofit2json.BR
import com.universeindustry.retrofit2json.databinding.HolderSearchBinding
import com.universeindustry.retrofit2json.retrofit2.PhotoModel
import com.universeindustry.retrofit2json.ui.recyclerview.IClickListener

class SearchViewHolder(
    private val holderBinding: HolderSearchBinding,
    private val iClickListener: IClickListener?
) : RecyclerView.ViewHolder(holderBinding.root){
    fun dataBinding(model : PhotoModel){
        holderBinding.apply {
            setVariable(BR.photoModel, model)
            executePendingBindings()
        }
    }

    init {

    }
}
