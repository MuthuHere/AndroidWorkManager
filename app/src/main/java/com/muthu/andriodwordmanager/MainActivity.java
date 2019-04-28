package com.muthu.andriodwordmanager;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import static com.muthu.andriodwordmanager.MyWorker.KEY_RETURN_DATA;


public class MainActivity extends AppCompatActivity {
    public static String KEY_DATA_DESCRIPTION = "key_data_description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnScheduler = findViewById(R.id.btnScheduler);
        final TextView tvStatus = findViewById(R.id.tvStatus);

        //send data to worker class using default
        Data data = new Data.Builder()
                .putString(KEY_DATA_DESCRIPTION, "Description from MainActivity")
                .build();

        //set constraint
        Constraints constraints =new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)// there are some more constraints we can set
                .build();


        //create one time request
        final OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setInputData(data)
                .setConstraints(constraints)
                .build();

        btnScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance().enqueue(oneTimeWorkRequest);
            }
        });

        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {

                if (workInfo!=null && workInfo.getState().isFinished()){
                    Data resultData = workInfo.getOutputData();
                    if (resultData != null) {
                        String responseString = resultData.getString(KEY_RETURN_DATA);

                        tvStatus.append(responseString);

                    }


                }
                String status = workInfo.getState().name();
                tvStatus.append(status + "\n");

            }
        });

    }


}
