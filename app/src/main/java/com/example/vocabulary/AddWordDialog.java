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
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.vocabulary.domain.Translation;

public class AddWordDialog extends Fragment {
    EditText wordEdit;
    EditText meanEdit;
    EditText sampleEdit;

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
        wordEdit = view.findViewById(R.id.wordEdit);
        meanEdit = view.findViewById(R.id.meanEdit);
        sampleEdit = view.findViewById(R.id.sampleEdit);
        Button buttonWeb = view.findViewById(R.id.btWeb);
        buttonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        try {
                            String s = (String) msg.obj;
                            Log.v("getWord", s);
                            meanEdit.setText(s);
                        }catch (NullPointerException e){
                            Toast.makeText(WordsApplication.getContext(), "无网络连接", Toast.LENGTH_LONG).show();
                        }
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

    public EditText getWordEdit() {
        return wordEdit;
    }

    public EditText getMeanEdit() {
        return meanEdit;
    }

    public EditText getSampleEdit() {
        return sampleEdit;
    }
}
