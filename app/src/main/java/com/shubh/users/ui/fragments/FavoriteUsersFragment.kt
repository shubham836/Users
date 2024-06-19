package com.shubh.users.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shubh.users.databinding.FragmentFavoriteUsersBinding


class FavoriteUsersFragment : Fragment() {

    private var _binding: FragmentFavoriteUsersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteUsersBinding.inflate(inflater, container, false)
        return binding.root
    }
}