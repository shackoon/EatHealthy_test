package com.example.admin.eathealthy;

import android.os.Bundle;
import android.service.autofill.Dataset;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.admin.eathealthy.CustomItem.RecipeItem;
import com.example.admin.eathealthy.News.CardViewItem;
import com.example.admin.eathealthy.RecycleViewCustomAdapter.AddFoodAdapter;
import com.example.admin.eathealthy.RecycleViewCustomAdapter.RecipeAdapter;

import java.util.ArrayList;

public class RecipePlatform extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecipeAdapter myAdapter;
    private ArrayList<RecipeItem> Dataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recpie);

        setData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recipe_recyclerView);
        myAdapter = new RecipeAdapter(Dataset);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); //設定此 layoutManager 為 linearlayout (類似ListView)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL); //設定此 layoutManager 為垂直堆疊
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(myAdapter);
    }

    private void setData() {
        Dataset.clear();
        int[] pic_food = {R.drawable.bbq, R.drawable.frychicken, R.drawable.tofu, R.drawable.beef, R.drawable.soup};
        String[] food_name = {getString(R.string.Recipe1), getString(R.string.Recipe2), getString(R.string.Recipe3), getString(R.string.Recipe4), getString(R.string.Recipe5)};
        int[] pic_yn = {R.drawable.yes, R.drawable.yes, R.drawable.no, R.drawable.yes, R.drawable.no};

        for (int i = 0; i < pic_food.length; i++) {
            RecipeItem recipeItem = new RecipeItem(pic_food[i], food_name[i], pic_yn[i]);
            Dataset.add(recipeItem);
        }

    }


}


