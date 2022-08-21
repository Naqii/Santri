package com.example.santri.ui.view

import android.app.SearchManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.santri.R
import com.example.santri.adapter.SantriAdapter
import com.example.santri.databinding.ActivityMainBinding
import com.example.santri.network.model.StatusResponse
import com.example.santri.ui.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SantriAdapter
    private lateinit var mAuth: FirebaseAuth

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val notConnected = intent.getBooleanExtra(ConnectivityManager
                .EXTRA_NO_CONNECTIVITY,false)
            if (notConnected) {
                disconnected()
            } else {
                connected()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = TITLE

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        binding.tvAccount.text = currentUser?.email

        val swiveled = binding.swipeLayout
        swiveled.setOnRefreshListener {
            setupRecyclerView()
            Handler(Looper.getMainLooper()).postDelayed({
                if (swiveled.isRefreshing) {
                    swiveled.isRefreshing = false
                }
            }, 1000)
        }

        setupRecyclerView()

        //fab add
        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = SantriAdapter(ArrayList())
        binding.recyclerView.adapter = adapter
        showData()
    }

    private fun showData() {
        viewModel.getSantri().observe(this) { response ->
            when (response.status) {
                StatusResponse.SUCCESS -> {
                    showLoading(false)
                    val data = response.body?.santri
                    if (response.body != null) {
                        adapter = data?.let { SantriAdapter(it) }!!
                        binding.recyclerView.adapter = adapter
                    }
                }
                StatusResponse.EMPTY -> {
                    showLoading(false)
                    adapter = SantriAdapter(ArrayList())
                    binding.recyclerView.adapter = adapter
                }
                StatusResponse.ERROR -> {
                    showLoading(false)
                    Toast.makeText(
                        this,
                        "An error occured, Please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    showLoading(true)
                }
            }
        }
    }

    //Option Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search Hint"
        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.isEmpty()){
                        return true
                    } else {
                        showLoading(true)
                        viewModel.setSrcSantri(query).observe(this@MainActivity) { response ->
                            when (response.status) {
                                StatusResponse.SUCCESS -> {
                                    showLoading(false)
                                    val id = response.body?.santri
                                    if (response.body != null) {
                                        id?.let { adapter.setData(it) }
                                        binding.recyclerView.adapter = adapter
                                    }
                                }
                                StatusResponse.EMPTY -> {
                                    showLoading(false)
                                    adapter = SantriAdapter(ArrayList())
                                    binding.recyclerView.adapter = adapter
                                }
                                StatusResponse.ERROR -> {
                                    showLoading(false)
                                    Toast.makeText(this@MainActivity,
                                        "An error occupied can't bind data",
                                        Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    showLoading(true)
                                }
                            }
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu -> {
                showAlertDialog(ALERT_DIALOG_CLOSE)
                return true
            }
            else -> return true
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
                    finish()
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

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    private fun disconnected() {
        binding.recyclerView.visibility = View.GONE
        binding.errorNoData.visibility = View.GONE
        binding.errorConnection.visibility = View.VISIBLE
    }

    private fun connected() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.errorConnection.visibility = View.GONE
        binding.errorNoData.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    companion object {
        const val TITLE = "Main Menu"
        const val ALERT_DIALOG_CLOSE = 10
    }
}