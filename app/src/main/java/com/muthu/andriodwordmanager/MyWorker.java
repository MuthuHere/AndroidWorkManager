package com.muthu.andriodwordmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {


    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        triggerNotification("Hey, I am Your work","Work Finished");

        return Result.Success;
    }


    private void triggerNotification(String title, String description){

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("myNotifyID","myNotifyID",NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }



        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(),"myNotifyID")
                        .setContentTitle(title)
                        .setContentText("")
                        .setSmallIcon(R.mipmap.ic_launcher);

        manager.notify(1,builder);
    }
}
