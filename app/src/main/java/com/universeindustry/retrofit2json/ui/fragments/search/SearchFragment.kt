package com.universeindustry.retrofit2json.ui.fragments.search


import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.universeindustry.retrofit2json.MainActivity
import com.universeindustry.retrofit2json.R
import com.universeindustry.retrofit2json.databinding.FragSearchBinding
import com.universeindustry.retrofit2json.retrofit2.RetrofitManager
import com.universeindustry.retrofit2json.utils.extensions.Resizing.reSizedImage
import com.universeindustry.retrofit2json.utils.extensions.Strings

class SearchFragment : Fragment(){
    private lateinit var binding: FragSearchBinding
    //----------------------------------------- 뷰 생성시  --------------------------------------------------------------------//
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        try {
            binding = DataBindingUtil.inflate(inflater, R.layout.frag_search, container, false)
        }catch (e:Exception){
            e("mException", "에러발생 -> SearchFragment, onCreateView // Exception : ${e.message}")
        }finally {
            return binding.root
        }
    }

    //----------------------------------------- 뷰 생성 완료 시 --------------------------------------------------------------------//
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            binding.search.apply {
                setCompoundDrawablesWithIntrinsicBounds(
                        reSizedImage(
                                requireContext(),
                                R.drawable.image_search,
                                30,
                                30
                        ),
                        null,
                        null,
                        null
                )
            }
            binding.button.frameSearchButton.setOnClickListener {
                when(val text = binding.search.text.toString()){
                    ""-> {
                        Toast.makeText(requireContext(), requireContext().resources.getString(R.string.rallo_edittext_null), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        val textError = requireContext().resources.getString(R.string.rallo_error)
                        val resultNull = requireContext().resources.getString(R.string.rallo_result_null)
                        RetrofitManager.instance.searchPhotos(
                                search = text,
                                completion = {
                                    when(it.first().author){
                                        textError -> {
                                            Toast.makeText(requireContext(), textError, Toast.LENGTH_SHORT).show()
                                        }
                                        resultNull -> {
                                            Toast.makeText(requireContext(), resultNull, Toast.LENGTH_SHORT).show()
                                        }
                                        else -> {
                                            val fragmentType : String = (activity as MainActivity).mainVM.getFragmentType.value!!
                                            searchAdapter?.setFragmentType(
                                                get = fragmentType,
                                                newList = it
                                            )
                                        }
                                    }
                                }
                        )
                    }
                }
            }
            setRecyclerView(binding.recyclerview)
        }catch (e:Exception){
            e("mException", "에러발생 -> SearchFragment, onViewCreated // Exception : ${e.message}")
        }finally {
            super.onViewCreated(view, savedInstanceState)
        }
    }

    //----------------------------------------- 액티비티 생성 완료 시 --------------------------------------------------------------------//
    private val _SearchVM : SearchVM by viewModels()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        try {
            activity?.let {
                binding.apply {
                    searchVM = _SearchVM
                    lifecycleOwner = this@SearchFragment
                }

                val fragmentType : String = (activity as MainActivity).mainVM.getFragmentType.value!!
                _SearchVM.let {
                    it.searchList.observe(activity as MainActivity, Observer {
                        when(fragmentType){
                            Strings.search-> {

                            }
                        }
                    })
                }
            }
            binding.reward.setOnClickListener {
                (activity as MainActivity).showRewardAd {
                    Toast.makeText(requireContext(), requireContext().resources.getString(R.string.rallo_thankyou),Toast.LENGTH_SHORT).show()
                }
            }
        }catch (e:Exception){
            e("mException", "에러발생 : SearchFragment, onActivityCreated  // Exception : ${e.message}")
        }finally {
            super.onActivityCreated(savedInstanceState)
        }
    }

    //----------------------------------------- 리사이클러뷰 설정 ---------------------------------------------//
    private var searchAdapter: SearchAdapter? = null
    private fun setRecyclerView(get : RecyclerView){
        try {
            searchAdapter = SearchAdapter()

            get.apply {
                adapter = searchAdapter
                layoutManager = GridLayoutManager(context, 2).apply {
                    orientation = GridLayoutManager.VERTICAL
                    isItemPrefetchEnabled = false
                }
                setHasFixedSize(true)
                setItemViewCacheSize(0)
            }
        }catch (e:Exception){
            e("mException", "SearchFragment, setRecyclerView // Exception : ${e.message}")
        }
    }
}