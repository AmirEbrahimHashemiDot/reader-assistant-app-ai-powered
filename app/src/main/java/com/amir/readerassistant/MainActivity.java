package com.amir.readerassistant;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    ImageView imgBtnChoosePdfFile, imgBtnChooseImage, imgRemoveSelectedPdfFile, imgRemoveSelectedImageFile;
    Button btnUploadFile;
    public static final int PICK_PDF_FILE_REQUEST_CODE = 99;
    public static final int PICK_IMAGE_FILE_REQUEST_CODE = 1;
    Uri selectedPdfUri, selectedImageUri;
    String pdfFileName, imageFileName;

    String filePath;
    String selectedPdfFilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_redesign);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpUIViews();
        pickPdfFile();
        pickImageFile();
        removeSelectedPDF();
        removeSelectedImage();
        exploreClicked();
    }

    private void exploreClicked() {
        btnUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });
    }

    private void removeSelectedImage() {
        imgRemoveSelectedImageFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageUri = null;
                tvImagePickerFileName.setText("");

                imgRemoveSelectedPdfFile.setClickable(true);
                imgBtnChoosePdfFile.setClickable(true);
                tvTitleChoosePdfFIle.setClickable(true);
                imgBtnChooseImage.setImageResource(R.drawable.img_icon_transe);
            }
        });
    }

    private void removeSelectedPDF() {
        imgRemoveSelectedPdfFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPdfUri = null;
                tvPdfFileName.setText("");

                imgRemoveSelectedImageFile.setClickable(true);
                imgBtnChooseImage.setClickable(true);
                tvTitleChooseImageFile.setClickable(true);
            }
        });
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
                try {
                    startActivityForResult(intent, PICK_PDF_FILE_REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "E: " + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        imgBtnChoosePdfFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                try {
                    startActivityForResult(intent, PICK_PDF_FILE_REQUEST_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "E: " + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pick PDF File.
        if (requestCode == PICK_PDF_FILE_REQUEST_CODE && data != null) {
            selectedPdfUri = data.getData();
            pdfFileName = null;
            if (selectedPdfUri.getScheme().equals("content")) {
                try (Cursor cursor = getContentResolver().query(selectedPdfUri, null, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        pdfFileName = cursor.getString(nameIndex);
                    }
                }
            }
            tvPdfFileName.setText(pdfFileName);

            imgRemoveSelectedImageFile.setClickable(false);
            imgBtnChooseImage.setClickable(false);
            tvTitleChooseImageFile.setClickable(false);
        } else {
            Log.i("TAG", "onActivityResult: Result was Null or Empty.");
        }

        // Pick Image File.
        if (requestCode == PICK_IMAGE_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imgBtnChooseImage.setImageURI(selectedImageUri);

            imageFileName = null;
            if (selectedImageUri.getScheme().equals("content")) {
                try (Cursor cursor = getContentResolver().query(selectedImageUri, null, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        imageFileName = cursor.getString(nameIndex);
                    }
                }
            }
            tvImagePickerFileName.setText(imageFileName);

            imgRemoveSelectedPdfFile.setClickable(false);
            imgBtnChoosePdfFile.setClickable(false);
            tvTitleChoosePdfFIle.setClickable(false);

        } else {
            Log.i("TAG", "onActivityResult: Result was Null or Empty.");
        }
    }

    private void setUpUIViews() {
        btnUploadFile = findViewById(R.id.btnUploadFile);
        imgRemoveSelectedImageFile = findViewById(R.id.imgRemoveSelectedImageFile);
        imgRemoveSelectedPdfFile = findViewById(R.id.imgRemoveSelectedPdfFile);
        tvImagePickerFileName = findViewById(R.id.tvImageFileName);
        imgBtnChooseImage = findViewById(R.id.imgBtnChooseImage);
        tvTitleChoosePdfFIle = findViewById(R.id.tvTitleChoosePdfFIle);
        tvPdfFileName = findViewById(R.id.tvPdfFileName);
        titleMainAct = findViewById(R.id.titleMainAct);
        deskMainAct = findViewById(R.id.deskMainAct);
        tvTitleChooseImageFile = findViewById(R.id.tvTitleChooseImage);
        animFrameMainAct = findViewById(R.id.animFrameMainAct);
        imgBtnChoosePdfFile = findViewById(R.id.imgBtnChoosePdfFile);
        animFrameMainAct.setRepeatCount(LottieDrawable.INFINITE);
    }
}