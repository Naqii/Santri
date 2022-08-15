package com.example.santri.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.santri.databinding.ActivityDetailBinding
import com.example.santri.network.model.SantriItem
import com.example.santri.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = TITLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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