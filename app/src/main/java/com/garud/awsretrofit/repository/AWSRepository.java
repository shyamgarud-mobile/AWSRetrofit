package com.garud.awsretrofit.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;


import com.garud.awsretrofit.services.AWSService;
import com.garud.awsretrofit.models.PresignUrlResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AWSRepository {
    private static final String AWS_PRESIGN_SERVICE_BASE_URL = "<PRESIGNED URL>";

    private AWSService awsService;
    private MutableLiveData<PresignUrlResponse> presignUrlResponseMutableLiveData;
    private MutableLiveData<JsonObject> jsonObjectMutableLiveData;
    public AWSRepository(){
        presignUrlResponseMutableLiveData = new MutableLiveData<>();
        jsonObjectMutableLiveData = new MutableLiveData<>();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        awsService =  new Retrofit.Builder()
                .baseUrl(AWS_PRESIGN_SERVICE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
                .create(AWSService.class);
    }
    public void callLambdaFunction(String requestedType){

        awsService.callLambdaFunction(requestedType)
                .enqueue(new Callback<PresignUrlResponse>() {
                    @Override
                    public void onResponse(Call<PresignUrlResponse> call, Response<PresignUrlResponse> response) {
                        if(response.body() != null){
                           Log.d(getClass().getName(),response.body().toString());
                            presignUrlResponseMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<PresignUrlResponse> call, Throwable t) {
                        presignUrlResponseMutableLiveData.postValue(null);
                    }
                });
    }

    public void uploadFile(String content_type,String url, RequestBody  file){
        Log.d(this.getClass().getName(),"uploadFile");

        awsService.uploadFile(content_type,url,file)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d(this.getClass().getName(),"Upload successful ? : "+response.isSuccessful()+"");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e(this.getClass().getName(),"Error"+t.getMessage());
                    }
                });

    }
    public LiveData<PresignUrlResponse> getPresignUrlResponse(){
        return presignUrlResponseMutableLiveData;
    }

    public MutableLiveData<JsonObject> getJsonObjectMutableLiveData() {
        return jsonObjectMutableLiveData;
    }

}
