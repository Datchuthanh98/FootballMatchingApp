package com.example.myclub.view.Player.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myclub.R;
import com.example.myclub.view.Player.Fragment.FragmentEditPlayerBasic;
import com.example.myclub.view.Player.Fragment.FragmentEditPlayerIntroduce;
import com.example.myclub.view.Player.Fragment.FragmentEditPlayerPlayer;
import com.example.myclub.databinding.ActivityEditPlayerBinding;

public class ActivityEditProfilePlayer extends AppCompatActivity {
    public static int RESULT_LOAD_IMG_AVATAR = 1012;
    public static int RESULT_LOAD_IMG_COVER = 1013;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_player);

        ActivityEditPlayerBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_player);

        binding.btnEditAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



//        toolbar.inflateMenu(R.menu.mymenu);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return false;
//            }
//        });


        binding.btnEditAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG_AVATAR);
            }
        });

        binding.btnEditCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG_COVER);
            }
        });

        binding.btnEditBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new FragmentEditPlayerBasic(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        binding.btnEditPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new FragmentEditPlayerPlayer(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        binding.btnEditIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new FragmentEditPlayerIntroduce(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }



    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            try {
//                imageUri = data.getData();
//                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                imagePreview.setImageBitmap(selectedImage);
//
//                Cursor returnCursor =
//                        getContext().getContentResolver().query(imageUri, null, null, null, null);
//                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
//                returnCursor.moveToFirst();
//                urlFirebase = "/todo/" + returnCursor.getString(nameIndex);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
//            }
//
//        } else {
//            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
//        }
    }

}
