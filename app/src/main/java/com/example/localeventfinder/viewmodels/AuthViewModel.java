package com.example.localeventfinder.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.localeventfinder.data.SimpleDataManager;
import com.example.localeventfinder.models.User;

public class AuthViewModel extends AndroidViewModel {
    private SimpleDataManager dataManager;
    private MutableLiveData<User> currentUser = new MutableLiveData<>();
    private MutableLiveData<String> loginError = new MutableLiveData<>();
    
    public AuthViewModel(Application application) {
        super(application);
        dataManager = SimpleDataManager.getInstance();
    }
    
    public MutableLiveData<User> getCurrentUser() {
        return currentUser;
    }
    
    public MutableLiveData<String> getLoginError() {
        return loginError;
    }
    
    public void login(String username, String password) {
        User user = dataManager.login(username, password);
        if (user != null) {
            currentUser.setValue(user);
            loginError.setValue(null);
        } else {
            loginError.setValue("Invalid username or password");
        }
    }
    
    public void logout() {
        dataManager.logout();
        currentUser.setValue(null);
    }
}