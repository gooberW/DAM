package com.example.catapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.model.CatImage
import com.example.catapp.repository.CatRepository
import kotlinx.coroutines.launch

class CatViewModel(private val repository: CatRepository = CatRepository()) : ViewModel() {

    private val _images = MutableLiveData<List<CatImage>>()
    val images: LiveData<List<CatImage>> get() = _images

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    init {
        loadImages()
    }

    fun loadImages() {
        _isLoading.value = true
        _error.value = null
        
        viewModelScope.launch {
            val result = repository.getImages()
            
            if (result.isSuccess) {
                _images.value = result.getOrNull() ?: emptyList()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "An unknown error occurred"
            }
            
            _isLoading.value = false
        }
    }
}
