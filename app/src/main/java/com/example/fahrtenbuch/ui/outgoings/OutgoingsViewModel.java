package com.example.fahrtenbuch.ui.outgoings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OutgoingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public OutgoingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is input fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}