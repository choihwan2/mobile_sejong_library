package com.fourB.library.RequestBook;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fourB.library.Util.HttpManager;
import com.fourB.library.R;
import com.fourB.library.SearchBook.SearchBookAdapter;
import com.fourB.library.SearchBook.SearchBookItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SearchRequestBookFragment extends Fragment {

    private Spinner mCategorySpinner;
    private Spinner mSortSpinner;
    private EditText mEditTextSearch;
    private Button mBtnSearch;
    private RecyclerView mRecyclerView;
    private SearchBookAdapter mSearchBookAdapter;
    private ViewGroup rootView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(R.layout.activity_search_book, container, false);

        initView();
        initListener();
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.search_book_sort, R.layout.item_basic_spinner);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.search_book_category, R.layout.item_basic_spinner);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortSpinner.setAdapter(sortAdapter);
        mCategorySpinner.setAdapter(categoryAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mSearchBookAdapter = new SearchBookAdapter(getContext());
        mRecyclerView.setAdapter(mSearchBookAdapter);

        return rootView;
    }

    private void initView() {
        mSortSpinner = (Spinner) rootView.findViewById(R.id.spinner_search_book_sort);
        mCategorySpinner = (Spinner) rootView.findViewById(R.id.spinner_search_book_category);
        mEditTextSearch = (EditText) rootView.findViewById(R.id.editText_search_book);
        mBtnSearch = (Button) rootView.findViewById(R.id.btn_search_book);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_search_book);
    }

    private void initListener() {
        mEditTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return (event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER);
            }
        });

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curCategory = mCategorySpinner.getSelectedItem().toString();
                String curSort = mSortSpinner.getSelectedItem().toString();
                final String[] categoryArr = getResources().getStringArray(R.array.search_book_category);
                final String[] sortArr = getResources().getStringArray(R.array.search_book_sort);


                for(int i=0; i<categoryArr.length; i++) {
                    if( curCategory.equals(categoryArr[i]) ) {
                        switch(i) {
                            case 0: curCategory = HttpManager.BOOK_CATEGORY_TITLE; break;
                            case 1: curCategory = HttpManager.BOOK_CATEGORY_AUTOR; break;
                            case 2: curCategory = HttpManager.BOOK_CATEGORY_TITLE; break;
                            case 3: curCategory = HttpManager.BOOK_CATEGORY_TITLE; break;
                            case 4: curCategory = HttpManager.BOOK_CATEGORY_PUBL; break;
                            default: break;
                        }
                    }
                }


                for(int i=0; i<sortArr.length; i++) {
                    if( curSort.equals(sortArr[i]) ) {
                        switch(i) {
                            case 0: curSort = HttpManager.BOOK_SORT_SIM; break;
                            case 1: curSort = HttpManager.BOOK_SORT_DATE; break;
                            case 2: curSort = HttpManager.BOOK_SORT_COUNT; break;
                            default: break;
                        }
                    }
                }

                final String searchSort = curSort;
                final String searchCategory = curCategory;

                mSearchBookAdapter.clear();
                mSearchBookAdapter.notifyDataSetChanged();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            recycleViewDataSetting(HttpManager.searchBookNaverApi(mEditTextSearch.getText().toString(),
//                                    10, searchSort));
                            recycleViewDataSetting(HttpManager.searchBookNaverXMLApi(mEditTextSearch.getText().toString(),
                                    10, searchCategory));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void recycleViewDataSetting(SearchBookItem[] data){
        ArrayList<SearchBookItem> dataArrList = new ArrayList<>(Arrays.asList(data));
        mSearchBookAdapter.addItems(dataArrList);

        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSearchBookAdapter.notifyDataSetChanged();
            }
        });
    }

}