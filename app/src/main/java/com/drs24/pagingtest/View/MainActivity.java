package com.drs24.pagingtest.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.drs24.pagingtest.Model.Item;
import com.drs24.pagingtest.Model.StackApiReponse;
import com.drs24.pagingtest.R;
import com.drs24.pagingtest.ViewModel.ItemViewModel;
import com.drs24.pagingtest.WebService.RetrofitClient;
import com.drs24.pagingtest.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

       initUI();

       //View接收資料顯示於畫面上
        ItemViewModel itemViewModel = new ItemViewModel();
        itemViewModel.itemPagedList.observe(this, new Observer<PagedList<Item>>() {
            @Override
            public void onChanged(PagedList<Item> items) {
                adapter.submitList(items);
            }
        });

        binding.recyclerView.setAdapter(adapter);
    }

    private void initUI() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(this);
    }
}
