package com.example.vocabulary;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.example.vocabulary.domain.Translation;
import com.example.vocabulary.sqlite.OperationDB;

public class AddWordDialog extends DialogFragment {

    public AddWordDialog() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        final EditText wordEdit = view.findViewById(R.id.wordEdit);
        final EditText meanEdit = view.findViewById(R.id.meanEdit);
        final EditText sampleEditText = view.findViewById(R.id.sampleEdit);
        Button buttonWeb = view.findViewById(R.id.btWeb);
        Button buttonCancel = view.findViewById(R.id.btCancel);
        Button buttonYes = view.findViewById(R.id.btYes);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperationDB operationDB = OperationDB.getInstance();
                operationDB.insert(wordEdit.getText().toString(), meanEdit.getText().toString(),
                        sampleEditText.getText().toString());

            }
        });
        buttonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        String s = (String) msg.obj;
                        Log.v("getWord", s);
                        meanEdit.setText(s);
                    }
                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.obj = Translation.translation(wordEdit.getText().toString());
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
        return view;
    }
}
