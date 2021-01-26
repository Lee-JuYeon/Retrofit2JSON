package com.universeindustry.retrofit2json

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.universeindustry.retrofit2json.databinding.ActivityMainBinding
import com.universeindustry.retrofit2json.ui.fragments.search.SearchFragment
import com.universeindustry.retrofit2json.utils.extensions.Strings

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewBind : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainViewBind.root)

        setMainVM()
        setBannderAdmob()
    }


    val mainVM : MainViewModel by viewModels()
    private var _searchFrag : SearchFragment? = null
    private fun setMainVM() {
        mainVM.getFragmentType.observe(this, Observer {
            val manager = supportFragmentManager.beginTransaction()
            when (it) {
                Strings.search -> {
                    _searchFrag = SearchFragment()
                    manager.replace(R.id.framelayout, _searchFrag!!)
//                        .addToBackStack(null)
                            .commit()
                }
                else -> {
                    _searchFrag = SearchFragment()
                    manager.replace(R.id.framelayout, _searchFrag!!)
//                        .addToBackStack(null)
                            .commit()
                }
            }
        })
    }

    //----------------------------------------- 광고 관련 설정 ---------------------------------------------------------------//

    // 배너 광고
    private fun setBannderAdmob(){
        try {
            MobileAds.initialize(this){}

            val adview = AdView(this)
            adview.adSize = AdSize.SMART_BANNER

            val admobID  = resources.getString(R.string.admob_id)
            adview.adUnitId = admobID

            val adRequest = AdRequest.Builder().build()
            mainViewBind.banner.loadAd(adRequest)
            mainViewBind.banner.adListener = object : AdListener(){
                // 광고 로드가 완료되면 실행할 코드.
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.e("BannerView", "onAdLoaded // 광고 로드가 완료되면 실행할 코드.")
                }

                // 광고 요청이 실패했을 때 실행할 코드
                override fun onAdFailedToLoad(error : LoadAdError) {
                    super.onAdFailedToLoad(error)
                    Log.e("BannerView", "onAdFailedToLoad // 광고 요청이 실패했을 때 실행할 코드.")
                    Log.e("mException", "베너광고 오류 원인 : $error")
                }

                // 광고가 화면을 덮는 오버레이를 열 때 실행할 코드.
                override fun onAdOpened() {
                    super.onAdOpened()
                    Log.e("BannerView", "onAdOpened // 광고가 화면을 덮어질때 나타나는 코드.")
                }

                // 사용자가 광고를 클릭할 때 실행할 코드.
                override fun onAdClicked() {
                    super.onAdClicked()
                    Log.e("BannerView", "onAdClicked // 사용자가 광고를 클릭할 때 실행할 코드.")
                }

                // 사용자가 광고를 누른 후 앱으로 돌아가려고 할 때 실행할 코드.
                override fun onAdClosed() {
                    super.onAdClosed()
                    Log.e("BannerView", "onAdClosed //  사용자가 광고를 누른 후 앱으로 돌아가려고 할 때 실행할 코드.")
                }


                override fun onAdImpression() {
                    super.onAdImpression()
                    Log.e("BannerView", "onAdImpression //  보상??")
                }

                // 사용자가 앱을 종료했을 때 실행할 코드.
                override fun onAdLeftApplication() {
                    super.onAdLeftApplication()
                    Log.e("BannerView", "onAdLeftApplication //  사용자가 앱을 종료했을 때 실행할 코드.")
                }
            }
        }catch (e: Exception){
            Log.e("mException", "MainActivity, setBannderAdmob // Exception : ${e.message}")
        }
    }

    //보상형 광고
    private lateinit var rewardedAd: RewardedAd
    private fun loadRewardAdView(): RewardedAd {
        rewardedAd = RewardedAd(this, resources.getString(R.string.admob_reward))
        val adLoadCallback = object: RewardedAdLoadCallback(){
            override fun onRewardedAdLoaded() {
                // Ad successfully loaded.
                Log.d("mDebugging", "디버깅 -> MainActivity, loadRewardAdView, onRewardedAdLoaded //  광고 요청 성공")
            }
            override fun onRewardedAdFailedToLoad(adError: LoadAdError) {
                // Ad failed to load.
                Log.e("mException", "에러발생 -> MainActivity, loadRewardAdView, onRewardedAdFailedToLoad //  광고 요청 실패 : ${adError.toString()}")
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
        return rewardedAd
    }
    fun showRewardAd(afterGetCoin : () -> Unit){
        try {
            if (rewardedAd.isLoaded) {
                val adCallback = object: RewardedAdCallback() {
                    override fun onRewardedAdOpened() {
                        // 이 메서드는 광고가 표시될 때 호출되며 기기 화면을 덮습니다
                    }
                    override fun onRewardedAdClosed() {
                        /*
                         이 메서드는 사용자가 닫기 아이콘을 탭하거나 뒤로 버튼을 사용하여 보상형 광고를 닫을 때 호출됩니다.
                         앱에서 오디오 출력 또는 게임 루프를 일시중지했을 때 이 메서드로 재개하면 편리합니다.

                          주의사항!
                          RewardedAd는 일회용 객체이므로, 광고가 표시된 이후에는 해당 객체를 사용해 다른 광고를 표시할 수 없다.
                          다른 광고를 요청하려면 새 RewardAd 객체를 만들어야한다.
                           이전 광고가 닫히자마자 다음 광고가 로드될 수 있도록 onRewardedAdCLosed()메서드로 RewardedAdCallback에 다른 광고를 로드하는 것이 좋다.
                         */
                        rewardedAd = loadRewardAdView()
                    }
                    override fun onUserEarnedReward(@NonNull reward: RewardItem) {
                        /*
                        이 메서드는 사용자가 광고와 상호작용하여 보상을 받아야 할 때 호출됩니다.
                        광고 단위에 설정된 보상 관련 세부정보는 RewardItem 매개변수의 getType() 및 getAmount() 메서드를 통해 액세스할 수 있습니다.
                         */
                        try {
                            afterGetCoin()
                        }catch (e:Exception){
                            Log.e("mException", "에러발생 -> MainActivity, showRewardAd , onUserEarnedReward //  Exception : ${e.message}")
                        }
                    }
                    override fun onRewardedAdFailedToShow(adError: AdError) {
                        /*
                        이 메서드는 광고 표시에 실패할 때 호출됩니다.
                        이 메서드에는 발생한 오류 유형을 나타내는 adError 매개변수가 포함됩니다.
                        가능한 오류 코드 값(adError.getCode())이 RewardedAdCallback 클래스의 상수로 정의됩니다.
                         */
                        Log.e("mException", "에러발생 -> MainActivity, showRewardAd , onRewardedAdFailedToShow //  ${adError}")
                    }
                }
                rewardedAd.show(this, adCallback)
            }
            else if (!rewardedAd.isLoaded){
                rewardedAd = loadRewardAdView()
                Log.e("mException", "에러발생 -> MainActivity, showRewardAd //  The rewarded ad wasn't loaded yet.")
                Toast.makeText(this, resources.getString(R.string.rallo_error), Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Log.e("mException", "에러발생 -> MainActivity, showRewardAd //  Exception : ${e.message}")
        }
    }
}