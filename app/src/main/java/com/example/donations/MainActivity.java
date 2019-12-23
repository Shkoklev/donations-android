package com.example.donations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView bookIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareBookIconListener();
    }

    private void prepareBookIconListener() {
        bookIcon = findViewById(R.id.book_icon);
        bookIcon.setClickable(true);

        bookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DocumentationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showOrganizations(View view) {
        Intent intent = new Intent(MainActivity.this, OrganizationsActivity.class);
        startActivity(intent);
    }

}
