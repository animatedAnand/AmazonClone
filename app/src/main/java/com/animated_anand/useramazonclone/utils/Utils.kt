package com.animated_anand.useramazonclone.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.animated_anand.useramazonclone.databinding.ProgressDialogBinding
import com.google.firebase.auth.FirebaseAuth

object Utils {

    private var progressDialog : AlertDialog? = null
    private var firebaseAuthInstance : FirebaseAuth? = null
    fun showToast(context : Context, message : String)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    fun showProgressDialog(context : Context,message : String)
    {
        var binding = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        binding.tvProgressDialog.text = message
        progressDialog = AlertDialog.Builder(context).setView(binding.root).setCancelable(false).create()
        progressDialog!!.show()
    }

    fun hideProgressDialog()
    {
        progressDialog?.dismiss()
    }

    fun getFirebaseAuthInstance() : FirebaseAuth
    {
        if(firebaseAuthInstance == null)
        {
            firebaseAuthInstance = FirebaseAuth.getInstance()
        }
        return firebaseAuthInstance!!
    }
}