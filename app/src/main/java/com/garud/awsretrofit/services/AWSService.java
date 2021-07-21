package com.garud.awsretrofit.services;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

import com.garud.awsretrofit.models.PresignUrlResponse;
import com.google.gson.JsonObject;

public interface  AWSService {
    @GET("UploadFileForInspections")
    Call<PresignUrlResponse> callLambdaFunction(@Query("requested_type") String requestedType);

    @PUT
    Call<Void> uploadFile(@Header("Content-Type") String content_type,@Url String url, @Body  RequestBody file);
}
