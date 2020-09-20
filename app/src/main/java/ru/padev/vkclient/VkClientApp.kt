package ru.padev.vkclient

import android.app.Application
import android.util.Log
import androidx.work.*
import com.facebook.stetho.Stetho
import com.vk.sdk.VKSdk
import ru.padev.vkclient.core.di.DaggerApplication
import ru.padev.vkclient.core.di.provider.CoreProvider
import ru.padev.vkclient.di.AppComponent
import ru.padev.vkclient.services.SyncFriendsWorker

class VkClientApp: Application(), DaggerApplication {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        buildDi()

        VKSdk.initialize(applicationContext)

        Stetho.initializeWithDefaults(this);

        updateFriends()
    }

    override fun getApplicationProvider(): CoreProvider {
        return appComponent
    }

    private fun buildDi() {
        appComponent = AppComponent.Builder.build(this)
    }

    private fun updateFriends() {
        val syncFriendsWorker = OneTimeWorkRequest.from(SyncFriendsWorker::class.java)

        val operation = WorkManager.getInstance(this)
            .enqueueUniqueWork(
                SyncFriendsWorker::class.java.name,
                ExistingWorkPolicy.KEEP,
                syncFriendsWorker)
            .result

        operation.addListener(
            { Log.d("Friends update", "update started")},
            { it.run() }
        )
    }
}