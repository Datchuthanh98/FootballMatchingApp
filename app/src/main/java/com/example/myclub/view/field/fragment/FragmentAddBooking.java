package com.example.myclub.view.field.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.R;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.FieldRepository;
import com.example.myclub.data.repository.TeamRepository;
import com.example.myclub.databinding.FragmentAddMatchBinding;
import com.example.myclub.databinding.LoadingLayoutBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Field;
import com.example.myclub.model.Team;
import com.example.myclub.model.TimeGame;
import com.example.myclub.session.SessionBookingField;
import com.example.myclub.session.SessionUser;
import com.example.myclub.view.field.adapter.RecycleViewAdapterListTimeVertical;
import com.example.myclub.view.match.fragment.FragmentListTime;
import com.example.myclub.view.team.adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.view.team.fragment.FragmentListMyTeam;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentAddBooking extends Fragment {
    private int pYear, pMonth, pDay, pHour, pMinute;
    private SessionBookingField matchViewModel = SessionBookingField.getInstance();
    private  Map<String ,Object> data = new HashMap<>();
    private Dialog loadingDialog;
    private LoadingLayoutBinding loadingLayoutBinding;
    private  FragmentAddMatchBinding binding;
    private  Field field;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    public FragmentAddBooking(Field field) {
        this.field = field;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddMatchBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeLiveData(view.getContext());
        initComponent();
        initLoadingDialog(view.getContext());

    }

    private void initLoadingDialog(Context context) {
        binding.setField(field);

        if(field.getUrlAvatar() != null) {
            storageRef.child(field.getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if (uri != null) {
                        Picasso.get().load(uri).fit().centerCrop().into(binding.avatarField);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        }

        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingLayoutBinding = LoadingLayoutBinding.inflate(getLayoutInflater());
        loadingDialog.setContentView(loadingLayoutBinding.getRoot());
        loadingDialog.setCancelable(false);
    }

    private  void initComponent(){
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });

        binding.btnSelectClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                Fragment selectTeam = new FragmentListMyTeam(false);
                activityHome.addFragment(selectTeam);
            }
        });



        binding.btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Dialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String sday, smonth;
                        if(day<10){
                            sday ="0"+day;
                        }else{
                            sday=""+day;
                        }

                        if(month<9){
                            smonth ="0"+(month+1);
                        }else{
                            smonth=""+(month+1);
                        }

                        String dateString = sday + "/" + smonth + "/" + year;
                        binding.txtDate.setText(dateString);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        binding.btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                Fragment selectTime = new FragmentListTime(field.getId());
                activityHome.addFragment(selectTime);
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchViewModel.addBookingField(getInforBooking());
                loadingDialog.show();

            }
        });

        binding.btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.put("idTeamAway", matchViewModel.getTeamLiveData().getValue().getId());
                binding.btnLocal.setBackgroundColor(Color.BLACK);
                binding.btnAway.setBackgroundColor(Color.BLUE);
            }
        });

        binding.btnAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.put("idTeamAway", null);
                binding.btnAway.setBackgroundColor(Color.BLACK);
                binding.btnLocal.setBackgroundColor(Color.BLUE);
            }
        });
    }

    private void observeLiveData(final Context context) {
        matchViewModel.getTeamLiveData().observe(getViewLifecycleOwner(), new Observer<Team>() {
            @Override
            public void onChanged(Team team) {
                if(team == null){
                    binding.viewTeam.setVisibility(View.GONE);
                }else{
                    binding.setTeam(team);
                    if(team.getUrlAvatar() != null) {
                        storageRef.child(team.getUrlAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if (uri != null) {
                                    Picasso.get().load(uri).fit().centerCrop().into(binding.avatarTeam);
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {

                            }
                        });
                    }
                    binding.viewTeam.setVisibility(View.VISIBLE);
                }

            }
        });

        matchViewModel.getTimeLiveData().observe(getViewLifecycleOwner(), new Observer<TimeGame>() {
            @Override
            public void onChanged(TimeGame timeGame) {
                Toast.makeText(getContext(), "ahihi",Toast.LENGTH_SHORT).show();
                if(timeGame == null){
                    binding.viewTimeGame.setVisibility(View.GONE);
                }else{
                    binding.setTimeGame(timeGame);
                    binding.viewTimeGame.setVisibility(View.VISIBLE);
                }
            }
        });

        matchViewModel.getBookingResult().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(result == Result.SUCCESS){
                    loadingDialog.dismiss();
                    Toast.makeText(getContext(),"Sucess",Toast.LENGTH_SHORT).show();
                    matchViewModel.setBookingResult(null);
                    detach();
                }else if(result == Result.FAILURE){
                    matchViewModel.setBookingResult(null);
                    loadingDialog.dismiss();
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private Map<String, Object> getInforBooking() {
        data.put("idTeamHome", matchViewModel.getTeamLiveData().getValue().getId());
        data.put("idField", field.getId());
        data.put("date", binding.txtDate.getText().toString());
        data.put("idTimeGame", matchViewModel.getTimeLiveData().getValue().getId());
        data.put("note",binding.txtNote.getText().toString());
        data.put("phone",binding.txtPhone.getText().toString());
//        data.put("timestamp",Calendar.getInstance().getTime());
        return data;
    }


    private void detach() {
        getParentFragmentManager().popBackStack();
    }
}
