package com.example.fetchrewards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";
    private RecyclerView mainRecyclerView; //recycler view that holds each section
    private List<Section> listOfSections = new ArrayList<>(); //
    private MainRecyclerAdapter mainRecyclerAdapter;
    private LinearLayoutManager layoutManager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRecyclerView = findViewById(R.id.mainRecycler);
        mainRecyclerAdapter = new MainRecyclerAdapter(listOfSections, this);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
        layoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mainRecyclerView.addItemDecoration(new VerticalSpaceDecoration(20));

        ListDownloader listDownloader = new ListDownloader(this);
        new Thread(listDownloader).start();

    }

    public void loadItemMap(Map<Integer, List<ListItem>> treeMap)
    {
        for (Map.Entry<Integer, List<ListItem>> e : treeMap.entrySet()) {
            List<ListItem> groupList = e.getValue();
            Collections.sort(groupList);
            Section newSection = new Section(String.valueOf(e.getKey()), groupList);
            listOfSections.add(newSection);
        }
        mainRecyclerAdapter.notifyDataSetChanged();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.group_1)
                    layoutManager.scrollToPositionWithOffset(0,0);
                else  if (item.getItemId() == R.id.group_2)
                    layoutManager.scrollToPositionWithOffset(1,0);
                else  if (item.getItemId() == R.id.group_3)
                    layoutManager.scrollToPositionWithOffset(2,0);
                else  if (item.getItemId() == R.id.group_4)
                    layoutManager.scrollToPositionWithOffset(3,0);
                return true;
            }
        });
    }
}