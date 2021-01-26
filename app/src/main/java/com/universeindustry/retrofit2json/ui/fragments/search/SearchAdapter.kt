package com.universeindustry.retrofit2json.ui.fragments.search


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.universeindustry.retrofit2json.databinding.HolderSearchBinding
import com.universeindustry.retrofit2json.retrofit2.PhotoModel
import com.universeindustry.retrofit2json.ui.base.BaseDiffUtil
import com.universeindustry.retrofit2json.ui.recyclerview.IClickListener


class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var customList : ArrayList<PhotoModel>? = arrayListOf()
    private var fragmentType : String? = null
    fun setFragmentType(get : String, newList : List<PhotoModel>){
        // 타입 업데이트
        this.fragmentType = get

        // 리스트 업데이트
        val diffResult = DiffUtil.calculateDiff(SearchDiffUtil(customList ?: arrayListOf(), newList), false)
        diffResult.dispatchUpdatesTo(this@SearchAdapter)
        customList?.clear()
        customList?.addAll(newList)
    }

    private var iClickListener : IClickListener? = null
    fun setClickListener(get : IClickListener?){ this.iClickListener = get }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val fundBinding = HolderSearchBinding.inflate(layoutInflater, parent, false)
        return SearchViewHolder(fundBinding, iClickListener)
    }
    override fun getItemCount(): Int {
        return customList?.size ?: 0
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SearchViewHolder -> {
                val dataList = customList!![position]
                holder.dataBinding(
                    model = dataList
                )
            }
            else -> {
                val exception = Exception()
                throw Exception("SearchAdapter, onBindViewHolder // Exception : ${exception.message}")
            }
        }
    }
    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        when(holder){
            is SearchViewHolder -> {
                holder.dataBinding(
                    model = PhotoModel(
                        thumnail = null,
                        author = null,
                        createdAt = null,
                        likesCount = 0

                    )
                )
            }
            else -> {
                val exception = Exception()
                throw Exception("SearchAdapter, onViewRecycled // Exception : ${exception.message}")
            }
        }
        super.onViewRecycled(holder)
    }
}