package com.vmcruz.foodicsexam.Pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.vmcruz.foodicsexam.Adapter.ProductAdapter
import com.vmcruz.foodicsexam.Model.Cart
import com.vmcruz.foodicsexam.Model.Products
import com.vmcruz.foodicsexam.Repository.Repository
import com.vmcruz.foodicsexam.ViewModel.AppViewModel
import com.vmcruz.foodicsexam.ViewModelRepository
import com.vmcruz.foodicsexam.databinding.FragmentProductBinding
import com.vmcruz.sampleapps.Room.AppDatabase
import kotlinx.coroutines.launch
import kotlin.random.Random


class ProductFragment : Fragment() {

    private lateinit var binding : FragmentProductBinding
    private lateinit var viewModel : AppViewModel
    private lateinit var adapter: ProductAdapter
    private lateinit var db: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(inflater, container, false);

        initVIews()

        populateList()

        return binding.root;
    }

    private fun initVIews() {
        db = AppDatabase(requireActivity())

        //communication between parent activity and fragment
        viewModel = activity?.run {
            ViewModelProvider(this, ViewModelRepository(Repository(requireActivity(),db))).get(AppViewModel::class.java)
        }!!
        adapter = ProductAdapter(requireActivity())

        adapter.setOnClick(object : ProductAdapter.onClickListener{
            override fun onClick(product: Products) {
                val cart = Cart(
                    Random.nextInt(10000),
                    id = product.id,
                    name = product.name,
                    category = product.category,
                    price = product.price,
                    bgColor = product.bgColor
                )

                lifecycleScope.launch {
                    if(db.cartDao().isExisting(cart.id).isEmpty()){
                        viewModel.addToCart(cart)
                        Snackbar.make(binding.root,product.name + " "+ "has been added to your cart",Snackbar.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(context,"Item is already existing in the cart",Toast.LENGTH_SHORT).show()
                    }
                }



            }


        })

        binding.rvProductList.adapter = adapter
    }

    private fun populateList() {
        viewModel.productsList.observe(viewLifecycleOwner, Observer {
            adapter.setProductList1(it);
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProducts()

    }


}