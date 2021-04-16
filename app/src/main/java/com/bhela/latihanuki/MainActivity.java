package com.bhela.latihanuki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Todo> list = new ArrayList<Todo>();
    TodoAdapter todoAdapter;
    DatabaseHelper myDb;
    RecyclerView ourtodo;
    FloatingActionButton btfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btfab = findViewById(R.id.fab);
        myDb = new DatabaseHelper(this);
        list.addAll(myDb.getAllData());
        ourtodo = findViewById(R.id.rvTodo);
        ourtodo.setLayoutManager(new LinearLayoutManager(this));
        todoAdapter = new TodoAdapter(MainActivity.this,list);
        todoAdapter.notifyDataSetChanged();
        ourtodo.setAdapter(todoAdapter);

        btfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentfab = new Intent(MainActivity.this, addtodo.class);
                startActivity(intentfab);
            }
        });
    }
}