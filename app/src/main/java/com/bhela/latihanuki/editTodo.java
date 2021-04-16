package com.bhela.latihanuki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class editTodo extends AppCompatActivity {

    EditText edtTitle, edtDesc, edtDate;
    Button btnUpdate, btnDelete;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalender;
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);


        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDesc);
        edtDate = findViewById(R.id.edtDate);
        btnUpdate = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        myDb = new DatabaseHelper(this);

        edtTitle.setText(getIntent().getStringExtra("titletodo"));
        edtDesc.setText(getIntent().getStringExtra("desctodo"));
        edtDate.setText(getIntent().getStringExtra("datetodo"));

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
                new DatePickerDialog(editTodo.this, date, myCalender.get(Calendar.YEAR),
                        myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String desc = edtDesc.getText().toString();
                String date = edtDate.getText().toString();
                String id = getIntent().getStringExtra("idtodo");
                if (title.equals("")||desc.equals("")||date.equals("")){
                    if (title.equals("")){
                        edtTitle.setError("judul harus di isi");
                    }
                    if (desc.equals("")){
                        edtDesc.setError("deskripsi harus di isi");
                    }
                    if (date.equals("")){
                        edtDate.setError("Isi tanggalnya juga dong");
                    }
                }else{
                    boolean isUpdate = myDb.updateData(title, desc, date, id);

                    if (isUpdate){
                        Toast.makeText(editTodo.this, "Data Berhasil di ubah",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(editTodo.this,"Data gagal diubah",Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(editTodo.this,MainActivity.class));
                    finish();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getIntent().getStringExtra("idtodo");
                Integer deletedRows = myDb.deleteData(id);

                if (deletedRows > 0){
                    Toast.makeText(editTodo.this,"Data Berhasil dihapus", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(editTodo.this,"Data gagal dihapus",Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(editTodo.this, MainActivity.class));
                finish();
            }
        });

    }
    private void updateLabel(){
        String myFormat = "dd-MM-yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        edtDate.setText(simpleDateFormat.format(myCalender.getTime()));
    }
}