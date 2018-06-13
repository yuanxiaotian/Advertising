package com.cangmaomao.advertising

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import cn.jzvd.*
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.cangmaomao.advertising.app.App
import com.cangmaomao.lib.utils.GlideImageLoader
import com.cangmaomao.lib.utils.e
import com.cangmaomao.lib.utils.longToast
import com.cangmaomao.lib.utils.shortToast
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


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

    var lastTime = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as App
        if (app.isFlag) {
            //竖屏
            setContentView(R.layout.activity_main)
            setSupportActionBar(toolbar);
            bmapView.onCreate(savedInstanceState)
            aMap = bmapView.map
            aMap.isMyLocationEnabled = false
            aMap.uiSettings.setLogoBottomMargin(-100)
            aMap.uiSettings.isZoomControlsEnabled = false
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
                    timer1.schedule(timerTask1, 0, 7200 * 1000)
                })
                dialog.show()
            } else {
                timer1.schedule(timerTask1, 0, 7200 * 1000)
            }

            cardView.radius = 10f
            cardView.cardElevation = 10f


            cardView1.radius = 10f
            cardView1.cardElevation = 10f

            val window = this.windowManager
            val width = window.defaultDisplay.width
            val params = cardView.layoutParams
            params.height = width / 2
            cardView.layoutParams = params

            val params1 = cardView1.layoutParams
            params.height = width / 2
            cardView1.layoutParams = params1

            if (!TextUtils.isEmpty(url)) {
                initBannerAndVideo()
            }


        } else {
            setContentView(R.layout.activity_main_h)
            bmapView.onCreate(savedInstanceState)
            aMap = bmapView.map
            aMap.isMyLocationEnabled = false
            aMap.uiSettings.setLogoBottomMargin(-100)
            aMap.uiSettings.isZoomControlsEnabled = false
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

    lateinit var info: ArrayList<FleetInfo>
    override fun carId(id: String, info: ArrayList<FleetInfo>) {
        if (!isFlag) {
            timerTask = object : TimerTask() {
                override fun run() {
                    isFlag = true
                    presenter.carLocation(TAG, mapOf("_action" to "queryChangedFromCache",
                            "lastTime" to lastTime,
                            "queryList" to id))
                }
            }
        }
        timer.schedule(timerTask, 0, 1000 * 40);
        this.info = info
    }

    val builder = LatLngBounds.builder()
    val absMarker = TreeMap<Int, Marker>()
    @SuppressLint("SetTextI18n")
    override fun carInfo(carLocation: CarLocation) {
        val edit = preferences.edit()
        edit.putString("accout", userName)
        edit.putString("pwd", password)
        edit.apply()
        var count = 0
        var stop = 0
        var pause = 0
        val carInfoList = carLocation.obj.data
        val markers = aMap.mapScreenMarkers
        for (car in carInfoList) {
            val latLng = LatLng(car.lat, car.lon)
            val icon = if (car.linkStatus == 0) R.drawable.hui else if (car.linkStatus == 1 && car.speed > 0) R.drawable.lv else R.drawable.van_icon
            if (absMarker.size <= 0) {
                val markerOption = MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, icon)))
                val marker = aMap.addMarker(markerOption)
                marker.rotateAngle = 360 - car.direction.toFloat()
                marker.options.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, icon)))
                absMarker[car.vehicleID] = marker
                builder.include(latLng)
            } else {
                val m = absMarker[car.vehicleID]
                if (m != null) {
                    for (marker in markers) {
                        if (m.id.equals(marker.id)) {
                            val markerOption = MarkerOptions().position(latLng)
                                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, icon)))
                            val a = aMap.addMarker(markerOption)
                            marker.remove()
                            a.rotateAngle = 360 - car.direction.toFloat()
                            absMarker[car.vehicleID] = a
                            builder.include(latLng)
                        }
                    }
                } else {
                    val markerOption = MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, icon)))
                    val marker = aMap.addMarker(markerOption)
                    marker.rotateAngle = 360 - car.direction.toFloat()
                    absMarker[car.vehicleID] = marker
                    builder.include(latLng)
                }
            }

            if (car.linkStatus == 0) {
                stop++
            }

            if (car.linkStatus == 1 && car.speed > 0) {
                count++
            }

            if (car.linkStatus == 1 && car.speed <= 0) {
                pause++
            }
        }
        e("${markers.size}b")
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 80))
        tv_title.text = "合计:${absMarker.size}"
        tv_title1.text = "运行:${count}"
        tv_title2.text = "停车:${pause}"
        tv_title3.text = "离线:${stop}"
        presenter.carInfo(info, carLocation)

    }

    override fun fleet(info: ArrayList<FleetInfo>) {
        var k = 0
        lateinit var view: View
        lateinit var linearLayout: LinearLayout
        for (i in info) {
            if (k % 3 == 0) {
                linearLayout = LinearLayout(this)
                linearLayout.orientation = LinearLayout.VERTICAL
                linearLayout.removeAllViews()
            }
            val itemView = layoutInflater.inflate(R.layout.activity_view_car_item, null)
            itemView.findViewById<AppCompatTextView>(R.id.textView1).text = i.name
            itemView.findViewById<AppCompatTextView>(R.id.textView2).text = "     ${i.onLine}"
            itemView.findViewById<AppCompatTextView>(R.id.textView3).text = "     ${i.pause}"
            itemView.findViewById<AppCompatTextView>(R.id.textView4).text = "     ${i.offLine}"
            linearLayout.addView(itemView)
            if ((info.size <= 2 && k % 3 == 1) || k % 3 == 2) {
                linearLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (if (info.size <= 3) itemView.layoutParams.height * info.size else itemView.layoutParams.height * 3))
                viewFlipper.addView(linearLayout)
            }
            k++
        }
        val p = viewFlipper.layoutParams
        p.height = linearLayout.layoutParams.height
        viewFlipper.layoutParams = p

        if (info.size < 3) {
            viewFlipper.stopFlipping()
        }
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
//                    url = "http://xuli008.0324.bftii.com/"
                    preferences.edit().putString("url", url).apply()
                    initBannerAndVideo()
                }).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun initBannerAndVideo() {
        val list: List<String> = listOf(
                "${url}5.jpg",
                "${url}5.jpg",
                "${url}11.jpg",
                "${url}12.jpg",
                "${url}5.jpg"
        )
        webView2.setImageLoader(GlideImageLoader())
        webView2.setBannerAnimation(Transformer.DepthPage);
        webView2.update(list)
        webView2.setDelayTime(5000)
        webView2.setIndicatorGravity(BannerConfig.NOT_INDICATOR)
        webView2.start()

        webView3.setUp("${url}time.mp4", JZVideoPlayer.SCREEN_WINDOW_NORMAL, "旭利")
        webView3.startVideo()
        webView3.setProgressListener {
            if (it == 99) {
                webView3.initTextureView()
                webView3.addTextureView()
                JZMediaManager.setDataSource(webView3.dataSourceObjects)
                JZMediaManager.setCurrentDataSource(JZUtils.getCurrentFromDataSource(webView3.dataSourceObjects, webView3.currentUrlMapIndex))
                webView3.onStatePreparing()
                webView3.onEvent(JZUserAction.ON_CLICK_START_ERROR)
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

