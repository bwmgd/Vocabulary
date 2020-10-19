package com.example.vocabulary.ui.main;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vocabulary.R;
import com.example.vocabulary.domain.WordsContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@link RecyclerView.Adapter} that can display a {@link WordsContent}.
 */
public class WordRecyclerViewAdapter extends RecyclerView.Adapter<WordRecyclerViewAdapter.ViewHolder> {

    public static final String Tag = "WordRecyclerViewAdapter";
    private static OnItemClickListener mListener;
    private final ArrayList<WordsContent.Word> mValues;
    private final Map<Integer, ImageView> viewMap;
    private int mPosition = -1;

    public WordRecyclerViewAdapter(ArrayList<WordsContent.Word> items) {
        mValues = items;
        viewMap = new HashMap<>();
    }

    public static WordRecyclerViewAdapter emptyWordAdapter() {
        WordsContent.Word word = new WordsContent.Word();
        word.setWord("暂无单词");
        word.setMeaning("");
        ArrayList<WordsContent.Word> words = new ArrayList<>();
        words.add(word);
        return new WordRecyclerViewAdapter(words);
    }

    public int getPosition() {
        return mPosition;
    }

    public boolean isEmpty() {
        return mValues.isEmpty();
    }

    public int removeItem(int position) {
        int id = mValues.remove(position).getId();
        Log.v(Tag, "remove:" + id);
        notifyDataSetChanged();
        return id;
    }

    public void clearItem() {
        mValues.clear();
        notifyDataSetChanged();
    }

    public WordsContent.Word addItem(String wordStr, String meaning, String sample, int newFlag) {
        WordsContent.Word word = new WordsContent.Word(wordStr, meaning, sample, newFlag);
        mValues.add(word);
        if (newFlag == 1) Objects.requireNonNull(viewMap.get(viewMap.size() - 1)).setVisibility(View.VISIBLE);
        notifyDataSetChanged();
        return word;
    }

    public WordsContent.Word getItem(int position) {
        return mValues.get(position);
    }

    public void newWord(int position) {
        Objects.requireNonNull(viewMap.get(position)).setVisibility(
                Objects.requireNonNull(viewMap.get(position)).getVisibility() ==
                        View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.word = mValues.get(position);
        holder.mWordView.setText(mValues.get(position).getWord());
        holder.mMeaningView.setText(mValues.get(position).getMeaning());
        holder.itemView.setLongClickable(true);
        holder.itemView.setOnLongClickListener(v -> {
            mPosition = holder.getAdapterPosition();
            return false;
        });
        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(v -> mListener.onItemClick(v));
        if (holder.word.isNewWord()) holder.mImageView.setVisibility(View.VISIBLE);
        viewMap.put(position, holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setMListener(OnItemClickListener mListener) {
        WordRecyclerViewAdapter.mListener = mListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mWordView;
        public final TextView mMeaningView;
        public final ImageView mImageView;
        public WordsContent.Word word;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mWordView = view.findViewById(R.id.item_word);
            mMeaningView = view.findViewById(R.id.item_meaning);
            mImageView = view.findViewById(R.id.startImageView);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mMeaningView.getText() + "'";
        }
    }
}