package com.drs24.pagingtest.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.drs24.pagingtest.Model.Item;
import com.drs24.pagingtest.R;
import com.drs24.pagingtest.databinding.RecyclerviewItemBinding;

/**
 * Created by dustin0128 on 2020/3/18
 */
public class ItemAdapter extends PagedListAdapter<Item,ItemAdapter.ItemViewHolder> {

    private Context mCtx;

    protected ItemAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }

    /**用binding的方式綁定元件*/
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerviewItemBinding binding = RecyclerviewItemBinding.inflate(layoutInflater, parent, false);

        return new ItemViewHolder(binding);
    }

    /**
     * 當我們要使用 Paging 時，我們會先把 RecyclerView 所用到的 Adapter 換去繼承 PagedListAdapter(DiffUtils)，
     * 且在 onBindViewHolder()做資料綁定時呼叫 getItem(position) 來取得目前位置的資料，
     * 這時候 PagedListAdapter 就會知道現在已經在列表的什麼位置，以及決定是否要觸發下一頁載入。
     */
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = getItem(position);

        if (item != null){
            holder.binding.imageView.setImageResource(R.drawable.ic_launcher_foreground);
            holder.binding.textViewName.setText(item.owner.display_name);
        }
        else{
            Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 這類別繼承了 RecyclerView.Adapter，用來顯示 PagedList，建立時需要傳遞一個 DiffUtils 的實作，
     * 好讓 Paging 載入更多資料時，可以用來比較差異後顯示新資料（計算差異會在背景執行）。
     *
     * 需建立DIFF_CALLBACK，這像是精簡版的DiffUtil，比較好寫之外還會自動在background thread計算資料差異。
     * 原本的getItemCount()那些不用寫了，PagedListAdapter會幫我們處理，並提供內建的method如getItem(position)讓我們取得資料，
     * 整體比RecyclerView Adapter還簡潔。
     * */
    private static DiffUtil.ItemCallback<Item> DIFF_CALLBACK = new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.answer_id == oldItem.answer_id;
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.equals(newItem);
        }
    };

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private RecyclerviewItemBinding binding;

        public ItemViewHolder(RecyclerviewItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
