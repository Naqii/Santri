package com.example.santri.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.santri.databinding.ActivityEditBinding
import com.example.santri.network.model.SantriItem
import com.example.santri.ui.viewmodel.EditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditActivity : AppCompatActivity() {

    private val viewModel: EditViewModel by viewModels()
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = TITLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.editButton.setOnClickListener {
            editSantri()
//            finish()
        }

        showData()
    }

    private fun showData() {
        //Get Detail Data
        val data = intent.getParcelableExtra(EXTRA_DATA) as? SantriItem
        viewModel.getDataFormDetail().observe(this) { response ->
            if (response != null) {
                Log.d("tag", response[0].toString())
                viewEditxt(response[0])
            }
        }
        if (data != null) {
            data.id?.let { viewModel.idSantri(it) }
            viewEditxt(data)
        }
    }

    private fun viewEditxt(santri: SantriItem) {
        with(binding) {
            edtNis.setText(santri.nis)
            edtName.setText(santri.name)
            edtTelp.setText(santri.telp)
            edtAddress.setText(santri.address)
            edtCity.setText(santri.city)
            edtProv.setText(santri.province)
            edtBirth.setText(santri.birth)
            edtEmail.setText(santri.email)
            edtSikap.setText(santri.nilaiSikap)
            edtMateri.setText(santri.nilaiMateri)
            edtBacaan.setText(santri.nilaiBacaan)
            edtHafalan.setText(santri.nilaiHafalan)
            edtHadir.setText(santri.presensiHadir)
            edtIzin.setText(santri.presensiIzin)
            edtAlfa.setText(santri.presensiAlfa)
            edtKeterangan.setText(santri.presensiKeterangan)
            edtUniv.setText(santri.kampusUniv)
            edtProgdi.setText(santri.kampusProgdi)
            edtJurusan.setText(santri.kampusJurusan)
            edtGelar.setText(santri.kampusGelar)
        }
    }

    //For Edit
    private fun editSantri() {
        val id = intent.getStringExtra(ID)
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
            id, nis, name, telp, address, city, prov, birth, email, sikap, materi,
            bacaan, hafalan, hadir, izin, alfa, ket, univ, progdi, jurusan, gelar
        )
        if (id != null) {
            viewModel.editSantri(santri, id, id)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val TITLE = "Edit Santri"
        const val EXTRA_DATA = "extra_data"
        const val ID = "extra_id"
    }
}