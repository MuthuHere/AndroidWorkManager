package com.muthu.andriodwordmanager;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnScheduler = findViewById(R.id.btnScheduler);
        final TextView tvStatus = findViewById(R.id.tvStatus);


        final OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class).build();

        btnScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance().enqueue(oneTimeWorkRequest);
            }
        });

        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                String status = workInfo.getState().name();
                tvStatus.append(status + "\n");
            }
        });

    }


}
