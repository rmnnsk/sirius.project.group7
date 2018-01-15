package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import  android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "myLog";
    private int indexToday = 5,
                      indexTomorrow = 3,
                      indexFuture = 3;
    RecyclerView listToday, listTomorrow, listFuture;
    ArrayList<String> indexList = new ArrayList<>();
    MyAdapter adapterToday, adapterTomorrow, adapterFuture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapterToday =  setRecycleView(listToday,indexToday,R.id.listToday);
        adapterTomorrow = setRecycleView(listTomorrow,indexTomorrow,R.id.listTomorrow);
        adapterFuture = setRecycleView(listFuture,indexFuture,R.id.listFuture);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                intent.putExtra("data", "null");
                startActivityForResult(intent,1);
            }
        });
    }
    public MyAdapter  setRecycleView(RecyclerView recycleView,int index,@IdRes int id){
        recycleView = findViewById(id);
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            /**
             * @param Here you can send Data from DB
             */
            if(indexList.isEmpty()){
                data.add("Item id = 0");
                indexList.add("0");
            }
            else {
                data.add("Item id = " +
                        String.valueOf(indexList.size()));
                indexList.add(String.valueOf(indexList.size()));
                Log.d(TAG,String.valueOf(data.size()));
            }
        }
        final MyAdapter adapter = new MyAdapter();
        adapter.addData(data);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.notifyDataSetChanged();
        final Context activity = getApplicationContext();
        recycleView.addOnItemTouchListener(
                new RecyclerItemClickListener(activity, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        String data_id = adapter.data.get(position);
                        Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                        intent.putExtra("data", data_id);
                        startActivityForResult(intent,0);
                    }
                })
        );
        return adapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
             if(requestCode == 0){
                 //TODO Change already existing card
                 /**
                  * You can get id with getStringExtra("id") and data with getStringExtra("date")
                  */
             }
             else{
                 //TODO Create new card
                 String time = data.getStringExtra("date");
                 ArrayList<String> newData = new ArrayList<>();
                 //TODO Взять описание карточки по id
                 String card_id = data.getStringExtra("id");
                 if (time == "today") {

                 }
                 if(time == "tomorrow"){

                 }
                 if(time == "future"){
                     //TODO Тестовый режим
                     newData.add(card_id);
                     indexFuture++;
                     adapterFuture.addData(newData);
                     adapterFuture.notifyItemInserted(indexFuture);
                 }

             }
        }
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

        private OnItemClickListener mListener;
        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }
        GestureDetector mGestureDetector;
        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        public String text;
        private List<String> data;

        public MyAdapter() {
            data = new ArrayList<>();
        }


        public void addData(List<String> inData) {
            data = inData;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return MyViewHolder.create(parent);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(data.get(position));
            this.text = data.get(position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_recycler_item);
        }

        public static MyViewHolder create(ViewGroup parent) {
            return new MyViewHolder( LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item, parent, false));
        }
    }
}