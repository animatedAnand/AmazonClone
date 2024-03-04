package com.animated_anand.useramazonclone.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.animated_anand.useramazonclone.R
import com.animated_anand.useramazonclone.databinding.FragmentOTPBinding
import com.animated_anand.useramazonclone.databinding.FragmentSignInBinding
import com.animated_anand.useramazonclone.utils.Utils
import com.animated_anand.useramazonclone.viewmodels.AuthViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OTPFragment : Fragment() {

    private lateinit var userNumber :String
    private lateinit var binding: FragmentOTPBinding
    private val viewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOTPBinding.inflate(layoutInflater)
        getUserNumber()
        customizingEnteringOtp()
        sendOTP()
        onBackButtonClicked()
        return binding.root
    }

    private fun sendOTP() {
        Utils.showProgressDialog(requireContext(),"Sending OTP..")
        viewModel.sendOTP(userNumber,requireActivity())
        viewModel.apply {
            lifecycleScope.launch {
                otpSent.collect {
                    if(it)
                    {
                        Utils.hideProgressDialog()
                        Utils.showToast(requireContext(),"OTP Sent")
                    }
                }
            }
        }
    }

    private fun onBackButtonClicked() {
        binding.tbOtpFragment.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_OTPFragment_to_signInFragment)
        }
    }

    private fun customizingEnteringOtp() {
        val listOtpEditTexts = arrayOf(binding.etOtp0,binding.etOtp1,binding.etOtp2,binding.etOtp3,binding.etOtp4,binding.etOtp5)
        for(i in listOtpEditTexts.indices)
        {
            listOtpEditTexts[i].addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if(s?.length == 1 && i<5)
                    {
                        listOtpEditTexts[i+1].requestFocus()
                    }
                    else if(s?.length == 0 && i>0)
                    {
                        listOtpEditTexts[i-1].requestFocus()
                    }
                }

            })

        }
    }

    private fun getUserNumber() {
        val bundle = arguments
        userNumber = bundle?.getString("number").toString()
        binding.tvUserNumber.text = "+91 "+ userNumber
    }


}