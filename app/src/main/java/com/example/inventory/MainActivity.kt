/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.inventory

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import kotlinx.android.synthetic.main.item_list_fragment.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve NavController from the NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        // Set up the action bar for use with the NavController
        setupActionBarWithNavController(this, navController)


//        setContentView(R.layout.item_list_fragment)
//        item_name.setOnClickListener {
//            makeNotification() }
    }

    private fun makeNotification() {
        val channelId="English"
        val channelName="english"
        val manager = notificationManager(channelId, channelName)
        val builder =NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Vocabulary")
            .setContentText("This is English.")
            .setSubText("This is info.")
            .setWhen(System.currentTimeMillis())
            .setChannelId(channelId)
        manager.notify(1,builder.build())
    }

    private fun notificationManager(
        channelId: String,
        channelName: String
    ): NotificationManager {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        manager.createNotificationChannel(channel)
        return manager
    }

    /**
     * Handle navigation when the user chooses Up from the action bar.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}
