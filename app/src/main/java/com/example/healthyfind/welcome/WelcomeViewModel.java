package com.example.healthyfind.welcome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WelcomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WelcomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment template");
    }

    public LiveData<String> getText() {
        return mText;
    }
}