package com.example.test.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.Adapter.BuyAgainAdapter
import com.example.test.R
import com.example.test.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater,container,false)
        setupRecycleView()
        return binding.root
    }

    private fun setupRecycleView(){
        val buyAgainFoodName = arrayListOf("Food 2","Food 2","Food 3")
        val buyAgainFoodPrice = arrayListOf("$10","$10","$10")
        val buyAgainFoodImage = arrayListOf(R.drawable.menu1,R.drawable.menu2,R.drawable.menu3)
        buyAgainAdapter = BuyAgainAdapter(buyAgainFoodName,buyAgainFoodPrice,buyAgainFoodImage)
        binding.BuyAgainRecycleView.adapter = buyAgainAdapter
        binding.BuyAgainRecycleView.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {

    }
}