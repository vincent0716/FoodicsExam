package com.vmcruz.foodicsexam.Pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.vmcruz.foodicsexam.Model.Orders
import com.vmcruz.foodicsexam.R
import com.vmcruz.foodicsexam.Repository.Repository
import com.vmcruz.foodicsexam.ViewModel.AppViewModel
import com.vmcruz.foodicsexam.ViewModelRepository
import com.vmcruz.foodicsexam.databinding.FragmentCheckoutBinding
import com.vmcruz.sampleapps.Room.AppDatabase


class CheckoutFragment : Fragment() {

    private lateinit var binding : FragmentCheckoutBinding
    private lateinit var viewModel : AppViewModel
    private lateinit var db: AppDatabase
    private var price : String = "";
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        price = arguments?.getString("price").toString()
        binding.btnPay.text = "Pay "+ price

        initial(view)
        //initListeners()


    }

    private fun initial(view:View) {
        db = AppDatabase(requireActivity())


        viewModel = activity?.run {
            ViewModelProvider(this, ViewModelRepository(Repository(requireActivity(),db))).get(
                AppViewModel::class.java)
        }!!

        binding.btnPay.setOnClickListener(View.OnClickListener {
            if(initFields()){
                val order = Orders(
                    name = binding.edtName.text.toString(),
                    email = binding.edtEmail.text.toString(),
                    payment = price,
                )
                viewModel.saveCheckOutDetails(order,requireActivity())
                val s = viewModel.getRecentOrder();
                val bundle = Bundle()
                bundle.putString("id",s.orderId.toString())
                Navigation.findNavController(view).navigate(R.id.action_checkoutFragment_to_orderFragment2,bundle)
            }
            else{
                Toast.makeText(requireActivity(),"Please populate all fields",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initFields() : Boolean{
        if(binding.edtName.text.isNullOrEmpty()) return false
        if(binding.edtEmail.text.isNullOrEmpty()) return false
        if(!binding.swTerms.isChecked) return false
        return true
    }


}