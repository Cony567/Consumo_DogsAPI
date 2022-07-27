package com.example.dogglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogglist.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),
    android.widget.SearchView.OnQueryTextListener {
    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svDogs.setOnQueryTextListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = DogAdapter(dogImages)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = adapter
    }


    // se crea una instancia de un objeto retrofit
    // contiene toda la url original o el endpoint general
    // el conversor de json
    // y toda la config para hacer llamadas a internet
    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()//conversión de Json a dogsResponse
    }

//como funciona un RV, un adapter va a recibir un listado de objetos, entonces el adapter le dice a otro que le convierta ese objeto en una vista
    private fun searchByName(query:String){
        //creación de corrutina para hacer la llamada a api en un hilo secundario
        CoroutineScope(Dispatchers.IO).launch {
                                                    //se llama al metodo getDogsByBreeds declarado en el servicio
            val call = getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")
            val puppies = call.body()
            runOnUiThread{
                if (call.isSuccessful){
                    val images = puppies?.images ?: emptyList()
                    dogImages.clear()
                    dogImages.addAll(images)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                    //dogImages.clear()
                    //dogImages.addAll(emptyList())
                    //adapter.notifyDataSetChanged()
                }
            }

        }
    }

    // envio de toast por el error
    private fun showError(){
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            searchByName(query.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}