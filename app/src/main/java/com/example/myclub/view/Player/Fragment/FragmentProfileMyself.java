package com.example.myclub.view.Player.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.myclub.R;
import com.example.myclub.auth.ActivityLogin;
import com.example.myclub.data.session.Session;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.databinding.FragmentProfileMyselfBinding;
import com.example.myclub.view.Team.Fragment.FragmentMainEditTeam;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentProfileMyself extends Fragment {

    private FragmentProfileMyselfBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_myself, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                activityHome.addFragment(new FragmentMainEditPlayer());
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(getContext(), ActivityLogin.class));
//                startActivity(new Intent(DashBoardActivity.this, FaceBookLoginActivity.class));
                getActivity().finish();
            }
        });
    }

    private void detach() {
        getActivity().finish();
    }
}
