package com.example.lab2books;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<String> selectedAuthor = new MutableLiveData<>("");
    private final MutableLiveData<String> selectedYear = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> clearInput = new MutableLiveData<>(false);

    public LiveData<String> getSelectedAuthor() {
        return selectedAuthor;
    }

    public LiveData<String> getSelectedYear() {
        return selectedYear;
    }

    public LiveData<Boolean> getClearInput() {
        return clearInput;
    }

    public void setSelection(String author, String year) {
        selectedAuthor.setValue(author);
        selectedYear.setValue(year);
    }

    public void requestClearInput() {
        clearInput.setValue(true);
    }

    public void clearHandled() {
        clearInput.setValue(false);
    }

    public void clearAllData() {
        selectedAuthor.setValue("");
        selectedYear.setValue("");
    }
}