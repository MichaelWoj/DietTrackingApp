package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodDBDisplay extends AppCompatActivity implements RecyclerViewInterface {

    private RecyclerView recyclerView;
    private ArrayList<String> foodID,foodNameDB, foodCaloriesNum, foodFatNum, foodCarbsNum, foodProteinNum;
    private DatabaseHelper dataBaseHelper;
    private FoodDBRecycleViewAdapter adapter;
    private Button addBtn, cancelBtn;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_food);
        dataBaseHelper = new DatabaseHelper(FoodDBDisplay.this);

        foodID = new ArrayList<>();
        foodNameDB = new ArrayList<>();
        foodCaloriesNum = new ArrayList<>();
        foodFatNum = new ArrayList<>();
        foodCarbsNum = new ArrayList<>();
        foodProteinNum = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerViewFoodList);
        adapter = new FoodDBRecycleViewAdapter(this,foodNameDB, foodCaloriesNum, foodFatNum, foodCarbsNum, foodProteinNum, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();

        addBtn = (Button) findViewById(R.id.dbAddFood);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodDBDisplay.this, FoodDBAddItem.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                finish();
                startActivity(intent);
            }
        });

        cancelBtn = (Button) findViewById(R.id.dbCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void displayData() {
        Cursor cursor = (Cursor) dataBaseHelper.getAllFoods();
        if(cursor.getCount()==0){
            Toast.makeText(FoodDBDisplay.this, "No Entry Found", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while (cursor.moveToNext()){
                foodID.add(cursor.getString(0));
                foodNameDB.add(cursor.getString(1));
                foodCaloriesNum.add(cursor.getString(2));
                foodFatNum.add(cursor.getString(3));
                foodCarbsNum.add(cursor.getString(4));
                foodProteinNum.add(cursor.getString(5));

            }
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(FoodDBDisplay.this, FoodDBItemPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        intent.putExtra("ID", foodID.get(position));
        intent.putExtra("Name", foodNameDB.get(position));
        intent.putExtra("Calories", foodCaloriesNum.get(position));
        intent.putExtra("Fat", foodFatNum.get(position));
        intent.putExtra("Carbs", foodCarbsNum.get(position));
        intent.putExtra("Protein", foodProteinNum.get(position));

        startActivity(intent);
        finish();
    }
}