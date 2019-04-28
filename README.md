# AndroidWorkManager

In this codeset we are going to see how to use WorkManager (is the new Android Jetpack component) to schedule background tasks. 


## MainActivity


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
                
        
### Create MyWorker class which is inherited by Worker class and do get data from MainActivity
    
    //get data from MainActivity
    
     Data data = getInputData();
        if (data != null) {
            description = data.getString(MainActivity.KEY_DATA_DESCRIPTION);
        }

        //I am showing a notification in my Example, Do your work based on the requirement
    
        displayNotification("Hey I am your work", description);

#### Return data to your MainActivity from MyWorker class

        Data dataToSend = new Data.Builder()
                .putString(KEY_RETURN_DATA,"Worker status RESULT executed").build();
                
                
                
#### Receving Result from Worker class to MainActivity

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
