package com.example.santri.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import com.example.santri.R
import com.example.santri.databinding.ActivityDetailBinding
import com.example.santri.network.model.SantriItem
import com.example.santri.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    //animation
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_anim
        )
    }

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = TITLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fabMenu.setOnClickListener {
            onFabMenuClicked()
        }

        //FAB Delete
        binding.fabDelete.setOnClickListener {
            Toast.makeText(this, "Delete Button Clicked", Toast.LENGTH_SHORT).show()
        }

        //Get Detail Data
        val data = intent.getParcelableExtra(EXTRA_DATA) as? SantriItem
        viewModel.getDetailUser().observe(this) { response ->
            if (response != null) {
                Log.d("tag", response[0].toString())
                view(response[0])
            }
        }
        if (data != null) {
            data.id?.let { viewModel.setDetailUser(it) }
            view(data)
        }
    }

    //Animation FAB
    private fun onFabMenuClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (clicked) {
            binding.fabEdit.visibility = View.VISIBLE
            binding.fabDelete.visibility = View.VISIBLE
        } else {
            binding.fabEdit.visibility = View.INVISIBLE
            binding.fabDelete.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.fabEdit.startAnimation(fromBottom)
            binding.fabDelete.startAnimation(fromBottom)
            binding.fabMenu.startAnimation(rotateOpen)
        } else {
            binding.fabEdit.startAnimation(toBottom)
            binding.fabDelete.startAnimation(toBottom)
            binding.fabMenu.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            binding.fabEdit.isClickable = true
            binding.fabDelete.isClickable = true
        } else {
            binding.fabEdit.isClickable = false
            binding.fabDelete.isClickable = false
        }
    }

    //Post Data to View
    private fun view(data: SantriItem) {
        with(binding) {
            nis.text = data.nis
            name.text = data.name
            telp.text = data.telp
            //Detail Bio
            tvEmail.text = data.email
            tvAddress.text = data.address
            tvKota.text = data.city
            tvTtl.text = data.birth
            tvProvince.text = data.province
            //Detail Kampus
            tvUniv.text = data.kampusUniv
            tvFakultas.text = data.kampusJurusan
            tvProgdi.text = data.kampusProgdi
            tvGelar.text = data.kampusGelar
            //Detail Nilai
            tvMateri.text = data.nilaiMateri
            tvBacaan.text = data.nilaiBacaan
            tvSikap.text = data.nilaiSikap
            tvHafalan.text = data.nilaiHafalan
            //Detail Presensi
            tvHadir.text = data.presensiHadir
            tvIzin.text = data.presensiIzin
            tvAlfa.text = data.presensiAlfa
            tvKeterangan.text = data.presensiKeterangan
        }
        //FAB Edit
        binding.fabEdit.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra(EditActivity.EXTRA_DATA, data)
            intent.putExtra(EditActivity.ID, data.id)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val TITLE = "Detail Santri"
        const val EXTRA_DATA = "extra_data"
    }
}