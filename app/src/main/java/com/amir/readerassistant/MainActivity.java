package com.amir.readerassistant;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

public class MainActivity extends AppCompatActivity {

    TextView titleMainAct, deskMainAct, tvTitleChooseImageFile, tvPdfFileName, tvTitleChoosePdfFIle, tvImagePickerFileName;
    LottieAnimationView animFrameMainAct;
    ImageView imgBtnChoosePdf, imgBtnChooseImage;
    public static final int PICK_PDF_FILE_REQUEST_CODE = 99;
    public static final int PICK_IMAGE_FILE_REQUEST_CODE = 1;
    Uri selectedFileUri;
    String filePath;
    String selectedPdfFilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpUIViews();
        pickPdfFile();
        pickImageFile();
    }

    private void pickImageFile() {
        tvTitleChooseImageFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_FILE_REQUEST_CODE);
            }
        });
        imgBtnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_FILE_REQUEST_CODE);
            }
        });
    }

    private void pickPdfFile() {
        tvTitleChoosePdfFIle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                startActivityForResult(intent, PICK_PDF_FILE_REQUEST_CODE);
            }
        });
        imgBtnChoosePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                startActivityForResult(intent, PICK_PDF_FILE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pick PDF File.
        selectedFileUri = data.getData();
        String fileName = null;
        if (selectedFileUri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(selectedFileUri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    fileName = cursor.getString(nameIndex);
                }
            }
        }
        tvPdfFileName.setText(fileName);

        // Pick Image File.
        if (requestCode == PICK_IMAGE_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imgBtnChooseImage.setImageURI(selectedImage);
        }
    }

    private void setUpUIViews() {
        tvImagePickerFileName = findViewById(R.id.tvImagePickerFileName);
        imgBtnChooseImage = findViewById(R.id.imgBtnChooseImage);
        tvTitleChoosePdfFIle = findViewById(R.id.tvTitleChoosePdfFIle);
        tvPdfFileName = findViewById(R.id.tvPdfFileName);
        titleMainAct = findViewById(R.id.titleMainAct);
        deskMainAct = findViewById(R.id.deskMainAct);
        tvTitleChooseImageFile = findViewById(R.id.tvTitleChooseImage);
        animFrameMainAct = findViewById(R.id.animFrameMainAct);
        imgBtnChoosePdf = findViewById(R.id.imgBtnChoosePdfFile);
        animFrameMainAct.setRepeatCount(LottieDrawable.INFINITE);
    }
}