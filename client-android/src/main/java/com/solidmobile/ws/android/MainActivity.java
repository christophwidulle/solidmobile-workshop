package com.solidmobile.ws.android;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import com.solidmobile.client.android.AndroidAsyncSolidClientCallbackAdapter;
import com.solidmobile.client.android.SolidClient;
import com.solidmobile.client.android.ui.app.activity.SolidActivity;
import com.solidmobile.client.persistence.ClientDataSourceException;
import com.solidmobile.commons.event.EventListenerRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A basic example of an {@link android.app.Activity} which uses SolidMobile functionalities
 *
 * @author Christoph Widulle
 * @author Stefan Haacker
 */
public class MainActivity extends SolidActivity {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("HH:mm:ss");

    private static StringBuilder logBuffer = new StringBuilder();

    private TextView textView;

    private EventListenerRegistry eventListenerRegistry;

    public MainActivity() {
        showAdminMenu(true);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null && savedInstanceState.getString("log") != null) {
            logBuffer = new StringBuilder(savedInstanceState.getString("log"));
        }

        textView = (TextView) findViewById(R.id.textoutput);

        textView.setText(logBuffer.toString());


        registerCallback(SolidClient.getAsync(new AndroidAsyncSolidClientCallbackAdapter() {
            @Override
            public void onAsyncGet(final SolidClient solidClient) {
                try {
                    eventListenerRegistry = solidClient.internal().getClientEventBus().createRegistry();

                } catch (final ClientDataSourceException e) {
                    e.printStackTrace();
                    log(e.getLocalizedMessage());
                }
            }
        }));
    }



    private void log(final String msg) {
        logBuffer.append("\n\r").append("[").append(DATE_FORMATTER.format(new Date())).append("] ").append(msg);
        if (textView != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(logBuffer.toString());
                }
            });
        }
    }


    protected void showProcessing(final boolean show) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setProgressBarIndeterminateVisibility(show);
            }
        });
    }
}