package com.example.ntasks.PO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ntasks.R;

public class newPOmaster extends AppCompatActivity {

    private Button button_clipo, button_compo, button_penpo, button_addPo, button_allPo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pomaster);

        button_clipo=findViewById(R.id.button_clipo);
        button_compo=findViewById(R.id.button_compo);
        button_penpo=findViewById(R.id.buttonpenpo);
        button_addPo=findViewById(R.id.button_addPo);
        button_allPo=findViewById(R.id.buttonShowAllPOs);



        button_clipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(newPOmaster.this, POmaster.class);
                startActivity(intent1);
            }
        });

        button_addPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(newPOmaster.this, AddPOActivity.class);
                startActivity(intent2);
            }
        });
        button_compo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(newPOmaster.this, CompletedPOActivity.class);
                startActivity(intent5);
            }
        });
        button_penpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent9 = new Intent(newPOmaster.this, PendingPOActivity.class);
                startActivity(intent9);
            }
        });
        button_allPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent91 = new Intent(newPOmaster.this, PendingPOActivity.class);
                startActivity(intent91);
            }
        });


    }
}