package com.example.myadapter421;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class MainActivity extends AppCompatActivity {


   // private Random random = new Random();
    public static final int REQUEST_CODE_PERMISSION = 11;

    private static ItemsDataAdapter adapter;

    private static List<Drawable> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        ListView listView = findViewById(R.id.listView);
        fillImages();



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });


        adapter = new ItemsDataAdapter(this,null);
        listView.setAdapter(adapter);
        //generateRandomItemData();
        //dataSample=new String[]{"aaaaaaa","dddddddd"};
        int permissionStatus= ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionStatus== PackageManager.PERMISSION_GRANTED){
            try {
                loadText();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_PERMISSION);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showItemData(position);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ItemData itemData = adapter.getItem(position);
                Toast.makeText(MainActivity.this,
                        "Title: " + itemData.getTitle() + "\n" +
                                "Долгое нажатие на элемент списка",
                        Toast.LENGTH_SHORT).show();
                //adapter.removeItem(position);
                return true;
            }
        });
    }

    private void fillImages() {
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_report_image));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_add));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_agenda));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_camera));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_call));
    }

    public static void loadItemData(String[] dataSample) {
        //for (int i = 0; i < images.size(); i++)
        adapter.addItem(new ItemData(images.get(0),dataSample[0],dataSample[1]));
        adapter.notifyDataSetChanged();

    }


    private void showItemData(int position) {
        ItemData itemData = adapter.getItem(position);
        Toast.makeText(MainActivity.this,
                "Title: " + itemData.getTitle() + "\n" +
                        "Subtitle: " + itemData.getSubtitle(),
                Toast.LENGTH_SHORT).show();
    }


    public void loadText()throws IOException {
        //досупность хранилища
        if(isExternalStorageReadable()){
            File textSample = new File(getApplicationContext().getExternalFilesDir(null),"list12.txt");
            if(!textSample.exists()) return;
            //Загружаем text
            FileReader sampleRead=null;
            StringBuilder text = new StringBuilder();
            String line;
            sampleRead= new FileReader(textSample);
            BufferedReader br = new BufferedReader(sampleRead);
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            sampleRead.close();
            String[]dataAdapter = text.toString().split(";");
            for (int i=0;i<dataAdapter.length;i=i+2) {
                adapter.addItem(new ItemData(images.get(0), dataAdapter[i], dataAdapter[i+1]));
                adapter.notifyDataSetChanged();
            }
            Toast.makeText(MainActivity.this,text.toString(),Toast.LENGTH_LONG).show();


        }
    }



    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }



    //что ответил пользователь
    @Override
    public void onRequestPermissionsResult(int requestCode, String[]permissions,int[]grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode){
            case REQUEST_CODE_PERMISSION:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //saveText();
                }else {
                    Toast.makeText(MainActivity.this,"У вас нет разрешения на чтение",Toast.LENGTH_LONG).show();
                    //finish();
                }
        }
    }
}