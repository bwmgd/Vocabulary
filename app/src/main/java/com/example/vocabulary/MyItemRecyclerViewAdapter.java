package com.example.vocabulary;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.vocabulary.domain.WordsContent;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link WordsContent}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<WordsContent.Word> mValues;


    public MyItemRecyclerViewAdapter(ArrayList<WordsContent.Word> items) {
        if (items.isEmpty()) {
            WordsContent.Word word = new WordsContent.Word();
            word.setId(-1);
            word.setWord("暂无单词");
            word.setMeaning("");
            ArrayList<WordsContent.Word> words = new ArrayList<>();
            words.add(word);
            mValues = words;
        }
        else mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.word = mValues.get(position);
        holder.mWordView.setText(mValues.get(position).getWord());
        holder.mMeaningView.setText(mValues.get(position).getMeaning());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mWordView;
        public final TextView mMeaningView;
        public WordsContent.Word word;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mWordView = view.findViewById(R.id.item_word);
            mMeaningView = view.findViewById(R.id.item_meaning);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mMeaningView.getText() + "'";
        }
    }
}