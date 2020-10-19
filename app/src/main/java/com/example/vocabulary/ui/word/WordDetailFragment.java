package com.example.vocabulary.ui.word;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.vocabulary.R;
import com.example.vocabulary.domain.WordsContent;

public class WordDetailFragment extends Fragment {

    View view;

    public static WordDetailFragment newInstance() {
        return new WordDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.word_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setWord(WordsContent.Word word) {
        ((TextView) view.findViewById(R.id.wordStr)).setText(word.getWord());
        ((TextView) view.findViewById(R.id.wordMean)).setText(word.getMeaning());
        ((TextView) view.findViewById(R.id.wordSample)).setText(word.getSample());
        view.findViewById(R.id.imageView).setVisibility(word.isNewWord() ? View.VISIBLE : View.INVISIBLE);
    }
}