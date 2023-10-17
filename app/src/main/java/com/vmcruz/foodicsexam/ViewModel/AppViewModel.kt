package com.vmcruz.foodicsexam.ViewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.vmcruz.foodicsexam.Model.Cart
import com.vmcruz.foodicsexam.Model.OrderJSONModel
import com.vmcruz.foodicsexam.Model.Orders
import com.vmcruz.foodicsexam.Model.Products
import com.vmcruz.foodicsexam.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class AppViewModel(private val repository: Repository) : ViewModel() {

    var productsList: MutableLiveData<ArrayList<Products>> = MutableLiveData(ArrayList())
    var cartItems: MutableLiveData<ArrayList<Cart>> = MutableLiveData(ArrayList())
    var checkItemInCart : MutableLiveData<Boolean> = MutableLiveData()
    var totalPrice : MutableLiveData<Double> = MutableLiveData()
    var orderDetails : MutableLiveData<Orders> = MutableLiveData()

    fun getProducts(){
        viewModelScope.launch {
            val products =repository.getProductFromJSON()
            productsList.value=products;
        }
    }

    fun addToCart(cart : Cart){
        viewModelScope.launch {
            repository.addToCart(cart)
            getItemsInCart()

        }
    }

    fun checkIfExist(id:String){
        viewModelScope.launch {
            checkItemInCart.value = repository.checkIfExist(id)
            Log.e("TAG",checkItemInCart.value.toString())
        }

    }

    fun getItemsInCart(){
        viewModelScope.launch {
            cartItems.value=repository.getItemsInCart()
            totalPrice.value = totalPrice(repository.getItemsInCart())
        }
    }

    fun deleteItemsInCart(cart : Cart){
        viewModelScope.launch {
            repository.removeItemInCart(cart)
            cartItems.value = repository.getItemsInCart()
            totalPrice.value = totalPrice(repository.getItemsInCart())
        }
    }

    fun saveCheckOutDetails(orders: Orders,context: Context){
        viewModelScope.launch {
            repository.saveCheckOutDetails(orders)
            saveOrderJSON(orders,context)
        }
    }


    private fun totalPrice(cart : ArrayList<Cart>) : Double{
        var total =0.00;

        for(i in 0 until cart.size){
            total+=cart[i].price.toDouble()
        }

        return total;
    }

    fun getRecentOrder() : Orders{
        viewModelScope.launch {
            orderDetails.value = repository.getOrderDetails()
        }
        return orderDetails.value!!
    }

    private fun saveOrderJSON(orders : Orders,context:Context){
        viewModelScope.launch {
            val orderJson = OrderJSONModel(orders,repository.getItemsInCart())
            val gson = Gson()
            val json = gson.toJson(orderJson)

            val fileName = "order_100"+repository.getOrderDetails().orderId+".json"
            val filePath = File(context.filesDir, fileName)
            try {
                withContext(Dispatchers.IO) {
                    FileOutputStream(filePath).use { outputStream ->
                        outputStream.write(json.toByteArray())
                        repository.deleteCart()

                    }
                }
                Log.e("TAG","The order json has been save in "+filePath)
                Toast.makeText(context, "The order json has been save in "+filePath,Toast.LENGTH_SHORT).show()
                cartItems.value = repository.getItemsInCart()


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }



    }

}