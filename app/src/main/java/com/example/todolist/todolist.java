package com.example.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class todolist extends AppCompatActivity {
    ListView listView;
    EditText editTextTask;
    Button buttonAddTask, buttonDeleteSelected, gotodatabase;
    ArrayList<String> taskList = new ArrayList<>();
    ArrayAdapter<String> taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        // Check if the user is logged in by checking the SharedPreferences flag
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("flag", false);

        if (!isLoggedIn) {
            // If the user is not logged in, redirect to the login activity
            Intent intent = new Intent(todolist.this, login.class);
            startActivity(intent);
            finish();
            return;  // Exit the current activity
        }

        // Initialize Views
        listView = findViewById(R.id.listview);
        editTextTask = findViewById(R.id.editTextTask);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        buttonDeleteSelected = findViewById(R.id.buttonDeleteSelected);

        // Set up adapter for ListView
        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, taskList);
        listView.setAdapter(taskAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // Enable checkboxes

        // Initially hide the delete button
        buttonDeleteSelected.setVisibility(View.GONE);

        // Set up click listener for Add Task button
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString();
                if (!task.isEmpty()) {
                    addTaskWithNumbering(task); // Add task with numbering
                    taskAdapter.notifyDataSetChanged(); // Refresh ListView
                    editTextTask.setText(""); // Clear the EditText
                }
            }
        });

        // Set up item click listener to toggle delete button visibility
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Check if any items are selected
            SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
            boolean anySelected = false;

            for (int i = 0; i < checkedItems.size(); i++) {
                if (checkedItems.valueAt(i)) {
                    anySelected = true;
                    break;
                }
            }

            // Show or hide the delete button based on selection
            if (anySelected) {
                buttonDeleteSelected.setVisibility(View.VISIBLE);
            } else {
                buttonDeleteSelected.setVisibility(View.GONE);
            }
        });

        // Set up click listener for Delete Selected button
        buttonDeleteSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                ArrayList<String> itemsToRemove = new ArrayList<>();

                // Collect selected items to remove
                for (int i = 0; i < checkedItems.size(); i++) {
                    int position = checkedItems.keyAt(i);
                    if (checkedItems.valueAt(i)) {
                        itemsToRemove.add(taskList.get(position));
                    }
                }

                // Remove the selected items from the taskList
                taskList.removeAll(itemsToRemove);
                updateTaskNumbers(); // Re-number tasks after deletion

                // Refresh the ListView and hide delete button
                taskAdapter.notifyDataSetChanged();
                buttonDeleteSelected.setVisibility(View.GONE);

                Toast.makeText(todolist.this, "Selected tasks deleted", Toast.LENGTH_SHORT).show();
            }
        });

        // Optionally, retrieve the email and password passed from the login activity
        Intent intent = getIntent();
        String email = intent.getStringExtra("Email");
        String password = intent.getStringExtra("Password");

        // Use the email and password for any required logic (e.g., displaying the email on the screen or validation)
        // For example:
        if (email != null && password != null) {
            // You can display the email or password in a TextView or use it for any required logic
        }
    }

    // Method to add task with numbering
    private void addTaskWithNumbering(String task) {
        taskList.add((taskList.size() + 1) + ". " + task); // Add new task with numbering
    }

    // Method to update numbering after deletion
    private void updateTaskNumbers() {
        for (int i = 0; i < taskList.size(); i++) {
            String originalTask = taskList.get(i).replaceFirst("^[0-9]+\\. ", ""); // Remove old numbering
            taskList.set(i, (i + 1) + ". " + originalTask); // Update with new numbering
        }
    }
}
