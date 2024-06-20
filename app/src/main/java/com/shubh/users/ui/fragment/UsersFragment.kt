package com.shubh.users.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubh.users.R
import com.shubh.users.databinding.FragmentUsersBinding
import com.shubh.users.model.User
import com.shubh.users.ui.adapter.UserAdapter
import com.shubh.users.ui.viewmodel.UserViewModel

private const val TAG = "UsersFragment"

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private lateinit var userAdapter: UserAdapter
    private lateinit var userViewModel: UserViewModel
    private var usersList = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.searchView.setQuery("", false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.getUsers()

        binding.userRecyclerView.layoutManager = LinearLayoutManager(context)
        userViewModel.userList.observe(viewLifecycleOwner, Observer {
            usersList.clear()
            usersList = it.toMutableList()
            binding.loadingTextView.visibility = View.GONE
            binding.userRecyclerView.visibility = View.VISIBLE
            userAdapter = UserAdapter(it, ({ position ->
                val action =
                    UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(it[position])
                findNavController().navigate(action)
            }))
            binding.userRecyclerView.adapter = userAdapter
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val filteredList = usersList.filter { it.name.contains(query.trim(), true) }
                    userAdapter.userList = filteredList
                    userAdapter.onClick = ({ position ->
                        val action =
                            UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(
                                filteredList[position]
                            )
                        findNavController().navigate(action)
                    })
                    userAdapter.notifyDataSetChanged()
                } else {
                    userAdapter.userList = usersList
                    userAdapter.notifyDataSetChanged()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == null || newText == "") {
                    if (usersList.isNotEmpty()) {
                        userAdapter.userList = usersList
                        userAdapter.notifyDataSetChanged()
                    }
                }
                return true
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}