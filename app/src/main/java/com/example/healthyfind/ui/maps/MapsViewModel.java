package com.example.healthyfind.ui.maps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MapsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Welcome to Healthy Finds, this text will likely change :)");
    }

    public LiveData<String> getText() {
        return mText;
    }
}