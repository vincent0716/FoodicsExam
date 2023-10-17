package com.vmcruz.foodicsexam.Pages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.vmcruz.foodicsexam.Adapter.CartAdapter
import com.vmcruz.foodicsexam.Adapter.ProductAdapter
import com.vmcruz.foodicsexam.Model.Cart
import com.vmcruz.foodicsexam.R
import com.vmcruz.foodicsexam.Repository.Repository
import com.vmcruz.foodicsexam.ViewModel.AppViewModel
import com.vmcruz.foodicsexam.ViewModelRepository
import com.vmcruz.foodicsexam.databinding.FragmentCartBinding
import com.vmcruz.sampleapps.Room.AppDatabase

class CartFragment : Fragment() {

    private lateinit var binding : FragmentCartBinding
    private lateinit var viewModel : AppViewModel
    private lateinit var adapter: CartAdapter
    private lateinit var db: AppDatabase
    private var totalPrice : Double = 0.00
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false)

        initVIews(binding.root)

        populateList()

        return binding.root
    }

    private fun populateList() {
        viewModel.cartItems.observe(viewLifecycleOwner, Observer {
            adapter.setCart1(it)
        })

        viewModel.totalPrice.observe(viewLifecycleOwner, Observer {
            totalPrice = it
            binding.tvProdPrice.text = String.format("%.2f",totalPrice)
        })

        binding.rvCart.adapter = adapter

    }

    private fun initVIews(view : View) {
        db = AppDatabase(requireActivity())

        //communication between parent activity and fragment
        viewModel = activity?.run {
            ViewModelProvider(this, ViewModelRepository(Repository(requireActivity(),db))).get(AppViewModel::class.java)
        }!!
        adapter = CartAdapter(requireActivity())

        adapter.setOnClick(object : CartAdapter.onClickListener{
            override fun onClick(cart: Cart) {
                viewModel.deleteItemsInCart(cart)
            }

        })

        binding.cvBuyNow.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("price",binding.tvProdPrice.text.toString())
            Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_checkoutFragment2,bundle)
        })
    }



    override fun onResume() {
        super.onResume()
        viewModel.getItemsInCart()
    }


}