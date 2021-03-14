package com.praktek.praktikumandroid7live.ui.jenisbarang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.praktek.praktikumandroid7live.R
import com.praktek.praktikumandroid7live.databinding.ActivityJenisbarangBinding
import com.praktek.praktikumandroid7live.model.JenisbarangData

class JenisbarangActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: ActivityJenisbarangBinding
    private val list = ArrayList<JenisbarangData>()
    private val viewModel: JenisbarangViewModel by lazy {
        ViewModelProvider(this).get(JenisbarangViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJenisbarangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvJenisbarang.setHasFixedSize(true)

        binding.progressBarJenisbarang.visibility = View.VISIBLE

        getListJenisbarang()

        binding.btTambahJenisbarang.setOnClickListener{
            val intent = Intent(this, JenisbarangPostActivity::class.java)
            startActivity(intent)
        }

        binding.swiperefreshlayout.setOnRefreshListener(this)

    }



    private fun getListJenisbarang() {
        viewModel.response.observe(this, {

            binding.progressBarJenisbarang.visibility = View.INVISIBLE
            binding.swiperefreshlayout.visibility = View.VISIBLE
            list.addAll(it.data)
            binding.rvJenisbarang.layoutManager = LinearLayoutManager(this)
            val listJenisbarangAdapter = ListJenisbarangAdapter(list)
            binding.rvJenisbarang.adapter = listJenisbarangAdapter
        })
    }

    override fun onRefresh() {
        list.clear()
        Toast.makeText(this, "refreshed", Toast.LENGTH_SHORT).show()
        onStop()

    }


}