package com.example.hw3;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.hw3.dummy.CarContent;

import java.util.List;

public class MyCarRecyclerViewAdapter extends RecyclerView.Adapter<MyCarRecyclerViewAdapter.ViewHolder> {

    private final List<CarContent.Car> mValues;
    public final CarFragment.OnListFragmentInteractionListener mListener;

    public MyCarRecyclerViewAdapter(List<CarContent.Car> items, CarFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_car, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        CarContent.Car mCar = mValues.get(position);
        holder.mItem = mCar;
        holder.mCarView.setText(mCar.Brand);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentClickInteraction(holder.mItem, v);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.onListFragmentLongClickInteraction(holder.mItem);
                return false;
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteButtonClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final ImageView mAvatarView;
        public final TextView mCarView;
        public CarContent.Car mItem;
        public ImageButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            view.setBackgroundColor(Color.rgb(60, 200, 50));
            mCarView = (TextView) view.findViewById(R.id.car_brand);
            deleteButton = view.findViewById(R.id.car_delete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCarView.getText() + "'";
        }
    }
}
