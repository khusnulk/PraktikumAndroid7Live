package com.praktek.praktikumandroid7live.ui.jenisbarang

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.praktek.praktikumandroid7live.databinding.ItemRowJenisbarangBinding
import com.praktek.praktikumandroid7live.model.JenisbarangData
import com.praktek.praktikumandroid7live.model.JenisbarangResponse
import com.praktek.praktikumandroid7live.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListJenisbarangAdapter(private val listJenisbarang: ArrayList<JenisbarangData>) : RecyclerView.Adapter<ListJenisbarangAdapter.ListViewHolder>() {

    class ListViewHolder(private val binding: ItemRowJenisbarangBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(jenisbarangData: JenisbarangData) {
            with(binding){
                tvNamaJenisbarang.text = jenisbarangData.namajenisbarang

                btnDelete.setOnClickListener {
                    AlertDialog.Builder(binding.root.context)
                            .setMessage("Data akan dihapus, lanjutkan?")
                            .setCancelable(false)
                            .setPositiveButton("Ya") { dialog, _ ->

                                Api.retrofitService.delete(jenisbarangData)
                                        .enqueue(object :
                                                Callback<JenisbarangResponse> {
                                            override fun onResponse(
                                                    call: Call<JenisbarangResponse>,
                                                    response: Response<JenisbarangResponse>
                                            ) {
                                                Toast.makeText(
                                                        binding.root.context, response.body()?.message,
                                                        Toast.LENGTH_SHORT
                                                ).show()
                                                listJenisbarang.removeAt(bindingAdapterPosition)
                                                bindingAdapter?.notifyItemRemoved(bindingAdapterPosition)
                                            }

                                            override fun onFailure(
                                                    call: Call<JenisbarangResponse>,
                                                    t: Throwable
                                            ) {
                                                Toast.makeText(
                                                        binding.root.context,
                                                        t.message,
                                                        Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        })
                            }
                            .setNegativeButton("Tidak") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowJenisbarangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listJenisbarang[position])
    }

    override fun getItemCount():Int = listJenisbarang.size



}