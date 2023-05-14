package com.example.listeddashboard.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listeddashboard.Model.ApiRes
import com.example.listeddashboard.Model.ListCustom
import com.example.listeddashboard.Network.Api
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Vm:ViewModel() {
    //defining bearer token for authentication
    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"
    //declaring our livedata variable to store api response
    private val _res  =MutableLiveData<ApiRes>()
    val res:LiveData<ApiRes>
        get() = _res
    //declaring our livedata variable to store api response
    private val _err  =MutableLiveData<String>()
    val err:LiveData<String>
        get() = _err
init{
    //calling function to fetch data from api
      fetchData()
}
    //function to fetch data from api
    private fun fetchData() {
        //here viewmodelscope is used and only one instance of retrofit is created as it is costly on resources to initialize it again and again
//        viewmodel scope is used as it will destroy automatically on termination of viewModel
        viewModelScope.launch{
            try{
                //calling retrofit services to get data
                val response = Api.retrofitService.getData("Bearer $token")
                _res.value = response
            }
            catch (e:Exception){
                _err.value = e.message.toString()
            }
        }
    }

}