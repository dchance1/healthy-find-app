package com.example.healthyfind.ui.foodpantry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoodPantryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FoodPantryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Welcome to Healthy Finds, this text will likely change :)");
    }

    public LiveData<String> getText() {
        return mText;
    }
}