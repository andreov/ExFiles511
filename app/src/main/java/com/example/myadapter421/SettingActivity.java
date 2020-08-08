package com.example.myadapter421;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.example.myadapter421.MainActivity.*;

public class SettingActivity extends AppCompatActivity {


    public static String[] dataSample;
    public EditText textSt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        textSt = findViewById(R.id.editTextSt);


        Button buttonSt=findViewById(R.id.buttonSt);
        buttonSt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 dataSample = textSt.getText().toString().split(";");
                 //Toast.makeText(SettingActivity.this,fileName,Toast.LENGTH_LONG).show();
                 MainActivity.loadItemData(dataSample);
                 saveText();

                finish();
            }
        });
    }
    public void saveText() {
        //досупность хранилища
        if (isExternalStorageWritable()) {
            //Получаем ссылку на файл
            File textSample = new File(getApplicationContext().getExternalFilesDir(null),"list12.txt");
            //Загружаем text
            FileWriter sampleWriter=null;
            String tempSave;
                try {
                    tempSave=textSt.getText().toString()+";";
                    sampleWriter= new FileWriter(textSample,true);
                    sampleWriter.append(tempSave);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            try {
                sampleWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}