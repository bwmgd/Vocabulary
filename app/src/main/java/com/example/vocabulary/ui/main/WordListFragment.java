package com.example.vocabulary.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vocabulary.R;
import com.example.vocabulary.WordsApplication;
import com.example.vocabulary.domain.WordsContent;
import com.example.vocabulary.sqlite.OperationDB;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 */
public class WordListFragment extends Fragment {

    public static final String Tag = "WordListFragment";
    private final OperationDB operationDB = OperationDB.getInstance();
    private RecyclerView recyclerView;
    private WordRecyclerViewAdapter viewAdapter;
    private int newWordFlag = 0;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WordListFragment() {
    }

    @SuppressWarnings("unused")
    public static WordListFragment newInstance(int columnCount) {
        WordListFragment fragment = new WordListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.word_item_list, container, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), OrientationHelper.VERTICAL));
        //获取单词列表
        refresh();
        recyclerView.findViewById(R.id.list).setOnCreateContextMenuListener((menu, v, menuInfo)
                -> Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.word_sitting, menu));
        return recyclerView;
    }

    public void refresh() {
        ArrayList<WordsContent.Word> items = operationDB.getAllWord();
        refresh(items);
        newWordFlag = 0;
    }

    public void refresh(ArrayList<WordsContent.Word> items) {
        viewAdapter = new WordRecyclerViewAdapter(items);
        recyclerView.setAdapter(items.isEmpty() ? WordRecyclerViewAdapter.emptyWordAdapter() : viewAdapter);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        WordsContent.Word word = viewAdapter.getItem(viewAdapter.getPosition());
        switch (item.getItemId()) {
            case R.id.update:
                update(word);
                break;
            case R.id.delete:
                removeItem();
                break;
            case R.id.newWord:
                operationDB.addNewWord(word.isNewWord() ? 0 : 1, word.getId());
                viewAdapter.newWord(viewAdapter.getPosition());
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void clearItem() {
        operationDB.clear();
        viewAdapter.clearItem();
        recyclerView.setAdapter(WordRecyclerViewAdapter.emptyWordAdapter());
    }

    public void addItem(String wordStr, String meaning, String sample) {
        recyclerView.setAdapter(viewAdapter);
        int id = operationDB.insert(wordStr, meaning, sample, newWordFlag);
        viewAdapter.addItem(wordStr, meaning, sample, newWordFlag).setId(id);
        Log.v(Tag, "add:" + id);
    }

    public void searchItem(String s) {
        refresh(operationDB.search(s));
    }

    public void getNewWord() {
        refresh(operationDB.getAllNewWords());
        newWordFlag = 1;
    }

    public void removeItem() {
        operationDB.delete(viewAdapter.removeItem(viewAdapter.getPosition()));
        if (viewAdapter.isEmpty()) recyclerView.setAdapter(WordRecyclerViewAdapter.emptyWordAdapter());
    }

    private void update(final WordsContent.Word word) {
        final AddWordDialog wordDialog = new AddWordDialog();
        new AlertDialog.Builder(Objects.requireNonNull(getActivity())).setView(wordDialog.onCreateView(getLayoutInflater(), null,
                null)).setTitle("修改单词").setNegativeButton("取消", null).setPositiveButton("确定",
                (dialog, which) -> {
                    if (wordDialog.getWordStr().isEmpty() || wordDialog.getMeanStr().isEmpty()) {
                        Toast.makeText(WordsApplication.getContext(), "请输入单词及词义", Toast.LENGTH_LONG).show();
                    }
                    else {
                        operationDB.update(word.getId(), wordDialog.getWordStr(), wordDialog.getMeanStr(),
                                wordDialog.getSampleStr());
                        viewAdapter.notifyDataSetChanged();
                    }
                }).create().show();
        wordDialog.setWordStr(word.getWord());
        wordDialog.setMeanStr(word.getMeaning());
        wordDialog.setSampleStr(word.getSample());
    }
}