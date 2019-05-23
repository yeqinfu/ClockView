package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LineFillActivity extends AppCompatActivity {
      private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_fill);
       /* if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            int i= ContextCompat.checkSelfPermission(this,permissions[0]);
            if (i!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, permissions, 321);
            }
        }*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==321){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Log.d("yeqinfu","=======grantResults======");
            }else{
                Log.d("yeqinfu","=======not======");
                Path path=new Path();
            }
        }
    }
}
