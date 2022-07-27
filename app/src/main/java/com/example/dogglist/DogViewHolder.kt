package com.example.dogglist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dogglist.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

//recibe una view que es la que se va a pintar, que hereda un viewholder
class DogViewHolder(view:View) : RecyclerView.ViewHolder(view){

    private val binding = ItemDogBinding.bind(view)
    fun bind (image: String){
        Picasso.get().load(image).into(binding.ivDog)
    }
}