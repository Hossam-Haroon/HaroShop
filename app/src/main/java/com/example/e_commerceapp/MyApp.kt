package com.example.e_commerceapp

import android.app.Application
import com.parse.Parse
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId("ZRhEijPfiXgHyVPFeqjVdX2iG5c02j1aUkrJGcu8")
                .clientKey("1yhs0PmxpUJTStgZH9iW3f3q6tq6QexhW8VxWbiy")
                .server("https://parseapi.back4app.com")
                .build()
        )

        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51SMdhjIegfDNXp9q48hQrcd3TuJK9gpr3IRuLujYQiGrmBYEdLHxzXiWum3Zp2wKI8dNuXZagGMekIQNTNVsT7Yp003Qyzf9EH"
        )
    }
}