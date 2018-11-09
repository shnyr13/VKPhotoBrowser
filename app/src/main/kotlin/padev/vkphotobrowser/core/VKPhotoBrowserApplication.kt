package padev.vkphotobrowser.core

import android.app.Application
import com.vk.sdk.VKSdk

class VKPhotoBrowserApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        VKSdk.initialize(applicationContext)
    }
}