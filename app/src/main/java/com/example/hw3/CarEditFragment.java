package com.example.hw3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.hw3.dummy.CarContent;


public class CarEditFragment extends Fragment {

    private int id;
    private String engine;
    private String car1;
    private String length;
    private String width;

    private EditText engineEditTxt;
    private EditText carEditTxt;
    private EditText lengthEditTxt;
    private EditText widthEditTxt;
    private Button saveChangesBtn;

    public CarEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_edit, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity activity = getActivity();

        engineEditTxt = activity.findViewById(R.id.engine);
        carEditTxt = activity.findViewById(R.id.car);
        lengthEditTxt = activity.findViewById(R.id.Length);
        widthEditTxt = activity.findViewById(R.id.Width);

        saveChangesBtn = activity.findViewById(R.id.saveChangesButton);

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        Intent intent = getActivity().getIntent();
        if(intent != null){
            CarContent.Car recivedCar = intent.getParcelableExtra(MainActivity.CarExtra);
            if(recivedCar != null) {
                displayCar(recivedCar);
            }
        }

    }

    public void displayCar(CarContent.Car car){

        id = car.ID;
        engine = car.engine;
        car1 = car.Brand;
        length = car.Length;
        width = car.Width;

       engineEditTxt.setText(engine);
        carEditTxt.setText(car1);
        lengthEditTxt.setText(length);
        widthEditTxt.setText(width);
    }

    public void saveChanges(){


        engine = engineEditTxt.getText().toString();
        car1 = carEditTxt.getText().toString();
        length = lengthEditTxt.getText().toString();
        width = widthEditTxt.getText().toString();

        // TODO save changes to db
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Cars").document(Integer.toString(id));

        docRef.update("Engine", engine,
                "Brand", car1,
                "Length", length,
                "Width", width);

        getActivity().finish();
    }
}
