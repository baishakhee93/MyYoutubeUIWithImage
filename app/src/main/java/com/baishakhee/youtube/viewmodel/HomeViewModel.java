package com.baishakhee.youtube.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.baishakhee.youtube.api.APIClient;
import com.baishakhee.youtube.api.APIInterface;
import com.baishakhee.youtube.model.Items;
import com.baishakhee.youtube.model.MainModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Items>> videoList = new MutableLiveData<>();
    private APIInterface apiService = APIClient.getClient().create(APIInterface.class);
    private Disposable disposable;

    public LiveData<List<Items>> getVideoList() {
        return videoList;
    }

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadVideos() {
        isLoading.setValue(true); // Set loading state to true

        disposable = apiService.searchVideos("id,snippet", 50, "UCVMWWQ985A_-SESZUy_SsVQ", "AIzaSyAityvKSb8_MZFnq9cuKOniroFkkA-L_SE")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mainModel -> {
                            isLoading.setValue(false); // Set loading state to false
                            videoList.setValue(mainModel.getItems());
                        },
                        throwable -> {
                            isLoading.setValue(false); // Set loading state to false in case of error
                            // Handle error, e.g., show an error message
                        }
                );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
