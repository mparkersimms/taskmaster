package com.mparkersimms.taskmaster;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushListenerService extends FirebaseMessagingService {
    String TAG = "msimms_pushListenerService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);
        Log.i(TAG, "onMessageReceived: " +  remoteMessage.toString());
        Log.i(TAG, "onMessageReceived: ============================" + remoteMessage.getNotification().getTitle());
        Log.i(TAG, "onMessageReceived: ============================" + remoteMessage.getNotification().getBody());

        Context context = getApplicationContext();
        CharSequence text = remoteMessage.getNotification().getTitle() + " " +  remoteMessage.getNotification().getBody();
        int duration = Toast.LENGTH_SHORT;
        Looper.prepare();
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

//        Intent intent = new Intent("push-notification");
//        Intent intent = new Intent.
//        intent.putExtra("from", remoteMessage.getFrom());
//        intent.putExtra("data", remoteMessage.getData());
//        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), MainActivity.TASK_CHANNEL)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(remoteMessage.getFrom())
                .setContentText(remoteMessage.getSentTime() + "")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());

//        notificationManager.notify(2, remoteMessage.getNotification());
    }
}
