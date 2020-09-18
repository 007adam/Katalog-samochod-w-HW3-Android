package com.example.hw3.dummy;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CarContent {


    public static final List<Car> CARS = new ArrayList<Car>();

    public static int lastID;

    public static void addItem(Car item) {
        CARS.add(item);
    }

    public static void deleteItem(int position) {

        Car temp = CARS.get(position);
        int id = temp.ID;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Cars").document(Integer.toString(id)).delete();
        CARS.remove(position);
    }

    public static class Car implements Parcelable {
        public final int ID;
        public final String engine;
        public final String Brand;
        public final String Length;
        public final String Width;

        public Car(int id, String engine, String Brand, String Length, String Width) {
            this.ID = id;
            this.engine = engine;
            this.Brand = Brand;
            this.Length = Length;
            this.Width = Width;
        }

        public Car(String engine, String Brand, String Length, String Width) {
            this.ID = lastID;
            this.engine = engine;
            this.Brand = Brand;
            this.Length = Length;
            this.Width = Width;
        }

        protected Car(Parcel in) {
            ID = in.readInt();
            engine = in.readString();
            Brand = in.readString();
            Length = in.readString();
            Width = in.readString();
        }

        public static final Creator<Car> CREATOR = new Creator<Car>() {
            @Override
            public Car createFromParcel(Parcel in) {
                return new Car(in);
            }

            @Override
            public Car[] newArray(int size) {
                return new Car[size];
            }
        };

        public static void clear() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ID);
            dest.writeString(engine);
            dest.writeString(Brand);
            dest.writeString(Length);
            dest.writeString(Width);
        }

    }

}
