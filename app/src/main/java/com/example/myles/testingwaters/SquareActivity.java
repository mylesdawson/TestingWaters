package com.example.myles.testingwaters;

import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class SquareActivity extends AppCompatActivity {

    Button submitBtn;
    EditText editTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        editTxt = (EditText) findViewById(R.id.number_of_squares);

        submitBtn = (Button) findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginGame();
            }
        });
    }

    public void beginGame() {
        if (editTxt.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter a number between 1 and 15", Toast.LENGTH_SHORT).show();
        } else {
            int numBtns = Integer.parseInt(editTxt.getText().toString());
            if (numBtns < 16) {
                populateButtons(numBtns);
            } else {
                Toast.makeText(getApplicationContext(), "Please enter a number between 1 and 15", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void populateButtons(int numButtons) {

        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.game_board);
        parentLayout.removeAllViews();

        for (int i = 0; i < numButtons; i++) {
            // creates a new row
            LinearLayout linLayout = new LinearLayout(this);
            linLayout.setOrientation(LinearLayout.HORIZONTAL);
            parentLayout.addView(linLayout);
            for (int j = 0; j < numButtons; j++) {
                Button btn = new Button(this);
                btn.setWidth(0);
                btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                if (j >= i) {
                    btn.getBackground().setColorFilter(0xff00ff00, PorterDuff.Mode.MULTIPLY);
                }
                linLayout.addView(btn);
            }
        }
    }
}
