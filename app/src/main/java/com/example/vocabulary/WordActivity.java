package com.example.vocabulary;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vocabulary.domain.WordsContent;
import com.example.vocabulary.ui.word.WordDetailFragment;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_activity);
        if (savedInstanceState != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, WordDetailFragment.newInstance())
                    .commitNow();
        }

        setSupportActionBar(findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);  //菜单栏返回按钮
        getSupportActionBar().setHomeButtonEnabled(true);

        WordsContent.Word word = (WordsContent.Word) getIntent().getExtras().getSerializable("word");
        ((TextView) findViewById(R.id.wordStr)).setText(word.getWord());
        ((TextView) findViewById(R.id.wordMean)).setText(word.getMeaning());
        ((TextView) findViewById(R.id.wordSample)).setText(word.getSample());
        findViewById(R.id.imageView).setVisibility(word.isNewWord() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}