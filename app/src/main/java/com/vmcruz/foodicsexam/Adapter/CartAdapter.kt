package com.vmcruz.foodicsexam.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.vmcruz.foodicsexam.Model.Cart
import com.vmcruz.foodicsexam.Model.Products
import com.vmcruz.foodicsexam.R
import org.w3c.dom.Text

class CartAdapter(val context : Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    var cart : ArrayList<Cart> = ArrayList()
    private lateinit var listener : onClickListener


    @SuppressLint("NotifyDataSetChanged")
    fun setCart1(arrayList: ArrayList<Cart>){
        this.cart = arrayList;
        notifyDataSetChanged()
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val cv_cont : MaterialCardView = itemView.findViewById(R.id.cv_bg)
        val tv_name : TextView = itemView.findViewById(R.id.tv_prodName)
        val tv_price : TextView = itemView.findViewById(R.id.tv_prodPrice)
        val cv_delete : MaterialCardView = itemView.findViewById(R.id.cv_close)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_adapter,parent,false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        var totalPrice =0;
        holder.cv_cont.setBackgroundColor(Color.parseColor(cart[position].bgColor))
        holder.tv_name.text = cart[position].name
        holder.tv_price.text = cart[position].price
        holder.cv_delete.post {
            holder.cv_delete.setOnClickListener(View.OnClickListener {
                listener.onClick(cart[position])
            })
        }
    }

    override fun getItemCount(): Int {
        return cart.size
    }

    interface onClickListener{
        fun onClick(cart:Cart)
    }

    fun setOnClick(listener:onClickListener){
        this.listener = listener
    }

}