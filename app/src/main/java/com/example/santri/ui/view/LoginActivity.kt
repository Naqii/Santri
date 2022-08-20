package com.example.santri.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.santri.R
import com.example.santri.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.tvRedirectSignUp.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
            showLoading(true)
        }
        showLoading(false)
    }

    private fun login() {
        val email = binding.inputEmail.text.toString()
        val pass = binding.inputPass.text.toString()

        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Email and Password can not be blank", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this){
            if (it.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                showLoading(true)
                finish()
            } else
                Toast.makeText(this, "Wrong input email or password ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }
}