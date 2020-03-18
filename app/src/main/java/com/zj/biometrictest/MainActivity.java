package com.zj.biometrictest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private BiometricPrompt biometricPrompt;
    private CancellationSignal cancellationSignal;
    private BiometricPrompt.AuthenticationCallback authenticationCallback;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void init() {
        biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("指纹识别")
                .setDescription("开始")
                .setNegativeButton("cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        cancellationSignal=new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "取消le....", Toast.LENGTH_SHORT).show();
            }
        });

        authenticationCallback=new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e(TAG,"onAuthenticationError");
                Toast.makeText(MainActivity.this, "onAuthenticationError....", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
                Log.e(TAG,"onAuthenticationHelp");
                Toast.makeText(MainActivity.this, "onAuthenticationHelp....", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.e(TAG,"onAuthenticationSucceeded");
                Toast.makeText(MainActivity.this, "onAuthenticationSucceeded....", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.e(TAG,"onAuthenticationFailed");
                Toast.makeText(MainActivity.this, "onAuthenticationFailed....", Toast.LENGTH_SHORT).show();
            }
        };
        biometricPrompt.authenticate(cancellationSignal,getMainExecutor(),authenticationCallback);

    }
}
