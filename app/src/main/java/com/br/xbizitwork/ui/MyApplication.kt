package com.br.xbizitwork.ui

import android.app.Application
import com.br.xbizitwork.core.util.logging.DebugTree
import com.example.xbizitwork.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.Forest.plant(DebugTree())
        }
    }
}