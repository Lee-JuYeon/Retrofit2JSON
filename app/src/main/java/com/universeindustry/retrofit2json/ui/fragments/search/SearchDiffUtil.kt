package com.universeindustry.retrofit2json.ui.fragments.search

import androidx.recyclerview.widget.DiffUtil
import com.universeindustry.retrofit2json.retrofit2.PhotoModel

class SearchDiffUtil (private val oldList : MutableList<PhotoModel>,
                      private val currentList : List<PhotoModel>) : DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition] == currentList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].thumnail == currentList[newItemPosition].thumnail
    }

    // 변경 전 리스트 크기
    override fun getOldListSize(): Int = oldList.size

    // 변경 후 리스트 크기
    override fun getNewListSize(): Int = currentList.size
}