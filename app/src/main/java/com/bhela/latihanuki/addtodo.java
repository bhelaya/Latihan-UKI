package com.bhela.latihanuki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Locale;

public class addtodo extends AppCompatActivity {

    EditText edtTitle, edtDesc, edtDate;
    Button btnSubmit, btnCancel;
    DatabaseHelper myDb;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtodo);

        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDesc);
        edtDate = findViewById(R.id.edtDate);
        btnSubmit = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        myDb = new DatabaseHelper(this);
        myCalender = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfmonth) {

                myCalender.set(Calendar.YEAR, year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfmonth);
                updateLabel();// memanggil fungsi update Label

            }
        };

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(addtodo.this, date, myCalender.get(Calendar.YEAR),
                        myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String date = edtDate.getText().toString();
                String desc = edtDesc.getText().toString();
                if (title.equals("") || desc.equals("") || desc.equals("")){
                    if (title.equals("")){
                        edtTitle.setError("Judul harus di isi");
                    }
                    if (desc.equals("")){
                        edtDesc.setError("deskripsi harus di isi");
                    }
                    if (date.equals("")){
                        edtDate.setError("tanggal harus di isi");
                    }
                }else{
                    boolean isInterested = myDb.insertData(title, desc, date);
                    if (isInterested){
                        Toast.makeText(addtodo.this, "Data berhasil ditambakan", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(addtodo.this, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(addtodo.this, MainActivity.class));
                    finish();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addtodo.this,MainActivity.class));
                finish();
            }
        });

    }

    // Fungsi untuk mengupdate isi eddate value my calender
    private void updateLabel(){
        String myFormat = "dd-MM-yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        edtDate.setText(simpleDateFormat.format(myCalender.getTime()));
    }


}