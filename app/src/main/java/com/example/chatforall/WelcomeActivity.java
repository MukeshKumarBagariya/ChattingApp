package com.example.chatforall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private ListView list;
    private ArrayList<String> usersName;
    private ArrayAdapter arrayAdapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        list = findViewById(R.id.list);
        swipeRefresh = findViewById(R.id.swipRefresh);
        usersName = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(WelcomeActivity.this,android.R.layout.simple_list_item_1,usersName);
        final ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null && objects.size() > 0){
                    for (ParseUser users : objects){
                        usersName.add(users.getUsername());
                    }
                    list.setAdapter(arrayAdapter);
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    ParseQuery<ParseUser> parseQuery1 = ParseUser.getQuery();
                    parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
                    parseQuery.whereNotContainedIn("username",usersName);
                    parseQuery.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                            if (objects.size() > 0){
                                if (e == null){
                                    for (ParseUser userss : objects){
                                        usersName.add(userss.getUsername());
                                    }
                                    arrayAdapter.notifyDataSetChanged();
                                    if (swipeRefresh.isRefreshing()){
                                        swipeRefresh.setRefreshing(false);
                                    }
                                }
                            } else  {
                                if (swipeRefresh.isRefreshing()){
                                    swipeRefresh.setRefreshing(false);
                                }
                            }
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tab_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutButton:
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        Intent intent = new Intent(WelcomeActivity.this,SignUpActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }
}
