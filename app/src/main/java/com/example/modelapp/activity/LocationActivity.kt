package com.example.modelapp.activity

import android.os.Bundle
import android.util.Log
import android.view.textservice.SuggestionsInfo
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.baidu.location.*
import com.baidu.mapapi.map.*
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.poi.*
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.example.modelapp.R
import com.example.modelapp.adapter.LocationAdapter
import com.example.modelapp.model.LocationModel
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_location.*
import com.example.mvvm.config.Config.getApplicationContext
import com.miguelcatalan.materialsearchview.MaterialSearchView
import java.util.ArrayList


class LocationActivity : BaseActivity<LocationModel>() {

    lateinit var map: BaiduMap
    private val adapter = LocationAdapter()

    override fun getViewModel() = ViewModelProviders.of(this).get(LocationModel::class.java)

    override fun getContentView() = R.layout.activity_location

    override fun initParam(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        map = mapView.map
        map.mapType = BaiduMap.MAP_TYPE_NORMAL
        map.isMyLocationEnabled = true

        val mUiSettings = map.uiSettings
        mUiSettings.isOverlookingGesturesEnabled = false
        mUiSettings.isRotateGesturesEnabled = false

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayout.HORIZONTAL))
        recyclerView.adapter = adapter
    }

    override fun initEvent() {
        map.setOnMapClickListener(object : OnMapClickListener {
            override fun onMapClick(latLng: LatLng) {
                viewModel.setLatLng(latLng)
                targetPoint(latLng)
            }

            override fun onMapPoiClick(mapPoi: MapPoi) = false
        })

        adapter.setOnItemOnClickListener {

        }

        searchView.setOnQueryTextListener(object :MaterialSearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                return false
            }

        })

        viewModel.locData.observe(this, Observer {
            val latLng = LatLng(it.latitude, it.longitude)
            moveTo(latLng)

            val locData = MyLocationData.Builder()
                    .accuracy(1000f)
                    .direction(100f)
                    .latitude(it.latitude)
                    .longitude(it.longitude)
                    .build()
            map.setMyLocationData(locData)
        })

        viewModel.searchData.observe(this, Observer {
            
        })

        viewModel.poiSearchData.observe(this, Observer {
            adapter.data.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun targetPoint(latLng: LatLng) {
        map.clear()
        val icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_locate_tiny)
        val ooA = MarkerOptions().position(latLng).icon(icon)
        map.addOverlay(ooA)
    }

    private fun moveTo(latLng: LatLng) {
        val sta = MapStatus.Builder()
                .target(latLng)
                .zoom(15f)
                .build()
        val u = MapStatusUpdateFactory.newMapStatus(sta)
        map.animateMapStatus(u)
    }
}

