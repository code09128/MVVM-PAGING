package com.drs24.pagingtest.Model;

import java.util.List;

/**
 * Created by dustin0128 on 2020/3/18
 * OriginURL:https://api.stackexchange.com/2.2/answers?page=1&pagesize=50&site=stackoverflow
 * JsonURL:https://jsoneditoronline.org/#left=local.yukuna&right=local.jiqimi
 */
public class StackApiReponse {
    public List<Item> items;
    public boolean has_more;
    public int quota_max;
    public int quota_remaining;
}
