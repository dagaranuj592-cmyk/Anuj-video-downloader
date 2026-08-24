package com.anuj.videodownloader;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView urlText;
    private Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        urlText = findViewById(R.id.urlText);
        downloadButton = findViewById(R.id.downloadButton);

        handleSharedUrl();

        downloadButton.setOnClickListener(v -> {
            String url = urlText.getText().toString().trim();

            if (!url.isEmpty() && !url.equals("No video link received")) {
                downloadVideo(url);
            }
        });
    }

    private void handleSharedUrl() {

        Intent intent = getIntent();

        if (Intent.ACTION_SEND.equals(intent.getAction())
                && intent.getType() != null
                && intent.getType().equals("text/plain")) {

            String sharedText =
                    intent.getStringExtra(Intent.EXTRA_TEXT);

            if (sharedText != null && !sharedText.trim().isEmpty()) {

                urlText.setText(sharedText.trim());
            }
        }
    }

    private void downloadVideo(String url) {

        // Download logic will be added next.
        urlText.setText(
                "Link received:\n\n" + url +
                "\n\nDownload system next step mein add hoga."
        );
    }
}
