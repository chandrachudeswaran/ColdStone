package com.example.chandra.coldstone;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText username;
    EditText password;
    Button login;
    LoginInterface loginInterface;
    Button signup;

    public LoginFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        login = (Button) view.findViewById(R.id.login);
        signup =(Button)view.findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateInput(username, password)) {
                    Toast.makeText(getActivity(),"Please enter all fields to login",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                        loginInterface.doLoginCheck(username.getText().toString(),password.getText().toString());
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            loginInterface.showSignup();
            }
        });

        return view;

    }

    public boolean validateInput(EditText username, EditText password) {

        if (username.getText().length() == 0 || password.getText().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            loginInterface = (LoginInterface) activity;
            activity.setTitle("EasyPay - Login");
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }

    }

    public interface LoginInterface{
        public void doLoginCheck(String username,String password);
        public void showSignup();
    }

}
