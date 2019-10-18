package com.github.satoshun.example.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.satoshun.example.R
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

    binding.container.setOnClickListener {
      findNavController().navigate(R.id.nav_card_dest)
    }
  }
}
