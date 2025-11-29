package com.example.localeventhub.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.localeventhub.models.User;
import com.example.localeventhub.repository.UserRepository;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthViewModel extends AndroidViewModel {
    private UserRepository repository;
    private MutableLiveData<User> currentUser = new MutableLiveData<>();
    private MutableLiveData<String> loginError = new MutableLiveData<>();
    private ExecutorService executorService;

    public AuthViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
        executorService = Executors.newSingleThreadExecutor();
    }

    public void login(String username, String password) {
        executorService.execute(() -> {
            User user = repository.login(username, password);
            if (user != null) {
                currentUser.postValue(user);
                loginError.postValue(null);
            } else {
                loginError.postValue("Invalid username or password");
            }
        });
    }

    public MutableLiveData<User> getCurrentUser() { return currentUser; }
    public MutableLiveData<String> getLoginError() { return loginError; }
    public void logout() { currentUser.setValue(null); }
}