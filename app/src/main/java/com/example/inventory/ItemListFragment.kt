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
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventory.databinding.ItemListFragmentBinding
import kotlinx.android.synthetic.main.item_list_fragment.*

/**
 * Main fragment displaying details for all items in the database.
 */
class ItemListFragment : Fragment() {

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    private var _binding: ItemListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ItemListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //點擊事件，畫面跳至Vocabulary Detail
        val adapter = ItemListAdapter {
            val action =   ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.floatingActionButton.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(
                getString(R.string.add_fragment_title)
            )
            this.findNavController().navigate(action)
        }
//        item_name.setOnClickListener {
//            makeNotification() }

    }

//    private fun makeNotification() {
//        val channelId="English"
//        val channelName="english"
//        val manager = notificationManager(channelId, channelName)
//        val builder =NotificationCompat.Builder(this)
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("Vocabulary")
//            .setContentText("This is English.")
//            .setSubText("This is info.")
//            .setWhen(System.currentTimeMillis())
//            .setChannelId(channelId)
//        manager.notify(1,builder.build())
//    }
//
//    private fun notificationManager(
//        channelId: String,
//        channelName: String
//    ): NotificationManager {
//        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        val channel =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
//            } else {
//                TODO("VERSION.SDK_INT < O")
//            }
//        manager.createNotificationChannel(channel)
//        return manager
//    }

}
