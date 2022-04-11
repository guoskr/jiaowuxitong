package com.example.shujuku.ui.gallery;

import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shujuku.MainActivity_mianjiemian;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
        MainActivity_mianjiemian.jm=2;
    }

    public LiveData<String> getText() {
        return mText;
    }
}