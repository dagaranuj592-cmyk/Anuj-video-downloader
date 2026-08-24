package com.anuj.videodownloader;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

            if (!url.isEmpty()
                    && !url.equals("No video link received")) {

                downloadVideo(url);
            }
        });
    }

    private void handleSharedUrl() {

        Intent intent = getIntent();

        if (Intent.ACTION_SEND.equals(intent.getAction())
                && "text/plain".equals(intent.getType())) {

            String sharedText =
                    intent.getStringExtra(Intent.EXTRA_TEXT);

            if (sharedText != null
                    && !sharedText.trim().isEmpty()) {

                urlText.setText(sharedText.trim());
            }
        }
    }

    private void downloadVideo(String url) {

        try {

            Uri videoUri = Uri.parse(url);

            DownloadManager.Request request =
                    new DownloadManager.Request(videoUri);

            request.setTitle("Video Download");
            request.setDescription("Downloading video...");

            request.setNotificationVisibility(
                    DownloadManager.Request
                            .VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            );

            request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "video_" + System.currentTimeMillis() + ".mp4"
            );

            DownloadManager downloadManager =
                    (DownloadManager)
                            getSystemService(
                                    Context.DOWNLOAD_SERVICE
                            );

            if (downloadManager != null) {

                downloadManager.enqueue(request);

                urlText.setText(
                        "Download started!\n\n" + url
                );
            }

        } catch (Exception e) {

            urlText.setText(
                    "Download failed.\n\n" +
                    "This link is not a direct downloadable video."
            );
        }
    }
}
