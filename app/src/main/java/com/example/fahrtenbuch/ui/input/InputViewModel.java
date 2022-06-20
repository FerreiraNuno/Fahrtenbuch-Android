package com.example.fahrtenbuch.ui.input;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InputViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InputViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is input fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}