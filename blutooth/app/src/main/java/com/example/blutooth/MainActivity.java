package com.example.blutooth;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    CheckBox enable, visible;
    ImageView bluetooth;
    ListView listView;
    TextView name;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> set;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enable = (CheckBox) findViewById(R.id.enable);
        visible = (CheckBox) findViewById(R.id.vivible);
        listView = (ListView) findViewById(R.id.list_view);
        bluetooth = (ImageView) findViewById(R.id.bluetooth);
        name  = (TextView) findViewById(R.id.name);

        name.setText(get());

        BA = BluetoothAdapter.getDefaultAdapter();
        if (BA==null){
            Toast.makeText(MainActivity.this, "Blutooth not support", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (BA.isEnabled()){
            enable.setChecked(true);
        }
        enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    BA.disable();
                    Toast.makeText(MainActivity.this, "Turned off", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,0);
                    Toast.makeText(MainActivity.this, "Turned on", Toast.LENGTH_SHORT).show();

                }
            }


        });
        visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    Intent intent2 = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent2,0);
                    Toast.makeText(MainActivity.this, "visible for 2 min", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list();
            }
        });

    }
    private void list(){
        set = BA.getBondedDevices();
        ArrayList list = new ArrayList();
        for (BluetoothDevice dv : set){
            list.add(dv.getName());
            Toast.makeText(MainActivity.this, "Showing device", Toast.LENGTH_SHORT).show();
            ArrayAdapter array = new ArrayAdapter(this, android.R.layout.simple_list_item_1,list);

        }
    }
    public String get(){
        if(BA ==null){
            BA = BluetoothAdapter.getDefaultAdapter();

        }
        String name = BA.getName();
        if(name == null){
            name = BA.getAddress();
        }
        return name;
    }
}