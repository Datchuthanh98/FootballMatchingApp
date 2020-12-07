package com.example.myclub.view.field.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentProfileFieldBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Field;
import com.example.myclub.view.field.adapter.RecycleViewAdapterListTimeVertical;
import com.example.myclub.viewModel.ProfileFieldViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class FragmentProfileField extends Fragment {
    private ProfileFieldViewModel viewModel;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FragmentProfileFieldBinding binding;
    private Field field;
    public final static int REQUEST_CALL_PHONE = 12322;

    public FragmentProfileField (Field field) {
        this.field = field;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileFieldBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(ProfileFieldViewModel.class);
        binding.setField(field);
        viewModel.setField(field);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel.getlistTime();
        super.onViewCreated(view, savedInstanceState);
        binding.recycleViewListTimeVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListTimeVertical adapter = viewModel.getAdapterListTimeVertical();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListTimeVertical.setAdapter(adapter);
        binding.recycleViewListTimeVertical.setLayoutManager(new GridLayoutManager(getContext(), 1));
        initComponent();

    }
    private void initComponent(){

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });
        binding.btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                activityHome.addFragment(new FragmentAddBooking(field));

            }
        });

        if(field.getUrlAvatar() != null){
            storageRef.child(field.getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(binding.avatar);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });}


        if(field.getUrlCover() != null) {
            storageRef.child(field.getUrlCover()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(binding.cover);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        }

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });

        binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
                } else {
                    doCalling();
                }
            }
        });


        binding.btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SENDTO);
                intent2.putExtra("sms_body", "Chào team ");
                intent2.setData(Uri.parse("sms:0984060798"));
                startActivity(intent2);
            }
        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Pemission granted", Toast.LENGTH_LONG);
                doCalling();
            } else {
                Toast.makeText(getContext(), "Pemission denied", Toast.LENGTH_LONG);
            }
        }
    }

    public void doCalling(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:0943761160"));
        startActivity(intent);
    }

    private void detach() {
        getParentFragmentManager().popBackStack();
    }
}
