package com.example.hw3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.hw3.dummy.CarContent;

public class EditDialog extends DialogFragment {

    private String engine;
    private CarContent.Car mCar;

    public EditDialog(CarContent.Car car) {
        this.engine = car.engine;
        this.mCar = car;
    }

    static EditDialog newInstance(CarContent.Car car){
        return new EditDialog(car);
    }

    public interface OnEditDialogInteractionListener {
        void onEditDialogPositiveClick(DialogFragment dialog, CarContent.Car car);
        void onEditDialogNegativeClick(DialogFragment dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Edit "+ engine +" engine?");

        builder.setPositiveButton(getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OnEditDialogInteractionListener mListener =  (OnEditDialogInteractionListener) getActivity();
                mListener.onEditDialogPositiveClick(EditDialog.this, mCar);
            }
        });

        builder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OnEditDialogInteractionListener mListener =  (OnEditDialogInteractionListener) getActivity();
                mListener.onEditDialogNegativeClick(EditDialog.this);
            }
        });
        return builder.create();
    }
}
