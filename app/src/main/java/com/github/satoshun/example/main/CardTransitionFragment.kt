/*
 * Copyright 2019 The Android Open Source Project
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
package com.github.satoshun.example.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.satoshun.example.databinding.CardTransitionFragBinding


class CardTransitionFragment : Fragment() {
  private lateinit var binding: CardTransitionFragBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = CardTransitionFragBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    Glide
      .with(binding.image)
      .load("https://storage.googleapis.com/gweb-uniblog-publish-prod/images/Android_symbol_green_2.max-1500x1500.png")
      .into(binding.image)

    ViewCompat.setTransitionName(binding.card, "card")

    binding.card.setOnClickListener {
      findNavController().navigate(
        CardTransitionFragmentDirections.navCardToDest(),
        FragmentNavigatorExtras(
          binding.card to CardTransitionDestinationFragment.TRANSITION_NAME_BACKGROUND
        )
      )
    }
  }
}
