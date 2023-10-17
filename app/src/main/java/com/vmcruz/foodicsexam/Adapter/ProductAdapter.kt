package com.vmcruz.foodicsexam.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.vmcruz.foodicsexam.Model.Products
import com.vmcruz.foodicsexam.R

class ProductAdapter(val context : Context) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var productsList : ArrayList<Products> = ArrayList()
    private lateinit var listener : onClickListener

    @SuppressLint("NotifyDataSetChanged")
    fun setProductList1(arrayList: ArrayList<Products>){
        this.productsList = arrayList;
        notifyDataSetChanged()
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val container : MaterialCardView = itemView.findViewById(R.id.cv_container)
        val tv_cat : TextView = itemView.findViewById(R.id.tv_prodCategory)
        val tv_name : TextView = itemView.findViewById(R.id.tv_prodName)
        val tv_price : TextView = itemView.findViewById(R.id.tv_prodPrice)
        val btn_add : TextView = itemView.findViewById(R.id.btn_add)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_productadapter,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.container.setBackgroundColor(Color.parseColor(productsList[position].bgColor))
        holder.tv_cat.text = productsList[position].category
        holder.tv_name.text = productsList[position].name
        holder.tv_price.text = productsList[position].price

        holder.btn_add.post {
            holder.btn_add.setOnClickListener(View.OnClickListener {
                listener.onClick(productsList[position])
            })
        }

    }

    override fun getItemCount(): Int {
        return productsList.size
    }


    interface onClickListener{
        fun onClick(product:Products)
    }

    fun setOnClick(listener:onClickListener){
        this.listener = listener
    }
}