package com.shubh.users.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.shubh.users.databinding.FragmentFavoriteUsersBinding
import com.shubh.users.model.User
import com.shubh.users.ui.adapter.UserAdapter
import com.shubh.users.ui.viewmodel.UserViewModel


class FavoriteUsersFragment : Fragment() {

    private var _binding: FragmentFavoriteUsersBinding? = null
    private val binding get() = _binding!!
    private lateinit var userAdapter: UserAdapter
    private lateinit var userViewModel: UserViewModel
    private var favoriteUsersList = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        binding.favoriteUsersRecyclerView.layoutManager = LinearLayoutManager(context)
        userViewModel.getFavoriteUsers()

        userViewModel.favoriteUserList.observe(viewLifecycleOwner, Observer { userEntityList ->
            favoriteUsersList.clear()
            userEntityList.filter { it.isFavorite }.forEach {
                favoriteUsersList.add(
                    User(
                        it.id,
                        it.name,
                        it.email,
                        it.phone,
                        it.address,
                        it.companyName
                    )
                )
            }
            userAdapter = UserAdapter(favoriteUsersList, { position,sharedElement ->
                val action =
                    FavoriteUsersFragmentDirections.actionFavoriteUsersFragmentToUserDetailFragment(
                        favoriteUsersList[position]
                    )
                val extras = FragmentNavigatorExtras(sharedElement to sharedElement.transitionName)
                findNavController().navigate(action,extras)
            }) { position ->
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Remove from Favorites")
                    .setMessage("Do you want to remove ${userEntityList[position].name} from your favorite?")
                    .setNegativeButton("Cancel") { dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("Remove") { dialog, which ->
                        userViewModel.removeUser(userEntityList[position])
                        favoriteUsersList.removeAt(position)
                        userAdapter.notifyItemRemoved(position)
                    }
                    .show()
            }
            binding.favoriteUsersRecyclerView.adapter = userAdapter
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}