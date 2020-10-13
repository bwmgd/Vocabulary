package com.example.vocabulary;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import com.example.vocabulary.sqlite.OperationDB;

public class MainActivity extends AppCompatActivity {

    ListFragment listFragment;
    OperationDB operationDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.listFragment);
        operationDB = OperationDB.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_sitting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_word:
                addWord();
                break;
            case R.id.clear:
                operationDB.clear();
                listFragment.refresh();
                break;
            case R.id.new_words:
                newWord();
                break;
            case R.id.app_bar_search:
                searchWords();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addWord() {
        final AddWordDialog wordDialog = new AddWordDialog();
        new AlertDialog.Builder(this).setView(wordDialog.onCreateView(getLayoutInflater(), null,
                null)).setTitle("添加单词").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                operationDB.insert(wordDialog.getWordEdit().getText().toString(),
                        wordDialog.getMeanEdit().getText().toString(),
                        wordDialog.getSampleEdit().getText().toString());
                listFragment.refresh();
            }
        }).create().show();
    }

    private void newWord() {
        // TODO:newWord DB
    }

    private void searchWords() {
        // TODO:searchWords form Helper
    }
}