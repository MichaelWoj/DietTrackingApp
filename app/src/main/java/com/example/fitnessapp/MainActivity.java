            package com.example.fitnessapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

    public class MainActivity extends AppCompatActivity{
    private TextView setCalories, setProtein, setFat, setCarbs ;
    private EditText userTargetCalories, userTargetCarbs, userTargetFat, userTargetProtein;
    private Button addFoodBtn, manualFoodBtn, undoFoodBtn, resetAllBtn;

    ImageButton lockBtn;
    private boolean isLocked = true;

    private float caloriesVal = 0f;
    private float proteinVal = 0f;
    private float fatVal = 0f;
    private float carbsVal = 0f;

    public static final String savedValCalories = "calories";
    public static final String savedValProtein = "protein";
    public static final String savedValFat = "fat";
    public static final String savedValCarbs = "carbs";

    private float foodCaloriesVal;
    private float foodProteinVal;
    private float foodFatVal;
    private float foodCarbsVal;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                Intent intent = result.getData();
                if(intent != null){
                    foodCaloriesVal = intent.getFloatExtra("foodCalories", 0f);
                    caloriesVal = caloriesVal + foodCaloriesVal;
                    caloriesVal = (float) (Math.round(caloriesVal * 100.0) / 100.0);

                    foodProteinVal = intent.getFloatExtra("foodProtein", 0f);
                    proteinVal = proteinVal + foodProteinVal;
                    proteinVal = (float) (Math.round(proteinVal * 100.0) / 100.0);

                    foodFatVal = intent.getFloatExtra("foodFat", 0f);
                    fatVal = fatVal + foodFatVal;
                    fatVal = (float) (Math.round(fatVal * 100.0) / 100.0);

                    foodCarbsVal = intent.getFloatExtra("foodCarbs", 0f);
                    carbsVal = carbsVal + foodCarbsVal;
                    carbsVal = (float) (Math.round(carbsVal * 100.0) / 100.0);

                    setValues();
                    saveSharedPreferences();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        setCalories = findViewById(R.id.calories);
        setCalories.setText(String.valueOf(caloriesVal));

        setProtein = findViewById(R.id.protein);
        setProtein.setText(String.valueOf(proteinVal));

        setFat = findViewById(R.id.fat);
        setFat.setText(String.valueOf(fatVal));

        setCarbs =  findViewById(R.id.carbs);
        setCarbs.setText(String.valueOf(carbsVal));

        userTargetCalories = findViewById(R.id.targetCalories);
        userTargetCalories.setEnabled(false);

        userTargetCarbs = findViewById(R.id.targetCarbs);
        userTargetCarbs.setEnabled(false);

        userTargetFat = findViewById(R.id.targetFat);
        userTargetFat.setEnabled(false);

        userTargetProtein = findViewById(R.id.targetProtein);
        userTargetProtein.setEnabled(false);

        lockBtn=findViewById(R.id.lockButton);
        lockBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(isLocked){

                    isLocked=false;
                    lockBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_open_lock));

                    userTargetCalories.setEnabled(true);
                    userTargetCalories.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.black));;

                    userTargetCarbs.setEnabled(true);
                    userTargetCarbs.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.black));

                    userTargetFat.setEnabled(true);
                    userTargetFat.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.black));

                    userTargetProtein.setEnabled(true);
                    userTargetProtein.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.black));

                }else{

                    isLocked=true;
                    lockBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock));

                    userTargetCalories.setEnabled(false);
                    userTargetCalories.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.transparent));;

                    userTargetCarbs.setEnabled(false);
                    userTargetCarbs.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.transparent));

                    userTargetFat.setEnabled(false);
                    userTargetFat.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.transparent));

                    userTargetProtein.setEnabled(false);
                    userTargetProtein.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.transparent));
                }
            }
        });

        addFoodBtn = (Button) findViewById(R.id.searchFood);
        addFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodDBDisplay.class);
                startForResult.launch(intent);
            }
        });

        manualFoodBtn = (Button) findViewById(R.id.manualAdd);
        manualFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManuallyAdd.class);
                startForResult.launch(intent);
            }
        });

        undoFoodBtn = (Button) findViewById(R.id.undoFood);
        undoFoodBtn.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v) {
                if (caloriesVal != 0 ){
                    foodCaloriesVal = (float) (Math.floor(foodCaloriesVal * 100) / 100);
                    caloriesVal = caloriesVal - foodCaloriesVal;
                    caloriesVal = (float) (Math.floor(caloriesVal * 100) / 100);

                    foodProteinVal = (float) (Math.floor(foodProteinVal * 100) / 100);
                    proteinVal = proteinVal - foodProteinVal;
                    proteinVal = (float) (Math.floor(proteinVal * 100) / 100);

                    foodFatVal = (float) (Math.floor(foodFatVal * 100) / 100);
                    fatVal = fatVal - foodFatVal;
                    fatVal = (float) (Math.floor(fatVal * 100) / 100);

                    foodCarbsVal = (float) (Math.floor(foodCarbsVal * 100) / 100);
                    carbsVal = carbsVal - foodCarbsVal;
                    carbsVal = (float) (Math.floor(carbsVal * 100) / 100);

                    setValues();
                    saveSharedPreferences();

                    foodCaloriesVal = 0;
                    foodProteinVal = 0;
                    foodFatVal = 0;
                    foodCarbsVal = 0;
                };
            }
        });


        resetAllBtn = (Button) findViewById(R.id.resetAll);
        resetAllBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                 caloriesVal = 0;
                 proteinVal = 0;
                 fatVal = 0;
                 carbsVal = 0;

                 setValues();
                 saveSharedPreferences();

            }
        });
    }
        public void saveSharedPreferences(){
            SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putFloat(savedValCalories, caloriesVal);
            editor.putFloat(savedValProtein, proteinVal);
            editor.putFloat(savedValFat, fatVal);
            editor.putFloat(savedValCarbs, carbsVal);

            editor.apply();
        }

        public void loadData() {
            SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
            caloriesVal = sharedPreferences.getFloat(savedValCalories,0f);
            proteinVal = sharedPreferences.getFloat(savedValProtein,0f);
            fatVal = sharedPreferences.getFloat(savedValFat,0f);
            carbsVal  = sharedPreferences.getFloat(savedValCarbs,0f);
        }
        private void setValues() {
            setCalories.setText(String.valueOf(caloriesVal));
            setProtein.setText(String.valueOf(proteinVal));
            setFat.setText(String.valueOf(fatVal));
            setCarbs.setText(String.valueOf(carbsVal));
        }

    }
