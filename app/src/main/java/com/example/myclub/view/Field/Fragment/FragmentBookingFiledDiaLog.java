package com.example.myclub.view.Field.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentBookingFieldBinding;
import com.example.myclub.databinding.FragmentListFieldBinding;
import com.example.myclub.view.Field.Adapter.RecycleViewAdapterListMatchVertical;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamHorizontal;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTimeHorizontal;
import com.example.myclub.viewModel.ViewModelTodo;

public class FragmentBookingFiledDiaLog extends DialogFragment {
    private ViewModelTodo viewModel;

    FragmentBookingFieldBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_booking_field, container, false);
        View view = binding.getRoot();



//        binding.recycleViewListTeamHorizontal.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
//        RecycleViewAdapterListTeamHorizontal adapterListTeamHorizontal = viewModel.getAdapterListTeamHorizontal();
//        adapterListTeamHorizontal.setFm(getParentFragmentManager());
//        binding.recycleViewListTeamHorizontal.setAdapter(viewModel.getAdapterListTeamHorizontal());
//
//        binding.recycleViewListTime.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
//        RecycleViewAdapterListTimeHorizontal adapterListTimeHorizontal = viewModel.getAdapterListTimeHorizontal();
//        adapterListTimeHorizontal.setFm(getParentFragmentManager());
//        binding.recycleViewListTime.setAdapter(viewModel.getAdapterListTimeHorizontal());
//
//        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"Show r ",Toast.LENGTH_SHORT).show();
//               getDialog().dismiss();
//            }
//        });

        return view;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_booking_field, null));
        builder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Show r ",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"Show r ",Toast.LENGTH_SHORT).show();
//               getDialog().dismiss();
//            }
//        });
    }
}
