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
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeTransform
import androidx.transition.Transition
import androidx.transition.TransitionSet
import com.github.satoshun.example.R
import com.github.satoshun.example.databinding.CardTransitionDestinationFragBinding

class CardTransitionDestinationFragment : Fragment() {
  companion object {
    const val TRANSITION_NAME_BACKGROUND = "background"
    const val TRANSITION_NAME_CARD_CONTENT = "card_content"
    const val TRANSITION_NAME_ARTICLE_CONTENT = "article_content"
  }

  private lateinit var binding: CardTransitionDestinationFragBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // These are the shared element transitions.
    sharedElementEnterTransition =
      createSharedElementTransition(TRANSITION_DURATION, R.id.article_mirror)
    sharedElementReturnTransition =
      createSharedElementTransition(TRANSITION_DURATION, R.id.card_mirror)
  }

  private fun createSharedElementTransition(duration: Long, @IdRes noTransform: Int): Transition {
    return transitionTogether {
      this.duration = duration
      interpolator = FAST_OUT_SLOW_IN
      this += SharedFade()
      this += ChangeBounds()
      this += ChangeTransform()
        // The content is already transformed along with the parent. Exclude it.
        .excludeTarget(noTransform, true)
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = CardTransitionDestinationFragBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    ViewCompat.setTransitionName(binding.background, TRANSITION_NAME_BACKGROUND)
    ViewCompat.setTransitionName(binding.coordinator, TRANSITION_NAME_ARTICLE_CONTENT)
    ViewCompat.setTransitionName(binding.cardMirror, TRANSITION_NAME_CARD_CONTENT)
    ViewGroupCompat.setTransitionGroup(binding.coordinator, true)

//    viewLifecycleOwner.lifecycleScope.launch {
//      binding.image.setBackgroundResource(R.drawable.ic_launcher_background)
//    }

//    Glide
//      .with(binding.image)
//      .load("https://storage.googleapis.com/gweb-uniblog-publish-prod/images/Android_symbol_green_2.max-1500x1500.png")
//      .into(binding.image)
  }
}

val FAST_OUT_SLOW_IN: TimeInterpolator by lazy(LazyThreadSafetyMode.NONE) {
  PathInterpolatorCompat.create(0.4f, 0f, 0.2f, 1f)
}

inline fun transitionTogether(crossinline body: TransitionSet.() -> Unit): TransitionSet {
  return TransitionSet().apply {
    ordering = TransitionSet.ORDERING_TOGETHER
    body()
  }
}

operator fun TransitionSet.plusAssign(transition: Transition) {
  addTransition(transition)
}
