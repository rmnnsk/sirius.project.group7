package com.example.myapplication;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

public class TaskActivity extends AppCompatActivity {
    //TODO Add Time of Deadline + calendar
    RecyclerView recyclerView;
    private int index = 0;
    private String date = "";
    private String id = "null";

    //Открываем базу данных
    final AppDatabase db = Room
            .databaseBuilder(getApplicationContext(), AppDatabase.class, "project.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Intent intent = getIntent();
        id = intent.getStringExtra("data");
        /**
         * @params String id is Card id. По нему можно взять данные из БД,если объекта не существует предложить ввести данные.
         */
        if(id != "null"){
            //TODO Show Data
        }
        final EditText editText = findViewById(R.id.checkBox);
        final MyAdapter adapter = setRecycleView(recyclerView,index,R.id.checkList);

        /**
         * @by_Ivan Эта кнопка открывает календарь
         */
        final Button btnDate = findViewById(R.id.buttonCalendar);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Start Calendar
            }
        });

        /**
         * @by_Ivan Сохраняющая кнопка,переход на основное активити
         */
        FloatingActionButton floatingActionButton = findViewById(R.id.buttonSave);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Here you can save all data from edit texts
                EditText shortDescriptionEdit = findViewById(R.id.editTextShort);
                String shortDescription = String.valueOf(shortDescriptionEdit.getText());
                EditText decriptionEdit = findViewById(R.id.editTextFull);
                String description = String.valueOf(decriptionEdit.getText());
                db.tasksDao().insertAll(new Tasks(shortDescription, System.currentTimeMillis(), id));
                db.taskInfoDao().insertAll(new taskInfo(description, id));
                saveData(date);
            }
        });
        /**
         * @by_Ivan  Кнопка,создающая новый checkbox
         */
        FloatingActionButton fab = findViewById(R.id.checkButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> newData = new ArrayList<>();
                String checkName = String.valueOf(editText.getText());
                //TODO Здесь нужно сохранить checkName в Базу Данных
                db.checkDao().insertAll(new Check(checkName, 0, id));
                newData.add(checkName); //TODO Remake Adapter to getting one String not ArrayList
                index++;
                adapter.addData(newData);
                /*adapter.notifyDataSetChanged();*/
                adapter.notifyItemInserted(index);
            }
        });
    }

    private void saveData(String input){
        if(isToday(input)){
            Intent intent = new Intent(TaskActivity.this,MainActivity.class);
            intent.putExtra("date","today");
            intent.putExtra("id",id);
            setResult(RESULT_OK,intent);
            finish();
        } else if(isTomorrow(input)){
            Intent intent = new Intent(TaskActivity.this,MainActivity.class);
            intent.putExtra("date","tomorrow");
            intent.putExtra("id",id);
            setResult(RESULT_OK,intent);
            finish();
        } else{
            Intent intent = new Intent(TaskActivity.this,MainActivity.class);
            intent.putExtra("date","future");
            intent.putExtra("id",id);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
    private boolean isToday(String in){
        //TODO Check data
        return false;
    }

    private boolean isTomorrow(String in){
        //TODO Check data
        return false;
    }
    private boolean isDataExist(String id){
        //TODO Check if Data Exist
        return false;
    }
    public MyAdapter setRecycleView(RecyclerView recycleView,int index,@IdRes int id){
        recycleView = findViewById(id);
        final MyAdapter adapter = new MyAdapter();
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.notifyDataSetChanged();
        return adapter;
    }
    public static class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        public String text;
        private final List<String> data;

        public MyAdapter() {
            data = new ArrayList<>();

        }


        public void addData(List<String> inData) {
            data.addAll(inData);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return MyViewHolder.create(parent);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.checkBox.setText(data.get(position));
            this.text = data.get(position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.tv_recycler_check);
        }

        public static MyViewHolder create(ViewGroup parent) {
            return new MyViewHolder( LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.check, parent, false));
        }
    }
}
