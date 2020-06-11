package com.example.kotlinproject.main.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinproject.MainActivity
import com.example.kotlinproject.R
import com.sunnyweather.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * @author zt
 * @date 2020/6/8 16:44
 * @description
 **/
class PlaceFragment : Fragment() {
    val viewModel by lazy {
        ViewModelProviders.of(this).get(PlaceViewModel::class.java)
    }
    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 对城市选择的存储与否进行判断和读取  注：因为若只判断是否保存过的话，会造成死循环，
        // 因为在PlaceAdapter中保存了之后，在这儿调用isPlaceSaved方法的时候，一定是true，则会死循环
        if (activity is MainActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng", place.location.lng)
                putExtra("location_lat", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter

        // 对城市搜索输入框进行监听，实时出现相关的城市名字
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(this, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何地点！", Toast.LENGTH_SHORT).show()
                // 不为空则调用printStackTrace()方法
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

}