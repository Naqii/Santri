package com.example.santri.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.santri.databinding.ItemListOneBinding
import com.example.santri.network.model.SantriItem

class SantriAdapter(private val listSantri: ArrayList<SantriItem>) :
    RecyclerView.Adapter<SantriAdapter.SantriViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(santri: ArrayList<SantriItem>) {
        listSantri.clear()
        listSantri.addAll(santri)
        notifyDataSetChanged()
    }

    inner class SantriViewHolder(private val binding: ItemListOneBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataSantri: SantriItem) {
            with(binding) {
                tvNis.text = dataSantri.nis
                tvUsername.text = dataSantri.name
                tvTelp.text = dataSantri.telp
                tvAddress.text = dataSantri.address
                tvEmail.text = dataSantri.email
            }
            //on click
//            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, DetailActivity::class.java)
//                intent.putExtra(DetailActivity.EXTRA_DATA, dataSantri)
//                itemView.context.startActivity(intent)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SantriViewHolder {
        val view =
            ItemListOneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SantriViewHolder(view)
    }

    override fun onBindViewHolder(holder: SantriViewHolder, position: Int) {
        holder.bind(listSantri[position])
    }

    override fun getItemCount(): Int = listSantri.size
}