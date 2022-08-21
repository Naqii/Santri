package com.example.santri.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.santri.R
import com.example.santri.databinding.ActivitySettingBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mAuth = FirebaseAuth.getInstance()
        binding.glogout.setOnClickListener {
            showAlertDialog(ALERT_DIALOG_CLOSE)
        }
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == DetailActivity.ALERT_DIALOG_CLOSE
        val dialogTitle = getString(R.string.txt_logout)
        val dialogMessage = getString(R.string.txt_dialog_logout)
        val alertDialogBuilder = AlertDialog.Builder(this)
        val back = Intent(this, LoginActivity::class.java)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton("Ya") { _, _ ->
                if (isDialogClose) {
                    showToast(getString(R.string.logedout))
                    mAuth.signOut()
                    startActivity(back)
                    finishAndRemoveTask()
                }
            }
            setNegativeButton("Tidak") { dialog, _ -> dialog.cancel()}
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val ALERT_DIALOG_CLOSE = 10
    }
}