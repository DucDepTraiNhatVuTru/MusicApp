package com.example.dell.music.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.music.Activity.ChangePasswordActivity;
import com.example.dell.music.Activity.ListSongInPlaylistActivity;
import com.example.dell.music.Activity.SignInActivity;
import com.example.dell.music.R;

public class AccountFragment extends android.support.v4.app.Fragment {
    TextView txtvChangePass,txtvLogOut;
    public AccountFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        anhXa();
        txtvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        txtvChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_account,container,false);
    }

    private void anhXa(){
        txtvChangePass = getActivity().findViewById(R.id.txtvChangePass);
        txtvLogOut = getActivity().findViewById(R.id.txtvLogout);
    }
}
