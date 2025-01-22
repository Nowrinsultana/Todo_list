package com.example.todolist;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class database_sqlite extends AppCompatActivity {
    private Button button;
    private EditText Database_name, Course_name;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_database_sqlite);

        button = findViewById(R.id.button);
        Database_name = findViewById(R.id.database_name);
        Course_name = findViewById(R.id.Course_name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dbname = Database_name.getText().toString();
                String course_name = Course_name.getText().toString();
                database_helper db = new database_helper(database_sqlite.this);
                //SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                db.addStudent(dbname, course_name);

            }
        });

    }
}