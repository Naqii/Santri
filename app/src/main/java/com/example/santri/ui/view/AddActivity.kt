package com.example.santri.ui.view

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.santri.databinding.ActivityAddBinding
import com.example.santri.network.model.SantriItem
import com.example.santri.ui.viewmodel.CreateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddActivity : AppCompatActivity() {

    private val viewModel: CreateViewModel by viewModels()
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = TITLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.createButton.setOnClickListener {
            createSantri()
//            Toast.makeText(this, "Success adding Data Santri", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createSantri() {
        val nis = binding.edtNis.text.toString()
        val name = binding.edtName.text.toString()
        val telp = binding.edtTelp.text.toString()
        val address = binding.edtAddress.text.toString()
        val city = binding.edtCity.text.toString()
        val prov = binding.edtProv.text.toString()
        val birth = binding.edtBirth.text.toString()
        val email = binding.edtEmail.text.toString()
        val sikap = binding.edtSikap.text.toString()
        val materi = binding.edtMateri.text.toString()
        val bacaan = binding.edtBacaan.text.toString()
        val hafalan = binding.edtHafalan.text.toString()
        val hadir = binding.edtHadir.text.toString()
        val izin = binding.edtIzin.text.toString()
        val alfa = binding.edtAlfa.text.toString()
        val ket = binding.edtKeterangan.text.toString()
        val univ = binding.edtUniv.text.toString()
        val progdi = binding.edtProgdi.text.toString()
        val jurusan = binding.edtJurusan.text.toString()
        val gelar = binding.edtGelar.text.toString()
        val santri = SantriItem(
            "",
            nis,
            name,
            telp,
            address,
            city,
            prov,
            birth,
            email,
            sikap,
            materi,
            bacaan,
            hafalan,
            hadir,
            izin,
            alfa,
            ket,
            univ,
            progdi,
            jurusan,
            gelar
        )
        if (nis.isBlank() || name.isBlank() || telp.isBlank() || address.isBlank() || city.isBlank()
            || prov.isBlank() || birth.isBlank() || email.isBlank() || sikap.isBlank() || materi.isBlank()
            || bacaan.isBlank() || hafalan.isBlank() || hadir.isBlank() || izin.isBlank() || alfa.isBlank()
            || ket.isBlank() || univ.isBlank() || progdi.isBlank() || jurusan.isBlank() || gelar.isBlank()) {
            Toast.makeText(this, "Empty field not allowed!", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.createSantri(santri)
            Toast.makeText(this, "Proceed..", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val TITLE = "Tambah Santri"
    }
}