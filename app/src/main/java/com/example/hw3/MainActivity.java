package com.example.hw3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.hw3.dummy.CarContent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static com.example.hw3.dummy.CarContent.CARS;

public class MainActivity extends AppCompatActivity implements
        CarFragment.OnListFragmentInteractionListener,
        DeleteDialog.OnDeleteDialogInteractionListener,
        EditDialog.OnEditDialogInteractionListener {

    int selectedDeleteItem = -1;
    public static  String CarExtra = "CarExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddCarActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        getFirebaseData();

    }
    public void getFirebaseData(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Cars")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            CARS.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Map<String, Object> car = new HashMap<>();
                                car = document.getData();

                                Object oId = car.get("ID");
                                Object oEngine = car.get("engine");
                                Object oCar = car.get("Brand");
                                Object oLength = car.get("Length");
                                Object oWidth = car.get("Width");

                                int id = Integer.parseInt(oId.toString());
                                String engine = oEngine.toString();
                                String car1 = oCar.toString();
                                String length = oLength.toString();
                                String width = oWidth.toString();
                                CarContent.Car carFromFireBase = new CarContent.Car(id, engine, car1, length, width);
                                CarContent.addItem(carFromFireBase);
                                CarContent.lastID = id+1;
                            }
                            ((CarFragment) getSupportFragmentManager().findFragmentById(R.id.CarFragment)).notifyDataChange();
                        }

                    }
                });

    }

    public void startCarInfoActivity(CarContent.Car car){
        Intent intent = new Intent(this, CarInfoActivity.class);
        intent.putExtra(CarExtra, car);
        startActivity(intent);
    }

    public void startEditCarActivity(CarContent.Car car){
        Intent intent = new Intent(this, EditCarActivity.class);
        intent.putExtra(CarExtra, car);
        startActivity(intent);
    }

    @Override
    public void onListFragmentClickInteraction(CarContent.Car item, View view) {

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            displayCarInFragment(item);
        } else {
            startCarInfoActivity(item);
        }
    }

    @Override
    public void onListFragmentLongClickInteraction(CarContent.Car item) {
        EditDialog.newInstance(item).show(getSupportFragmentManager(), getString(R.string.callingdialog));
    }

    @Override
    public void onDeleteButtonClick(int position) {
        selectedDeleteItem = position;
        DeleteDialog.newInstance().show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (selectedDeleteItem >= 0 && selectedDeleteItem < CARS.size()) {
            CarContent.deleteItem(selectedDeleteItem);


            ((CarFragment) getSupportFragmentManager().findFragmentById(R.id.CarFragment)).notifyDataChange();

            CarInfoFragment carInfoFragment = ( (CarInfoFragment) getSupportFragmentManager().findFragmentById(R.id.CarInfoFragment));
            if(carInfoFragment != null){
                carInfoFragment.displayEmptyCar();
            }
        }
    }

    public void displayCarInFragment(CarContent.Car car){
        CarInfoFragment carInfoFragment = ( (CarInfoFragment) getSupportFragmentManager().findFragmentById(R.id.CarInfoFragment));
        if(carInfoFragment != null){
            carInfoFragment.displayCar(car);
        }
    }

    @Override
    public void onEditDialogPositiveClick(DialogFragment dialog, CarContent.Car car) {

        startEditCarActivity(car);

    }
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onEditDialogNegativeClick(DialogFragment dialog) {

    }

}
