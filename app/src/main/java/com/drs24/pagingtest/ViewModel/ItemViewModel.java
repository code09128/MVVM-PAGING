package com.drs24.pagingtest.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.drs24.pagingtest.DataSource.ItemDataSource;
import com.drs24.pagingtest.DataSource.ItemDataSourceFactory;
import com.drs24.pagingtest.Model.Item;

/**
 * Created by dustin0128 on 2020/3/19
 */
public class ItemViewModel extends ViewModel {

    public LiveData<PagedList<Item>> itemPagedList;
    private LiveData<PageKeyedDataSource<Integer,Item>> liveDataSource;

    public ItemViewModel(){
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory();
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        /*
        * 配置頁面列表PageList的作用：
        1.從數據源取出數據
        2.負責控制首先第一次加載多少數據，之後每一次加載多少數據，如何加載等等
        3.將數據的變更反映到UI上。
        * */
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)//配置是否啟動PlaceHolders
                .setPageSize(ItemDataSource.PAGE_SIZE) //配置分頁加載量
                .build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();
    }
}
