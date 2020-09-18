package com.example.hw3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw3.dummy.CarContent;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        addCar();
    }

    @SuppressLint("WrongViewCast")
    private void addCar() {

        Button addButton;

        addButton = findViewById(R.id.addCarButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText EngineEditTxt;
                EditText CarEditTxt;
                EditText LengthEditTxt;
                EditText WidthEditTxt;

                EngineEditTxt = findViewById(R.id.newEngine);
                CarEditTxt = findViewById(R.id.newCar);
                LengthEditTxt = findViewById(R.id.newLength);
                WidthEditTxt = findViewById(R.id.newWidth);

                String engine;
                String car;
                String length;
                String width;

                engine = EngineEditTxt.getText().toString();
                car = CarEditTxt.getText().toString();
                length = LengthEditTxt.getText().toString();
                width = WidthEditTxt.getText().toString();


                if(car.isEmpty()){
                    CarEditTxt.setError(getString(R.string.thisFieldCannotBeEmpty));
                    return;
                }


                CarContent.Car newCar = new CarContent.Car(engine, car, length, width);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Cars").document(Integer.toString(newCar.ID)).set(newCar);


                EngineEditTxt.setText("");
                CarEditTxt.setText("");
                LengthEditTxt.setText("");
                WidthEditTxt.setText("");

                finish();
            }
        });
    }
}
