package com.example.santri.ui.view

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.santri.R
import com.example.santri.adapter.SantriAdapter
import com.example.santri.databinding.ActivityMainBinding
import com.example.santri.network.model.StatusResponse
import com.example.santri.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SantriAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = TITLE

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = SantriAdapter(ArrayList())
        binding.recyclerView.adapter = adapter
        showData()

        val swiveled = binding.swipeLayout
        swiveled.setOnRefreshListener {
            showData()
            Handler(Looper.getMainLooper()).postDelayed({
                if (swiveled.isRefreshing) {
                    swiveled.isRefreshing = false
                }
            }, 1000)
        }

        //fab add
        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search Hint"
        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isEmpty()){
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
                val menu = Intent(this, SettingActivity::class.java)
                startActivity(menu)
                return true
            }
            else -> return true
        }
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

    companion object {
        const val TITLE = "Main Menu"
    }
}