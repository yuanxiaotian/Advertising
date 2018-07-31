package com.cangmaomao.advertising

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
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
import com.cangmaomao.lib.utils.AppNetworkMgr
import com.cangmaomao.lib.utils.GlideImageLoader
import com.cangmaomao.lib.utils.e
import com.cangmaomao.lib.utils.shortToast
import com.facebook.drawee.backends.pipeline.Fresco
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), MainContract.MainView {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var userName: String
    private lateinit var userName1: String

    private lateinit var queryTime: String

    private lateinit var presenter: MainContract.MainPresenter

    private lateinit var aMap: AMap

    private var timer = Timer()

    private val timer2 = Timer()

    private lateinit var timerTask: TimerTask

    private lateinit var timerTask2: TimerTask

    var i = 0

    private lateinit var preferences: SharedPreferences

    private lateinit var url: String

    var isFlag = false

    var lastTime = "0"

    private lateinit var dialog: AlertDialog

    val builder = LatLngBounds.builder()
    val absMarker = TreeMap<Int, Marker>()

    var s: Int = 0

    lateinit var info: ArrayList<FleetInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        bmapView.onCreate(savedInstanceState)

        aMap = bmapView.map
        aMap.isMyLocationEnabled = false
        aMap.uiSettings.setLogoBottomMargin(-100)
        aMap.uiSettings.isZoomControlsEnabled = false
        MainPresenter(this)
        dialog = AlertDialog.Builder(this@MainActivity).create()
        val view = View.inflate(this, R.layout.activity_wifi, null)
        dialog.setView(view)
        dialog.show()
        timerTask2 = object : TimerTask() {
            override fun run() {
                val isFlag = AppNetworkMgr.isWifiConnected(this@MainActivity)
                if (isFlag) {
                    runOnUiThread {
                        timer2.cancel()
                        timerTask2.cancel()
                        start()
                        dialog.dismiss()
                    }

                }
            }
        }
        timer2.schedule(timerTask2, 0, 2000)
    }

    fun start() {
        preferences = applicationContext.getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
        userName = preferences.getString("account", "")
        userName1 = preferences.getString("account1", "")
        url = preferences.getString("url", "")
        if (TextUtils.isEmpty(url)) {
            url = "xuli008.0324.bftii"
        }

        if (TextUtils.isEmpty(userName)) {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("账号设置")
            val view = View.inflate(this, R.layout.activity_dialog, null)
            val account = view.findViewById<AppCompatEditText>(R.id.account)
            val account1 = view.findViewById<AppCompatEditText>(R.id.account1)
            dialog.setView(view)
            dialog.setCancelable(false)
            dialog.setPositiveButton("确定", { dialogInterface: DialogInterface, i: Int ->
                userName = account.text.toString()
                userName1 = account1.text.toString()
                if (TextUtils.isEmpty(userName1)) {
                    startService(Intent(this, AbsService::class.java).putExtra("account", userName))
                } else {
                    startService(Intent(this, AbsService::class.java).putExtra("account", userName)
                            .putExtra("account1", userName1))
                }
            })
            dialog.show()
        } else {
            startService(Intent(this, AbsService::class.java).putExtra("account", userName)
                    .putExtra("account1", userName1))
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
//                "password" to password,
                "backUrl" to "/Map/MapArea/GetOrgTreeAjax"
        )
    }

    override fun fail(msg: String) {
        shortToast(this, "登录异常!")
    }

    override fun carId(id: String, info: ArrayList<FleetInfo>) {
        presenter.carLocation(TAG, mapOf("_action" to "queryChangedFromCache",
                "lastTime" to lastTime,
                "queryList" to id))
        this.info = info
    }

    @SuppressLint("SetTextI18n")
    override fun carInfo(carLocation: CarLocation) {
        val edit = preferences.edit()
        edit.putString("account", userName)
        edit.putString("account1", userName1)
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
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 60))
        tv_title.text = "合计:${absMarker.size}"
        tv_title1.text = "运行:$count"
        tv_title2.text = "停车:$pause"
        tv_title3.text = "离线:$stop"
        presenter.carInfo(info, carLocation)
    }

    override fun fleet(info: ArrayList<FleetInfo>) {
        try {
            lateinit var linearLayout: LinearLayout
            lateinit var itemView: View
            viewFlipper.removeAllViews()


            val list = ArrayList<MutableList<FleetInfo>>()


            var j = 0
            for ((k, i) in info.withIndex()) {
                if (k % 3 == 0) {
                    linearLayout = LinearLayout(this)
                    linearLayout.orientation = LinearLayout.VERTICAL
                    linearLayout.removeAllViews()
                }
                itemView = layoutInflater.inflate(R.layout.activity_view_car_item, null)
                itemView.findViewById<AppCompatTextView>(R.id.textView1).text = i.name
                itemView.findViewById<AppCompatTextView>(R.id.textView2).text = "${i.onLine}"
                itemView.findViewById<AppCompatTextView>(R.id.textView3).text = "${i.pause}"
                itemView.findViewById<AppCompatTextView>(R.id.textView4).text = "${i.offLine}"
                linearLayout.addView(itemView)
                if (info.size == 1 || (k > 0 && info.size <= 2) || (k > 0 && k % 3 == 2) || k == info.size - 1) {
                    linearLayout.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (if (info.size <= 3) itemView.layoutParams.height * info.size else itemView.layoutParams.height * 3))
                    viewFlipper.addView(linearLayout)
                }
            }
            val p = viewFlipper.layoutParams
            p.height = if (info.size <= 3) itemView.layoutParams.height * info.size else itemView.layoutParams.height * 3
            viewFlipper.layoutParams = p

            if (info.size <= 3) {
                viewFlipper.stopFlipping()
            } else {
                viewFlipper.startFlipping()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        when (id) {
            R.id.manage -> {
                val view = View.inflate(this, R.layout.activity_dialog2, null)
                val mUrl = view.findViewById<AppCompatEditText>(R.id.url)
                AlertDialog.Builder(this).setTitle("Url设置").setView(view).setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface: DialogInterface, v: Int ->
                    val u = mUrl.text.toString()
                    url = "http://${u}.com/"
                    preferences.edit().putString("url", url).apply()
                    initBannerAndVideo()
                }).show()
            }
            R.id.acc -> {
                //跳转到开启智能安装服务的界面
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent)
            }

            R.id.videos -> {
                val view = View.inflate(this, R.layout.activity_dialog2, null)
                val name = view.findViewById<AppCompatEditText>(R.id.url)
                name.setText("")
                AlertDialog.Builder(this).setTitle("视频名称").setView(view).setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface: DialogInterface, v: Int ->
                    webView3.setUp("$url${name.text}.mp4", JZVideoPlayer.SCREEN_WINDOW_NORMAL, "旭利")
                    webView3.startVideo()
                    webView3.setProgressListener {
                        e("进度:$it")
                        if (it == 99) {
                            webView3.initTextureView()
                            webView3.addTextureView()
                            JZMediaManager.setDataSource(webView3.dataSourceObjects)
                            JZMediaManager.setCurrentDataSource(JZUtils.getCurrentFromDataSource(webView3.dataSourceObjects, webView3.currentUrlMapIndex))
                            webView3.onStatePreparing()
                            webView3.onEvent(JZUserAction.ON_CLICK_START_ERROR)
                        }
                    }
                }).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun initBannerAndVideo() {

        val count = intent.getIntExtra("count", 0)
        val list = arrayListOf<String>()
        for (i in 0 until count) {
            list.add("$url$i.jpg")
        }
        val imagePipe = Fresco.getImagePipeline()
        imagePipe.clearCaches()

        webView2.setImageLoader(GlideImageLoader())
        webView2.update(list)
        webView2.setDelayTime(6000)
        webView2.setBannerStyle(BannerConfig.NOT_INDICATOR)
        webView2.start()

        webView3.setUp("${url}time.mp4", JZVideoPlayer.SCREEN_WINDOW_NORMAL, "旭利")
        webView3.startVideo()
        webView3.setProgressListener {
            e("进度:$it")
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MainEvent) {
        if (event.flag) {
            if (!isFlag) {
                timerTask = object : TimerTask() {
                    override fun run() {
                        isFlag = true
                        presenter.getOrgTreeAjax(TAG)
                    }
                }
                timer.schedule(timerTask, 0, 1000 * 60)
            }
        } else {
            val uri = Uri.fromFile(File(Environment.getExternalStorageDirectory().absolutePath + "/v.apk"))
            val localIntent = Intent(Intent.ACTION_VIEW)
            localIntent.setDataAndType(uri, "application/vnd.android.package-archive")
            startActivity(localIntent)
        }
    }


    /**
     * 方法必须重写
     */
    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
        bmapView.onResume()
    }

    /**
     * 方法必须重写
     */
    override fun onPause() {
        super.onPause()
        bmapView.onPause()
        JZVideoPlayerStandard.releaseAllVideos()

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
        EventBus.getDefault().unregister(this)
    }


}

