package com.example.dogglist

import com.google.gson.annotations.SerializedName

//object class que se utilizará para recibir el json y convertirlo a la misma
data class DogsResponse (
    @SerializedName("status")var status: String,
    @SerializedName("message")var images: List<String>
)