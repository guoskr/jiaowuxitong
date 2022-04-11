package com.example.shujuku.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shujuku.MainActivity_mianjiemian;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
        MainActivity_mianjiemian.jm=1;
    }

    public LiveData<String> getText() {
        return mText;
    }
}