package com.example.android.booklisting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText search_edit_text=(EditText)findViewById(R.id.search_edit_text);


        final EditText max_edit_text=(EditText)findViewById(R.id.max_result);


        Button search_button=(Button)findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_search = search_edit_text.getText().toString();
                String input_max = max_edit_text.getText().toString();

                if(isEmpty(max_edit_text))
                    input_max="10";

                if(isEmpty(search_edit_text)){
                    Toast.makeText(MainActivity.this,"Fill Search Book",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.v(LOG_TAG,"-----+++++------++++++");
                    Intent intent = new Intent(MainActivity.this,DisplayBook.class);
                    intent.putExtra("search", input_search);
                    intent.putExtra("max",input_max);
                    startActivity(intent);
                }
            }
        });
    }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
}
