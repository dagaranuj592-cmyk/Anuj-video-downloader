package com.anuj.videodownloader;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText urlText;
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

            if (url.isEmpty()) {
                Toast.makeText(
                        this,
                        "Please paste a video link",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (!url.startsWith("http://")
                    && !url.startsWith("https://")) {

                Toast.makeText(
                        this,
                        "Please enter a valid http/https link",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            downloadVideo(url);
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

            request.setAllowedOverMetered(true);
            request.setAllowedOverRoaming(true);

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

                urlText.setText("");

                Toast.makeText(
                        this,
                        "Download started!",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "Download failed. This must be a direct downloadable video link.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
