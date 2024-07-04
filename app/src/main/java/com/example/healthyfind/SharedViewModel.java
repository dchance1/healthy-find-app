package com.example.healthyfind;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<String> selectedItem = new MutableLiveData<>();

    public void setData(String data) {
        selectedItem.setValue(data);
    }

    public LiveData<String> getData() {
        return selectedItem;
    }
}
