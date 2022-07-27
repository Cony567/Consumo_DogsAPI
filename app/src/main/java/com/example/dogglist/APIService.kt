package com.example.dogglist

//importante que lo importe de retrofit
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

//Interface que creará el método por el cual se accede a una api, que consume un servicio, get en este caso
interface APIService {
    // anotaciones
    @GET
    suspend fun getDogsByBreeds(@Url url: String) : Response<DogsResponse>
}