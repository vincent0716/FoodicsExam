package com.vmcruz.foodicsexam.Pages

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vmcruz.foodicsexam.R
import com.vmcruz.foodicsexam.activity.MainActivity
import com.vmcruz.foodicsexam.databinding.FragmentOrderConfirmationBinding


class OrderConfirmationFragment : Fragment() {

    private lateinit var binding : FragmentOrderConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOrderConfirmationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments;
        binding.tvMessage.text = "Your Order ID is #100"+bundle?.getString("id")
        binding.btnReturn.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireActivity(),MainActivity::class.java)
            startActivity(intent)
        })
    }


}