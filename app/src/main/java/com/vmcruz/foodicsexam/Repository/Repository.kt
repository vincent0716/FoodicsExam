package com.vmcruz.foodicsexam.Repository

import android.content.Context
import android.widget.Toast
import com.vmcruz.foodicsexam.Model.Cart
import com.vmcruz.foodicsexam.Model.Orders
import com.vmcruz.foodicsexam.Model.Products
import com.vmcruz.foodicsexam.R
import com.vmcruz.sampleapps.Room.AppDatabase
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class Repository(private val context: Context,private val appDatabase: AppDatabase) {

    fun getProductFromJSON() : ArrayList<Products>{
        val productsList : ArrayList<Products> = ArrayList()
        val productJson  = readJSONDataFromFile();
        val jsonObject = JSONObject(productJson)
        val jsonArray = jsonObject.getJSONArray("products")

        for (i in 0 until jsonArray.length()) {
            val itemObj = jsonArray.getJSONObject(i)
            val id = itemObj.getString("id")
            val name = itemObj.getString("name")
            val category = itemObj.getString("category")
            val price = itemObj.getString("price")
            val bgcolor = itemObj.getString("bgColor")

            val products : Products = Products(id = id,name = name, category = category, price = price, bgColor = bgcolor)
            productsList.add(products);
        }

        return productsList
    }

    suspend fun addToCart(cart:Cart){
        appDatabase.cartDao().insert(cart)
    }

    suspend fun removeItemInCart(cart:Cart){
        appDatabase.cartDao().delete(cart)
    }

    suspend fun getItemsInCart() : ArrayList<Cart>{
        return appDatabase.cartDao().itemInCartList() as ArrayList
    }

    suspend fun checkIfExist(id:String) : Boolean{
        val count = appDatabase.cartDao().isExisting(id).size
        return count != 0
    }

    suspend fun saveCheckOutDetails(orders: Orders){
        appDatabase.orderDao().insert(orders)
    }


    suspend fun getOrderDetails() : Orders{
        return appDatabase.orderDao().orderedList()
    }

    suspend fun deleteCart(){
        appDatabase.cartDao().deleteCart()
    }


    @Throws(IOException::class)
    private fun readJSONDataFromFile(): String {
        var inputStream: InputStream? = null
        val builder = StringBuilder()
        try {
            var jsonString: String? = null
            inputStream = context.resources.openRawResource(R.raw.products)
            val bufferedReader = BufferedReader(
                InputStreamReader(inputStream, StandardCharsets.UTF_8)
            )
            while (bufferedReader.readLine().also { jsonString = it } != null) {
                builder.append(jsonString)
            }
        } finally {
            inputStream?.close()
        }
        return String(builder)
    }

}