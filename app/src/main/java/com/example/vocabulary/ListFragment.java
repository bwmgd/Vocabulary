package com.example.vocabulary;

import android.content.Context;
import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vocabulary.domain.WordsContent;
import com.example.vocabulary.sqlite.OperationDB;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 */
public class ListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private final OperationDB operationDB = OperationDB.getInstance();
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListFragment() {
    }

    @SuppressWarnings("unused")
    public static ListFragment newInstance(int columnCount) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) recyclerView.setLayoutManager(new LinearLayoutManager(context));
            else recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            //获取单词列表
            refresh();
        }
        registerForContextMenu(view);
        return view;
    }

    public void refresh() {
        ArrayList<WordsContent.Word> items = operationDB.getAllWord();
        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(items));
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable @org.jetbrains.annotations.Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.word_sitting, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // View itemView = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).targetView;
        switch (item.getItemId()) {
            case R.id.update:
                // TODO:update
                break;
            case R.id.delete:
                // TODO:delete
                break;
            case R.id.newWord:
                // TODO:newWord
                break;
        }
        return super.onContextItemSelected(item);
    }
}