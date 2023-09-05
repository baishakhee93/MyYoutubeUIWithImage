package com.baishakhee.youtube.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;

import com.baishakhee.youtube.R;
import com.baishakhee.youtube.databinding.ActivityDetailsBinding;
import com.baishakhee.youtube.model.Items;
import com.baishakhee.youtube.utility.DateUtils;
import com.baishakhee.youtube.viewmodel.DetailsViewModel;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    private DetailsViewModel viewModel;
    private static final int REQUEST_WRITE_STORAGE = 112;
    static String imageUrl = ""; // Replace with your image URL
    private static final String CHANNEL_ID = "youtube_image_download_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        //  binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Assuming you're in the receiving activity's code
        Intent intent = getIntent();
        if (intent != null) {
            Parcelable receivedItem = intent.getParcelableExtra("videoItem");
            if (receivedItem != null) {
                // Do something with the received Parcelable object
                // For example, if it's a VideoItem object:
                if (receivedItem instanceof Items) {
                    Items videoItem = (Items) receivedItem;
                    // Now you can access the data in videoItem and display it
                    // For example:
                    String title = videoItem.getSnippet().getChannelTitle();
                    String description = videoItem.getSnippet().getTitle();
                    String publishTime = videoItem.getSnippet().getPublishTime();

                    binding.titleText.setText(description);
                    binding.titleTextView.setText(title);
                    binding.timeasText.setText(DateUtils.formatDate(publishTime));

                    if (videoItem.getSnippet().getThumbnails() != null) {
                        System.out.println("Details....getSnippet..." + videoItem.getSnippet().getThumbnails().getMedium());
                        imageUrl = videoItem.getSnippet().getThumbnails().getHigh().getUrl();
                        Glide.with(this)
                                .load(videoItem.getSnippet().getThumbnails().getHigh().getUrl())
                                .error(R.drawable.ic_profile)
                                .into(binding.imageView);
                        Glide.with(this)
                                .load(videoItem.getSnippet().getThumbnails().getMedium().getUrl())
                                .error(R.drawable.ic_profile)
                                .into(binding.chainalImage);

                        binding.share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("image/*");
                                shareIntent.putExtra(Intent.EXTRA_STREAM, getUriFromDrawable(binding.imageView));
                                shareIntent.putExtra(Intent.EXTRA_TEXT, description);

                                // Start the sharing activity
                                startActivity(Intent.createChooser(shareIntent, "Share Image with Title"));
                            }
                        });
                    }
                    // Display the data as needed
                }
            }
        }

        createNotificationChannel();
        binding.selectOption.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                binding.selectOption.setBackgroundResource(R.drawable.ic_circal_shap);
            }
        });
    binding.save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                binding.save.setBackgroundResource(R.drawable.ic_circal_shap_gray);
            }
        });

        binding.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage();
            }
        });


    }


    private Uri getUriFromDrawable(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        // Save the bitmap to a temporary file and return its URI
        try {
            File file = new File(getCacheDir(), "shared_image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            return FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with image download
                downloadImage();
            } else {
                Toast.makeText(this, "Permission denied. Cannot download image.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Image Download Channel";
            String description = "Channel for image download notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void downloadImage() {
        // Check if permission to write to external storage is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        } else {
            // Permission is granted, proceed with image download

            Picasso.get()
                    .load(imageUrl)
                    .into(binding.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            // Save the image to external storage
                            BitmapDrawable drawable = (BitmapDrawable) binding.imageView.getDrawable();
                            Bitmap bitmap = drawable.getBitmap();

                            String fileName = "downloaded_image.jpg";
                            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                            File file = new File(directory, fileName);

                            try {
                                FileOutputStream outputStream = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                outputStream.flush();
                                outputStream.close();
                                showDownloadCompleteNotification(file);

                                Toast.makeText(DetailsActivity.this, "Image downloaded successfully", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(DetailsActivity.this, "Error downloading image", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(DetailsActivity.this, "Error loading image", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private void showDownloadCompleteNotification(File imageFile) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Image Download Complete")
                .setContentText("Image has been downloaded successfully.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle()
                .bigPicture(BitmapFactory.decodeFile(imageFile.getAbsolutePath()))
                .setBigContentTitle("Image Download Complete")
                .setSummaryText("Image has been downloaded successfully.");

        builder.setStyle(bigPictureStyle);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = 1; // You can use a unique ID for each notification
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(notificationId, builder.build());
    }

}