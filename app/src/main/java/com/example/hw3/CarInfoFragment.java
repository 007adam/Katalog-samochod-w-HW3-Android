package com.example.hw3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.hw3.dummy.CarContent;


public class CarInfoFragment extends Fragment {

    public CarInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_info, container, false);
    }

    public void displayCar(CarContent.Car car){
        FragmentActivity activity = getActivity();

        TextView engineEditTxt = activity.findViewById(R.id.engine);
        TextView carEditTxt = activity.findViewById(R.id.car);
        TextView lengthEditTxt = activity.findViewById(R.id.length);
        TextView widthEditTxt = activity.findViewById(R.id.width);

        String engine = car.engine;
        String car1 = car.Brand;
        String length = car.Length;
        String width = car.Width;
        engineEditTxt.setText("Engine: "+engine);
        carEditTxt.setText("Brand and model: "+car1);
        lengthEditTxt.setText("Length: "+length);
        widthEditTxt.setText("Width: "+width);
    }

    public void displayEmptyCar(){
        FragmentActivity activity = getActivity();

        TextView engineEditTxt = activity.findViewById(R.id.engine);
        TextView carEditTxt = activity.findViewById(R.id.car);
        TextView lengthEditTxt = activity.findViewById(R.id.length);
        TextView widthEditTxt = activity.findViewById(R.id.width);

        engineEditTxt.setText("");
        carEditTxt.setText("");
        lengthEditTxt.setText("");
        widthEditTxt.setText("");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        if(intent != null){
           CarContent.Car recivedCar = intent.getParcelableExtra(MainActivity.CarExtra);
            if(recivedCar != null) {
                displayCar(recivedCar);
            }
        }
    }

}
