package com.shubh.users.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shubh.users.databinding.FragmentUserDetailBinding
import com.shubh.users.db.UserEntity
import com.shubh.users.model.Address
import com.shubh.users.ui.viewmodel.UserViewModel

class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private var currentUser: UserEntity? = null
    private val args: UserDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        val user = args.user
        userViewModel.getUserById(user.id)
        binding.nameTextView.text = user.name
        binding.emailTextView.text = user.email
        binding.phoneTextView.text = user.phone
        binding.suiteTextView.text = user.address.suite + ", " + user.address.street
        binding.cityTextView.text = user.address.city + ", " + user.address.zipcode

        userViewModel.userEntity.observe(viewLifecycleOwner, Observer { userEntity ->
            if (userEntity == null)
                binding.addFavoriteButton.visibility = View.VISIBLE
            else {
                currentUser = userEntity
                if (!userEntity.isFavorite) {
                    binding.addFavoriteButton.visibility = View.VISIBLE
                } else {
                    binding.addFavoriteButton.visibility = View.GONE
                }
                if (binding.addressViewgroup.isVisible) {
                    binding.suiteTextView.text =
                        "${userEntity.address.suite}, ${userEntity.address.street}"
                    binding.cityTextView.text =
                        "${userEntity.address.city}, ${userEntity.address.zipcode}"
                }
            }
        })
        binding.editButton.setOnClickListener {
            if (binding.editButton.text == "Edit Address") {
                binding.addressViewgroup.visibility = View.GONE
                binding.addressEditGroup.visibility = View.VISIBLE
                binding.editButton.text = "Submit"
            } else {
                if (binding.suiteEditText.text?.split(",")?.size == 2) {
                    val suite = binding.suiteEditText.text!!.split(",").first().trim()
                    val street = binding.suiteEditText.text!!.split(",").last().trim()
                    if (binding.cityEditText.text.isNullOrBlank() || binding.zipEditText.text.isNullOrBlank()) {
                        Toast.makeText(context, "Fill all the fields", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    val city = binding.cityEditText.text!!.toString().trim()
                    val zipCode = binding.zipEditText.text!!.toString().trim()
                    if (currentUser != null) {
                        currentUser = UserEntity(
                            user.id,
                            user.name,
                            user.email,
                            user.phone,
                            Address(suite, street, city, zipCode),
                            user.company,
                            currentUser!!.isFavorite,
                            true
                        )
                        userViewModel.updateUser(
                            currentUser!!
                        )
                    } else {
                        currentUser = UserEntity(
                            user.id,
                            user.name,
                            user.email,
                            user.phone,
                            Address(suite, street, city, zipCode),
                            user.company,
                            false,
                            true
                        )
                        userViewModel.addUser(
                            currentUser!!
                        )
                    }

                    findNavController().popBackStack()
                } else {
                    Toast.makeText(
                        context,
                        "Address field must contain Suite and Street",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.addFavoriteButton.setOnClickListener {
            if (currentUser == null) {
                userViewModel.addUser(
                    UserEntity(
                        user.id,
                        user.name,
                        user.email,
                        user.phone,
                        user.address,
                        user.company,
                        true,
                        false
                    )
                )
                return@setOnClickListener
            }
            userViewModel.updateUser(currentUser!!.copy(isFavorite = true))
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}