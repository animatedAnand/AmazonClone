package com.animated_anand.useramazonclone.viewmodels

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.animated_anand.useramazonclone.utils.Utils
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {

    private val _verificationID = MutableStateFlow<String?>(null)
    private val _otp = MutableStateFlow(false)
    val otpSent = _otp

    fun sendOTP(phoneNumber : String, activity :Activity)
    {
        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                TODO("Not yet implemented")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                TODO("Not yet implemented")
            }

            override fun onCodeSent(verificationID : String, token : PhoneAuthProvider.ForceResendingToken)
            {
                _verificationID.value = verificationID
                otpSent.value = true
            }

        }

        val options = PhoneAuthOptions.newBuilder(Utils.getFirebaseAuthInstance())
            .setPhoneNumber("+91$phoneNumber")
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}