package com.example.vocabulary;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vocabulary.domain.WordsContent;
import com.example.vocabulary.ui.main.AddWordDialog;
import com.example.vocabulary.ui.main.WordListFragment;
import com.example.vocabulary.ui.main.WordRecyclerViewAdapter;
import com.example.vocabulary.ui.word.WordDetailFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    WordListFragment wordListFragment;
    SearchView searchView;
    RecyclerView recyclerView;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //申请网络权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
        }

        //绑定工具栏
        setSupportActionBar(findViewById(R.id.toolbar));
        //获取Fragment
        fragmentManager = getSupportFragmentManager();
        wordListFragment = (WordListFragment) fragmentManager.findFragmentById(R.id.listFragment);
        assert wordListFragment != null;
        recyclerView = (RecyclerView) wordListFragment.getView();
        //搜索框初始化
        searchViewInit();
        listenerAdd();
    }

    private void listenerAdd() {
        WordRecyclerViewAdapter viewAdapter = (WordRecyclerViewAdapter) recyclerView.getAdapter();
        assert viewAdapter != null;
        viewAdapter.setMListener(view -> {
            WordsContent.Word word = viewAdapter.getItem(recyclerView.getChildAdapterPosition(view));
            if (isLand()) {
                ((WordDetailFragment) Objects.requireNonNull(fragmentManager.findFragmentById(R.id.detailFragment))).setWord(word);
            }
            else {
                Intent intent = new Intent(MainActivity.this, WordActivity.class);
                intent.putExtra("word", word);
                startActivity(intent);
            }
        });
    }

    private void searchViewInit() {
        searchView = findViewById(R.id.search_bar);
        //设置提示词
        searchView.setQueryHint("请输入关键字");
        //为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(this);
    }

    private boolean isLand() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_sitting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("main", "resume");
        wordListFragment.refresh();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_all:
                wordListFragment.refresh();
                break;
            case R.id.add_word:
                addWord();
                break;
            case R.id.clear:
                wordListFragment.clearItem();
                break;
            case R.id.new_words:
                wordListFragment.getNewWord();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addWord() {
        final AddWordDialog wordDialog = new AddWordDialog();
        new AlertDialog.Builder(this).setView(wordDialog.onCreateView(getLayoutInflater(), null,
                null)).setTitle("添加单词").setNegativeButton("取消", null).setPositiveButton("确定",
                (dialog, which) -> {
                    if (wordDialog.getWordStr().isEmpty() || wordDialog.getMeanStr().isEmpty()) {
                        Toast.makeText(MainActivity.this, "请输入单词及词义", Toast.LENGTH_LONG).show();
                    }
                    else {
                        wordListFragment.addItem(wordDialog.getWordStr(),
                                wordDialog.getMeanStr(),
                                wordDialog.getSampleStr());
                    }
                }).create().show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.v("search", newText);
        wordListFragment.searchItem(newText);
        return false;
    }
}