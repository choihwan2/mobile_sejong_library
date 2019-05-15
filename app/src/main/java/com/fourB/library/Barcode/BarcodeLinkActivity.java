package com.fourB.library.Barcode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fourB.library.R;

public class BarcodeLinkActivity extends AppCompatActivity {

    private Button mBorrowBtn;
    private Button mCancelBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcord_book_borrow);

        setTitle("책 대여");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBorrowBtn = (Button)findViewById(R.id.barcode_borrowBtn);
        mBorrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "책 빌림!!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        mCancelBtn = (Button)findViewById(R.id.barcode_cancelBtn);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "대여 취소!!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
