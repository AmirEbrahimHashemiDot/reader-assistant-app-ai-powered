package com.amir.readerassistant;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
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

    TextView titleMainAct, deskMainAct, tvTitleChooseFile, tvPdfFileName, tvTitleChoosePdfFIle;
    LottieAnimationView animFrameMainAct;
    ImageView imgBtnChoosePdf;
    public static final int PICK_PDF_FILE_REQUEST_CODE = 99;
    Uri selectedFileUri;
    String filePath;
    String selectedPdfFilePath;
    PdfRenderer pdfRenderer;

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

        Uri uri = data.getData();

        String fileName = null;

        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    fileName = cursor.getString(nameIndex);
                }
            }
        }

        // Show the file name in a Toast
        //Toast.makeText(this, "Selected File: " + fileName, Toast.LENGTH_SHORT).show();
        tvPdfFileName.setText(fileName);




        /*if (requestCode == PICK_PDF_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedFileUri = data.getData();
                filePath = selectedFileUri.getPath();
                Toast.makeText(this, "Path: " + filePath, Toast.LENGTH_SHORT).show();
                Log.i("MY_TAG", "File Path: " + filePath);
            }
        }*/
    }

    /*private ArrayList<String> getLocalPdfFiles() {
        ContentResolver contentResolver = getContentResolver();
        String mime = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf");
        String args[] = new String[]{mimeType};
        String proj[] = {MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.DISPLAY_NAME};
        String sortingOrder = MediaStore.Files.FileColumns.DATE_ADDED + " DESC";
        Cursor cursor = contentResolver.query(MediaStore.Files.getContentUri("external"), proj, mime, args, sortingOrder);
        ArrayList<String> pdfList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);

                String path = cursor.getString(index);
                pdfList.add(path);
            }
            cursor.close();
        }
        return pdfList;
    }*/


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_FILE && requestCode == RESULT_OK) {
            if (data != null) {
                selectedFileUri = data.getData();
                selectedPdfFilePath = selectedFileUri.getPath();
                Toast.makeText(this, "File: " + selectedPdfFilePath.toString(), Toast.LENGTH_SHORT).show();
                try {
                    ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedFileUri, "r");
                    Toast.makeText(this, "File: " + selectedFileUri.toString(), Toast.LENGTH_SHORT).show();

                    //pdfRenderer = new PdfRenderer(parcelFileDescriptor);

                } catch (FileNotFoundException fnfE) {
                    fnfE.printStackTrace();
                    Toast.makeText(this, "FileNotFoundException", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }*/

    private void setUpUIViews() {
        tvTitleChoosePdfFIle = findViewById(R.id.tvTitleChoosePdfFIle);
        tvPdfFileName = findViewById(R.id.tvPdfFileName);
        titleMainAct = findViewById(R.id.titleMainAct);
        deskMainAct = findViewById(R.id.deskMainAct);
        tvTitleChooseFile = findViewById(R.id.tvTitleChooseImage);
        animFrameMainAct = findViewById(R.id.animFrameMainAct);
        imgBtnChoosePdf = findViewById(R.id.imgBtnChoosePdfFile);
        animFrameMainAct.setRepeatCount(LottieDrawable.INFINITE);
    }
}