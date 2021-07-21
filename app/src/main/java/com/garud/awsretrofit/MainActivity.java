package com.garud.awsretrofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.garud.awsretrofit.models.PresignUrlResponse;
import com.garud.awsretrofit.viewmodels.AWSViewModel;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class MainActivity extends AppCompatActivity {

    private AWSViewModel awsViewModel;

    ImageView imgView ;
    Button btn;
    int RESULT_LOAD_IMAGE = 1234;
    String picturePath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        awsViewModel =new  ViewModelProvider(this).get(AWSViewModel.class);
        awsViewModel.init();
        awsViewModel.getPresignUrlResponseLiveData().observe(this, new Observer<PresignUrlResponse>() {
            @Override
            public void onChanged(PresignUrlResponse presignUrlResponse) {
                if(presignUrlResponse != null){
                    RequestBody requestBodyee = null;
                    MediaType mediaType = MediaType.parse("image/jpeg");
                    try {
                        InputStream in = new FileInputStream(picturePath);

                        byte[] buf;
                        buf = new byte[in.available()];
                        while (in.read(buf) != -1) ;

                        requestBodyee = RequestBody
                                .create(mediaType, buf);
                    } catch (IOException  e) {
                        e.printStackTrace();
                    }

                    awsViewModel.uploadFile(mediaType.toString(),presignUrlResponse.getUploadURL(),requestBodyee );
                }
                else
                {
                    Toast.makeText(MainActivity.this,"presignUrlResponse is null",Toast.LENGTH_LONG).show();
                }
            }
        });


        imgView = findViewById(R.id.imageView);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picturePath = "";
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                MainActivity.this.startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        btn = findViewById(R.id.button);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!picturePath.equals("") || !picturePath.isEmpty()){

                    String ext = picturePath.substring(picturePath.lastIndexOf(".") + 1);

                    Toast.makeText(getApplicationContext(),ext,Toast.LENGTH_LONG).show();
                    awsViewModel.callLambdaFunction(ext);

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            //String mimeType = getContentResolver().getType(selectedImage);
           // Log.v(getClass().getName(),mimeType);
            cursor.close();
            imgView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            btn.setVisibility(View.VISIBLE);
        }

    }
}