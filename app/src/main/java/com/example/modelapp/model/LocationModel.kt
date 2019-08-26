package com.example.modelapp.model

import androidx.lifecycle.MutableLiveData
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.poi.*
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.example.mvvm.base.BaseViewModel
import com.example.mvvm.config.Config

class LocationModel : BaseViewModel() {

    private var poiSearch = PoiSearch.newInstance()
    private var suggestionSearch = SuggestionSearch.newInstance()
    private var locationClient = LocationClient(Config.getApplicationContext())
    private var locationOption = LocationClientOption()

    val locData = MutableLiveData<BDLocation>()
    val searchData = MutableLiveData<List<SuggestionResult.SuggestionInfo>>()
    val poiSearchData = MutableLiveData<List<PoiInfo>>()


    init {

        locationOption.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("gcj02")
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000)
        //可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true)
        locationOption.setIsNeedLocationDescribe(true)
        locationOption.setIgnoreKillProcess(true)
        locationOption.setIsNeedLocationDescribe(true)
        locationOption.setIsNeedLocationPoiList(true)
        locationOption.SetIgnoreCacheException(false)
        locationOption.isOpenGps = true
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode()
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.locOption = locationOption
        //开始定位
        locationClient.start()


        locationClient.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(location: BDLocation?) {
                locData.value = location
            }
        })

        suggestionSearch.setOnGetSuggestionResultListener { suggestionResult ->
            searchData.value = suggestionResult.allSuggestions
        }

        poiSearch.setOnGetPoiSearchResultListener(object : OnGetPoiSearchResultListener {
            override fun onGetPoiResult(result: PoiResult?) {
                poiSearchData.value = result?.allPoi
            }

            override fun onGetPoiIndoorResult(result: PoiIndoorResult?) {}
            override fun onGetPoiDetailResult(p0: PoiDetailResult?) {}
            override fun onGetPoiDetailResult(p0: PoiDetailSearchResult?) {}
        })
    }

    override fun onCleared() {
        poiSearch.destroy()
        suggestionSearch.destroy()
        locationClient.stop()
    }

    fun setLatLng(latLng: LatLng) {

    }
}
