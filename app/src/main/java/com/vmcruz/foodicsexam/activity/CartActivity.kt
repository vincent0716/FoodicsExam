package com.vmcruz.foodicsexam.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.vmcruz.foodicsexam.R
import com.vmcruz.foodicsexam.Repository.Repository
import com.vmcruz.foodicsexam.ViewModel.AppViewModel
import com.vmcruz.foodicsexam.ViewModelRepository
import com.vmcruz.foodicsexam.databinding.ActivityCartBinding
import com.vmcruz.foodicsexam.databinding.ActivityMainBinding
import com.vmcruz.sampleapps.Room.AppDatabase

class CartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCartBinding
    private lateinit var viewModel : AppViewModel
    private lateinit var db : AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        db=AppDatabase(baseContext)
        viewModel =  ViewModelProvider(this, ViewModelRepository(Repository(baseContext,db))).get(AppViewModel::class.java)

        viewModel.cartItems.observe(this, Observer {
            binding.ibvIcon2.badgeValue = it.size
        })

        viewModel.getItemsInCart()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        /*binding.ibvIcon2.setOnClickListener(View.OnClickListener {
            val currentDestination = navHostFragment.navController.currentDestination
            val destinationIdToNavigate = R.id.cartFragment


            if(currentDestination?.id!=destinationIdToNavigate){
                navHostFragment.navController.navigate(R.id.actioncar)
            }





        })*/
    }


    override fun onResume() {
        super.onResume()

    }
}