package com.ims.tasol.networkingexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ims.tasol.networkingexample.model.DataPojo;
import com.ims.tasol.networkingexample.model.ListData;
import com.ims.tasol.networkingexample.retrofit.ServiceClass;
import com.ims.tasol.networkingexample.retrofit.ServiceInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AddNewUser extends AppCompatActivity {
    Button btnAdd,btnUpdate;
    DataPojo dataPojo;
    String studentID;
    List<DataPojo> dataList;
    EditText etStudentName,etStudentAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataPojo= new DataPojo();
        setContentView(R.layout.add_student);
        etStudentName=(EditText)findViewById(R.id.etStudentName);
        etStudentAge=(EditText)findViewById(R.id.etStudentAge);

        btnUpdate=(Button)findViewById(R.id.btnUpdate);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        studentID=getIntent().getStringExtra("STUDENT_ID");
        dataList= new ArrayList<>();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPojo.setUserId(studentID);
                dataPojo.setUserName(etStudentName.getText().toString());
                dataPojo.setUserAge(etStudentAge.getText().toString());
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser(dataPojo);
            }
        });
    }

    public void addUser(DataPojo pojo){
        ServiceInterface serviceInterface=ServiceClass.connection();
        Call<ListData> call=serviceInterface.saveUser(pojo);
        call.enqueue(new Callback<ListData>() {
            @Override
            public void onResponse(Response<ListData> response, Retrofit retrofit) {
                Log.v("@@@Response",""+response.toString());
                if(response.isSuccess()){
                    Toast.makeText(AddNewUser.this,"User Added",Toast.LENGTH_LONG).show();
                    supportFinishAfterTransition();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.v("@@@Failure"," Message"+t.getMessage());
            }
        });
    }

    public void printStudentDetails(List<DataPojo> list){
        Log.v("@@@WWe","Student List");
        for (DataPojo dataPojo:list){
            Log.d("Student ID ",dataPojo.getUserId());
            Log.d("Student Name ",dataPojo.getUserName());
            Log.d("Student Age ",dataPojo.getUserAge());
        }
    }


}
