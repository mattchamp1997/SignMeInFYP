package com.example.signmeinfyp.ui.MyClasses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyClassesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyClassesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("MyClasses");
    }

    public LiveData<String> getText() {
        return mText;
    }
}