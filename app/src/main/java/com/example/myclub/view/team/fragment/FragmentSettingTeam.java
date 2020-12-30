package com.example.myclub.view.team.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.myclub.R;
import com.example.myclub.data.enumeration.Result;

import com.example.myclub.databinding.FragmentProfileMyTeamBinding;
import com.example.myclub.databinding.FragmentSettingTeamBinding;
import com.example.myclub.databinding.LoadingLayoutBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.session.SessionTeam;
import com.example.myclub.view.player.Fragment.FragmentProfilePlayer;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FragmentSettingTeam extends Fragment {

    private FragmentSettingTeamBinding binding;
    public static final int RESULT_LOAD_IMG_AVATAR = 1001;
    public static final int RESULT_LOAD_IMG_COVER = 1002;
    private  String urlAvatar , urlCover;
    private SessionTeam sessionTeam = SessionTeam.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingTeamBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view.getContext());


    }

    private  void initComponent(final Context context){

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });


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
                BottomSheetDialogFragment dialog = new FragmentEditTeamBasic();
                dialog.show(getParentFragmentManager(), null);
            }
        });



        binding.btnEditIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment dialog = new FragmentEditTeamIntroduce();
                dialog.show(getParentFragmentManager(), null);
            }
        });

        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                activityHome.addFragment(new FragmentProfileListPlayerRequest(SessionTeam.getInstance().getTeamLiveData().getValue().getId()));
            }
        });


    }

    private void detach(){
        getParentFragmentManager().popBackStack();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == RESULT_LOAD_IMG_AVATAR || requestCode == RESULT_LOAD_IMG_COVER) && resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Cursor returnCursor =
                        getContext().getContentResolver().query(imageUri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();

                if(requestCode == RESULT_LOAD_IMG_AVATAR){
                    urlAvatar = returnCursor.getString(nameIndex);
                    updateImage(imageUri,urlAvatar,true);
                }else if(requestCode == RESULT_LOAD_IMG_COVER){
                    urlCover =  returnCursor.getString(nameIndex);
                    updateImage(imageUri,urlCover,false);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    private void updateImage(Uri uri, String path , boolean isAvatar) {
        sessionTeam.updateImage(uri, path,isAvatar);
    }


}
