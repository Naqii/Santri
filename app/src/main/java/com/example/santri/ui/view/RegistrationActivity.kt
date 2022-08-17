package com.example.santri.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.santri.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        binding.btnSign.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        val email = binding.email.text.toString()
        val pass = binding.password.text.toString()
        val confirmPass = binding.confirmPassword.text.toString()

        if (email.isBlank() || pass.isBlank() || confirmPass.isBlank()) {
            Toast.makeText(this, "Email and Password can not be blank", Toast.LENGTH_SHORT).show()
            return
        } else if (pass != confirmPass) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                showLoading(true)
                finish()
            } else {
                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }
}