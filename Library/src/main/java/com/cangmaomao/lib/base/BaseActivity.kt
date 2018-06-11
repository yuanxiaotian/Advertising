package com.cangmaomao.lib.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.cangmaomao.lib.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layViewId())
        initView()

    }

    //布局ID
    abstract fun layViewId(): Int

    //初始化
    abstract fun initView()

    //fragment
    abstract fun addViewId(): Int

    fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(addViewId(), fragment).commitAllowingStateLoss()
    }




}