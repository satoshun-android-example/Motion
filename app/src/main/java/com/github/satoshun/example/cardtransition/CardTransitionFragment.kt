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
package com.github.satoshun.example.cardtransition

import android.animation.TimeInterpolator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import com.github.satoshun.example.databinding.CardTransitionFragBinding


class CardTransitionFragment : Fragment() {
  private lateinit var binding: CardTransitionFragBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    exitTransition = Fade(Fade.OUT).apply {
      duration = TRANSITION_DURATION / 2
      interpolator = FAST_OUT_LINEAR_IN
    }

    reenterTransition = Fade(Fade.IN).apply {
      duration = TRANSITION_DURATION / 2
      startDelay = TRANSITION_DURATION / 2
      interpolator = LINEAR_OUT_SLOW_IN
    }
  }

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

//    Glide
//      .with(binding.image)
//      .load("https://storage.googleapis.com/gweb-uniblog-publish-prod/images/Android_symbol_green_2.max-1500x1500.png")
//      .into(binding.image)

    ViewCompat.setTransitionName(binding.card, "card")
    ViewCompat.setTransitionName(binding.cardContent, "card_content")
    ViewCompat.setTransitionName(binding.articleMirror, "article")
    ViewGroupCompat.setTransitionGroup(binding.cardContent, true)

    binding.card.setOnClickListener {
      findNavController().navigate(
        CardTransitionFragmentDirections.navCardToDest(),
        FragmentNavigatorExtras(
          binding.card to CardTransitionDestinationFragment.TRANSITION_NAME_BACKGROUND,
          binding.cardContent to CardTransitionDestinationFragment.TRANSITION_NAME_CARD_CONTENT,
          binding.articleMirror to CardTransitionDestinationFragment.TRANSITION_NAME_ARTICLE_CONTENT
        )
      )
    }
  }
}

val FAST_OUT_LINEAR_IN: TimeInterpolator by lazy(LazyThreadSafetyMode.NONE) {
  PathInterpolatorCompat.create(0.4f, 0f, 1f, 1f)
}

val LINEAR_OUT_SLOW_IN: TimeInterpolator by lazy(LazyThreadSafetyMode.NONE) {
  PathInterpolatorCompat.create(0f, 0f, 0.2f, 1f)
}
