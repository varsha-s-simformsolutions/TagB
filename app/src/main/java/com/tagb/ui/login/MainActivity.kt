package com.tagb.ui.login

import android.annotation.SuppressLint
import android.os.Build
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.tagb.R
import com.tagb.base.BaseAppCompatActivity
import com.tagb.base.BaseViewModel
import com.tagb.data.model.response.BaseResponse
import com.tagb.data.model.response.LoginResponse
import com.tagb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseAppCompatActivity<ActivityMainBinding, LoginViewModel>(), View.OnClickListener {

    override val viewModel: LoginViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_main

    private var isPasswordVisible = false

    override fun initialize() {
        super.initialize()
        binding.apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
            clickHandler = this@MainActivity
        }
    }

    override fun initializeObservers(viewModel: LoginViewModel) {
        super.initializeObservers(viewModel)
        viewModel.validationLiveData.observe(this, { errorMessage ->
            errorMessage.run {
                when {
                    emptyEmail != 0 -> {
                        Toast.makeText(applicationContext, R.string.message_null_email, Toast.LENGTH_LONG).show()
                    }
                    invalidEmail != 0 -> {
                        Toast.makeText(applicationContext, R.string.message_invalid_email, Toast.LENGTH_LONG).show()
                    }
                    emptyPassword != 0 -> {
                        Toast.makeText(applicationContext, R.string.message_null_password, Toast.LENGTH_LONG).show()
                    }

                }
            }
        })

        viewModel.status.observe(this, Observer { status ->
            status?.let {
                if (status == true) {
                    Toast.makeText(this, R.string.message_login_success, Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(this, R.string.message_login_unsuccess, Toast.LENGTH_LONG).show()
                }
                viewModel.status.value = null
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                validation()
                viewModel.callLoginAPI()
            }
            R.id.btn_show_password -> {
                handleShowPassword()
            }
        }
    }

    private fun validation() {
        if (!viewModel.isUserInputValid()) {
            return
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun handleShowPassword() {
        if (isPasswordVisible) {
            binding.apply {
                editPasswordLogin.transformationMethod = PasswordTransformationMethod()
                btnShowPassword.setImageDrawable(getDrawable(R.drawable.ic_icon_hide_password))
            }
            isPasswordVisible = false
        } else {
            binding.apply {
                editPasswordLogin.transformationMethod = null
                btnShowPassword.setImageDrawable(getDrawable(R.drawable.ic_icon_show_password))
            }
            isPasswordVisible = true
        }
    }
}
