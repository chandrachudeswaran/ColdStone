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
public class Signup extends Fragment {

    EditText username;
    EditText password;
    EditText confirmpassword;
    Button signup;

    SignupInterface signupInterface;

    public Signup() {

    }

    public interface SignupInterface{
        public void doSingup(String username,String password);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        confirmpassword = (EditText) view.findViewById(R.id.confirmpassword);

        signup = (Button) view.findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateInput()){
                        signupInterface.doSingup(username.getText().toString(),password.getText().toString());
                }
            }
        });

        return view;
    }


    public boolean validateInput(){
        if(username.getText().length()==0|| password.getText().length()==0|| confirmpassword.getText().length()==0){
            Toast.makeText(getActivity(),"Enter all the fields",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!password.getText().toString().equals(confirmpassword.getText().toString())){
            Toast.makeText(getActivity(),"Passwords dont match",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            signupInterface = (SignupInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }

    }
}
