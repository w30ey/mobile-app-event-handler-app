package com.example.localeventhub.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.localeventhub.models.User;
import com.example.localeventhub.repository.UserRepository;

public class AuthViewModel extends AndroidViewModel {
    private UserRepository repository;
    private MutableLiveData<User> currentUser = new MutableLiveData<>();
    private MutableLiveData<String> loginError = new MutableLiveData<>();
    
    public AuthViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
    }
    
    public void login(String username, String password) {
        User user = repository.login(username, password);
        if (user != null) {
            currentUser.setValue(user);
            loginError.setValue(null);
        } else {
            loginError.setValue("Invalid username or password");
        }
    }
    
    public MutableLiveData<User> getCurrentUser() {
        return currentUser;
    }
    
    public MutableLiveData<String> getLoginError() {
        return loginError;
    }
    
    public void logout() {
        currentUser.setValue(null);
    }
}