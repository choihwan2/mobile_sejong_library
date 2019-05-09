package com.fourB.library.SearchBook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourB.library.R;

import java.util.ArrayList;

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.ViewHolder> {
    Context context;
    ArrayList<SearchBookItem> items = new ArrayList<SearchBookItem>();

    public SearchBookAdapter(Context context){
        this.context = context;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    @NonNull
    @Override
    public SearchBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_search_book, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchBookAdapter.ViewHolder viewHolder, int i) {
        final SearchBookItem item = items.get(i);
        viewHolder.setItem(item);

        viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,item.getBookTitle(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addItem(SearchBookItem item){
        items.add(item);
    }

    public void addItems(ArrayList<SearchBookItem> items){
        this.items = items;
    }

    public SearchBookItem getItem (int position){
        return items.get(position);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mBookTitle;
        TextView mBookAuthor;
        TextView mBookPublisher;
        TextView mBookLend;
        TextView mBookPublishYear;
        TextView mBookCallNum;
        CardView mCardView;
        ImageView mBookImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mBookImageView = (ImageView)itemView.findViewById(R.id.imageView_search_book);
            mCardView = (CardView)itemView.findViewById(R.id.cardView_search_book_recycleView);
            mBookTitle = (TextView)itemView.findViewById(R.id.textView_search_book_title);
            mBookAuthor = (TextView)itemView.findViewById(R.id.textView_search_book_author);
            mBookPublisher = (TextView)itemView.findViewById(R.id.textView_search_book_publisher);
            mBookLend = (TextView)itemView.findViewById(R.id.textView_search_book_lend);
            mBookPublishYear = (TextView)itemView.findViewById(R.id.textView_search_book_publish_year);
            mBookCallNum = (TextView)itemView.findViewById(R.id.textView_search_book_callnum);
        }

        public void setItem(SearchBookItem item){
            mBookTitle.setText(item.getBookTitle());
            mBookAuthor.setText(item.getBookAuthor());
            mBookPublisher.setText(item.getBookPublisher());
            mBookPublishYear.setText(item.getBookPublishYear());
            mBookCallNum.setText(item.getBookCallNum());
            if(item.getBookLend()){
                mBookLend.setText("대출가능");
            }else {
                mBookLend.setText("대출불가");
            }

        }
    }
}