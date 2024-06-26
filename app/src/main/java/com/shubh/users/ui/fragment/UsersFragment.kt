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
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.shubh.users.R
import com.shubh.users.UserResponse
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
            when(it){
                is UserResponse.Error -> {
                    usersList.clear()
                    userAdapter.userList = usersList
                    userAdapter.notifyDataSetChanged()
                    binding.loadingTextView.visibility = View.GONE

                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG).setAction("Retry") {
                        userViewModel.getUsers()
                        binding.loadingTextView.visibility = View.VISIBLE
                    }.show()
                }
                is UserResponse.Loading -> binding.loadingTextView.visibility = View.VISIBLE
                is UserResponse.Success -> {
                    usersList.clear()
                    usersList = it.data!!.toMutableList()
                    binding.loadingTextView.visibility = View.GONE
                    binding.userRecyclerView.visibility = View.VISIBLE
                    userAdapter = UserAdapter(usersList, ({ position, sharedElement ->
                        val action =
                            UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(usersList[position])
                        val extras = FragmentNavigatorExtras(sharedElement to sharedElement.transitionName)
                        findNavController().navigate(action, extras)
                    }))
                    binding.userRecyclerView.adapter = userAdapter
                }
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val filteredList = usersList.filter { it.name.contains(query.trim(), true) }
                    userAdapter.userList = filteredList
                    userAdapter.onClick = ({ position, sharedElement ->
                        val action =
                            UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(
                                filteredList[position]
                            )
                        val extras =
                            FragmentNavigatorExtras(sharedElement to sharedElement.transitionName)
                        findNavController().navigate(action, extras)
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