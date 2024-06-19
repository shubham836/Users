package com.shubh.users.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubh.users.R
import com.shubh.users.databinding.FragmentUsersBinding
import com.shubh.users.ui.adapter.UserAdapter
import com.shubh.users.ui.viewmodel.UserViewModel

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private lateinit var userAdapter: UserAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.getUsers()

        binding.userRecyclerView.layoutManager = LinearLayoutManager(context)
        userViewModel.userList.observe(viewLifecycleOwner, Observer {
            userAdapter = UserAdapter(it, ({ position ->
                val action =
                    UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(it[position])
                findNavController().navigate(action)
            }))
            binding.userRecyclerView.adapter = userAdapter
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}