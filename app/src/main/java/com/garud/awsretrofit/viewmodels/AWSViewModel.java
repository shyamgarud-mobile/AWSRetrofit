package com.garud.awsretrofit.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.garud.awsretrofit.models.PresignUrlResponse;
import com.garud.awsretrofit.repository.AWSRepository;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AWSViewModel extends AndroidViewModel {
    private AWSRepository awsRepository;
    private LiveData<PresignUrlResponse> presignUrlResponseLiveData;
    private LiveData<JsonObject> jsonObjectLiveData;

    public AWSViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void init(){
        awsRepository = new AWSRepository();
        presignUrlResponseLiveData = awsRepository.getPresignUrlResponse();

        jsonObjectLiveData = awsRepository.getJsonObjectMutableLiveData();
    }

    public void callLambdaFunction(String requestedType){
        awsRepository.callLambdaFunction(requestedType);
    }

    public void uploadFile(String content_type,String url, RequestBody file){
        awsRepository.uploadFile(content_type,url,file);
    }

    public LiveData<PresignUrlResponse> getPresignUrlResponseLiveData(){
        return presignUrlResponseLiveData;
    }
    public LiveData<JsonObject> getJsonObjectLiveData() {
        return jsonObjectLiveData;
    }

}
