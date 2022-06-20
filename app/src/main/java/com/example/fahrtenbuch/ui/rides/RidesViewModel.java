package com.example.fahrtenbuch.ui.rides;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RidesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RidesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is rides fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}