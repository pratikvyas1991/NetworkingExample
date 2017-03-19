package com.ims.tasol.networkingexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ims.tasol.networkingexample.model.DataPojo;
import com.ims.tasol.networkingexample.model.GeonameModel;
import com.ims.tasol.networkingexample.model.GeonameResponse;
import com.ims.tasol.networkingexample.model.ListData;
import com.ims.tasol.networkingexample.model.TaskData;
import com.ims.tasol.networkingexample.retrofit.ServiceClass;
import com.ims.tasol.networkingexample.retrofit.ServiceInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    Button btnAdd,btnRefresh;
    ListData listData;
    List<DataPojo> dataList;
    RecyclerView rvStudent;
    UserAdapter userAdapter;
    String studentID=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRefresh=(Button)findViewById(R.id.btnRefresh);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        dataList= new ArrayList<>();
        rvStudent=(RecyclerView)findViewById(R.id.rvStudent);
        getTaskData();
        userAdapter = new UserAdapter();

        LinearLayoutManager layoutManager= new LinearLayoutManager(MainActivity.this);
        rvStudent.setLayoutManager(layoutManager);
        rvStudent.setAdapter(userAdapter);

        
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this," Call Initiated ",Toast.LENGTH_LONG).show();
                getTaskData();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentID=String.valueOf(dataList.size()+1);
                Intent intent = new Intent(MainActivity.this,AddNewUser.class);
                intent.putExtra("STUDENT_ID",studentID);
                startActivity(intent);
            }
        });
    }

    public void getTaskData(){
        ServiceInterface serviceInterface=ServiceClass.connection();
        Call<ListData> call=serviceInterface.taskData("getAllUsersSimple",0);
        call.enqueue(new Callback<ListData>() {
            @Override
            public void onResponse(Response<ListData> response, Retrofit retrofit) {
                Log.v("@@@Response",""+response.toString());
                if(response.isSuccess()){
                    listData=response.body();
                    dataList=listData.getData();
                    printStudentDetails(dataList);

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



    public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View parentView= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item,parent,false);
            RecyclerView.ViewHolder holder=new UserViewHolder(parentView);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final UserViewHolder viewHolder=(UserViewHolder)holder;
            viewHolder.studentName.setText(dataList.get(position).getUserName());
            viewHolder.studentAge.setText(dataList.get(position).getUserAge());

        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }
    public class UserViewHolder extends RecyclerView.ViewHolder{

        public TextView studentName,studentAge;
        public Button btnDelete,btnUpdate;
        public UserViewHolder(View itemView) {
            super(itemView);
            btnDelete=(Button)itemView.findViewById(R.id.btnDelete);
            btnUpdate=(Button)itemView.findViewById(R.id.btnUpdate);
            studentName=(TextView)itemView.findViewById(R.id.studentName);
            studentAge=(TextView)itemView.findViewById(R.id.studenAge);
        }
    }

}
