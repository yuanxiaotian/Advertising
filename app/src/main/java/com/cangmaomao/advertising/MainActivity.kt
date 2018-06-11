package com.cangmaomao.advertising

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatEditText
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.jzvd.JZVideoPlayer
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.maps.model.MarkerOptions
import com.cangmaomao.advertising.app.App
import com.cangmaomao.lib.utils.GlideImageLoader
import com.cangmaomao.lib.utils.e
import com.cangmaomao.lib.utils.shortToast
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), MainContract.MainView {

    private val TAG = MainActivity::class.java.simpleName

    //    private lateinit var userName = "ZHWL"
//    private lateinit var password = "123654"
    private lateinit var userName: String
    private lateinit var password: String

    private lateinit var queryTime: String

//    private val path = "http://xuli008.0324.bftii.com/time.mp4"

    private lateinit var presenter: MainContract.MainPresenter

    private lateinit var aMap: AMap

    private val timer = Timer()

    private val timer1 = Timer()

    private lateinit var timerTask: TimerTask

    private lateinit var timerTask1: TimerTask

    var i = 0

    private lateinit var preferences: SharedPreferences

    private lateinit var url: String

    var isFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as App
        if (app.isFlag) {
            //竖屏
            setContentView(R.layout.activity_main)
            setSupportActionBar(toolbar);
            bmapView.onCreate(savedInstanceState)
            aMap = bmapView.map
            aMap.uiSettings.setZoomInByScreenCenter(true)
            aMap.uiSettings.setLogoBottomMargin(-100)
            MainPresenter(this)
            timerTask1 = object : TimerTask() {
                override fun run() {
                    presenter.login(TAG)
                }
            }


            preferences = applicationContext.getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
            userName = preferences.getString("accout", "")
            password = preferences.getString("pwd", "")
            url = preferences.getString("url", "")

            if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("登录")
                val view = View.inflate(this, R.layout.activity_dialog, null)
                val accountId = view.findViewById<AppCompatEditText>(R.id.account)
                val pwdId = view.findViewById<AppCompatEditText>(R.id.pwd)
                dialog.setView(view)
                dialog.setCancelable(false)
                dialog.setPositiveButton("登录", DialogInterface.OnClickListener { dialogInterface: DialogInterface, v: Int ->
                    userName = accountId.text.toString()
                    password = pwdId.text.toString()
                    val edit = preferences.edit()
                    edit.putString("accout", userName)
                    edit.putString("pwd", password)
                    edit.apply()
                    timer1.schedule(timerTask1, 0, 7200 * 1000)
                })
                dialog.show()
            } else {
                timer1.schedule(timerTask1, 0, 7200 * 1000)
            }
            val window = this.windowManager
            val width = window.defaultDisplay.width
            val params = webView2.layoutParams
            params.height = width / 2
            webView2.layoutParams = params

            val params1 = webView3.layoutParams
            params.height = width / 2
            webView3.layoutParams = params1

            if (!TextUtils.isEmpty(url)) {
                initBannerAndVideo()
            }


        } else {
            setContentView(R.layout.activity_main_h)

            bmapView.onCreate(savedInstanceState)
            aMap = bmapView.map
            aMap.uiSettings.setZoomInByScreenCenter(true)
            aMap.uiSettings.setLogoBottomMargin(-100)
            MainPresenter(this)
            timerTask1 = object : TimerTask() {
                override fun run() {
                    presenter.login(TAG)
                }
            }
            timer1.schedule(timerTask1, 0, 7200 * 1000)

            val window = this.windowManager
            val height = window.defaultDisplay.height
            val params = webView2.layoutParams
            params.width = height / 2
            webView2.layoutParams = params

            val params1 = webView3.layoutParams
            params.height = height / 2
            webView3.layoutParams = params1

            if (!TextUtils.isEmpty(url)) {
                initBannerAndVideo()
            }
        }
    }

    override fun setPresenter(presenter: MainContract.MainPresenter) {
        this.presenter = presenter
    }

    override fun map(): Map<String, String> {
        queryTime = "${Date().time}"
        return mapOf(
                "loginType" to "sys_user",
                "module" to "freight",
                "username" to userName,
                "password" to password,
                "backUrl" to "/Map/MapArea/GetOrgTreeAjax"
        )
    }

    override fun fail(msg: String) {
        shortToast(this, "登录异常!")
    }

    override fun carId(id: String) {
        if (!isFlag) {
            timerTask = object : TimerTask() {
                override fun run() {
                    isFlag = true
                    presenter.carLocation(TAG, mapOf("_action" to "queryChangedFromCache",
                            "lastTime" to "0",
                            "queryList" to id))
                }
            }
        }
        timer.schedule(timerTask, 0, 30000);
    }


    override fun carInfo(carLocation: CarLocation) {
        aMap.clear()
        val carInfoList = carLocation.obj.data
        val builder = LatLngBounds.builder()
        for (car in carInfoList) {
            val icon = if (car.linkStatus == 0) R.drawable.hui else if (car.linkStatus == 1 && car.speed > 0) R.drawable.lv else R.drawable.van_icon
            builder.include(LatLng(car.lat, car.lon))
            val markerOption = MarkerOptions().position(LatLng(car.lat, car.lon))
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, icon)))
            aMap.addMarker(markerOption).setRotateAngleNotUpdate(360 - car.direction.toFloat())
        }
        val u = CameraUpdateFactory.newLatLngBounds(builder.build(), 100)
        aMap.animateCamera(u)
        aMap.moveCamera(u)


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        when (id) {
            R.id.manage -> {
                val view = View.inflate(this, R.layout.activity_dialog2, null);
                val mUrl = view.findViewById<AppCompatEditText>(R.id.url)
                AlertDialog.Builder(this).setTitle("Url设置").setView(view).setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface: DialogInterface, v: Int ->
                    val u = mUrl.text.toString()
                    url = "http://${u}.com/"
                    preferences.edit().putString("url", url).apply()
                    initBannerAndVideo()
                }).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun initBannerAndVideo() {
        val list: List<String> = listOf(
                "${url}1.jpg",
                "${url}2.jpg",
                "${url}3.jpg",
                "${url}4.jpg")
        webView2.setImageLoader(GlideImageLoader())
        webView2.setBannerAnimation(Transformer.DepthPage);
        webView2.setImages(list)
        webView2.setDelayTime(5000)
        webView2.setIndicatorGravity(BannerConfig.NOT_INDICATOR)
        webView2.start()

        webView3.setUp("${url}time.mp4", JZVideoPlayer.SCREEN_WINDOW_NORMAL, "旭利优卡")
        webView3.startVideo()
        webView3.setProgressListener {
            if (it == 99) {
                webView3.startVideo()
            }
        }
    }


    /**
     * 方法必须重写
     */
    override fun onResume() {
        super.onResume()
        bmapView.onResume()
    }

    /**
     * 方法必须重写
     */
    override fun onPause() {
        super.onPause()
        bmapView.onPause()
    }

    /**
     * 方法必须重写
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        bmapView.onSaveInstanceState(outState)
    }

    /**
     * 方法必须重写
     */
    override fun onDestroy() {
        super.onDestroy()
        bmapView.onDestroy()
    }


}

