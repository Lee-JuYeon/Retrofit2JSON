package com.universeindustry.retrofit2json.ui.base

import androidx.recyclerview.widget.DiffUtil
import com.universeindustry.retrofit2json.retrofit2.PhotoModel

class BaseDiffUtil (private val oldList : MutableList<Any>,
                    private val currentList : List<Any>,
                    private val type : Any) : DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when(type){
            is PhotoModel -> {
                (oldList as MutableList<PhotoModel>)[oldItemPosition] == (currentList as MutableList<PhotoModel>)[newItemPosition]
            }

            else -> {
                (oldList as MutableList<PhotoModel>)[oldItemPosition] == (currentList as MutableList<PhotoModel>)[newItemPosition]
            }
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when(type){
            is PhotoModel -> {
                (oldList as MutableList<PhotoModel>)[oldItemPosition].thumnail == (currentList as MutableList<PhotoModel>)[newItemPosition].thumnail
            }

            else -> {
                (oldList as MutableList<PhotoModel>)[oldItemPosition].thumnail == (currentList as MutableList<PhotoModel>)[newItemPosition].thumnail
            }
        }
    }

    // 변경 전 리스트 크기
    override fun getOldListSize(): Int = oldList.size

    // 변경 후 리스트 크기
    override fun getNewListSize(): Int = currentList.size
}