package com.example.linsa.retrofitdemo.netprogress;

import android.content.ClipData.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linsa on 2018/3/19:16:29.
 * des:
 */

public class RetrofitBean {
    private Integer total_count;
    private Boolean incompleteResults;
    private List<Item> items = new ArrayList<Item>();

    /**
     *
     * @return
     *     The totalCount
     */
    public Integer getTotalCount() {
        return total_count;
    }

    /**
     *
     * @param totalCount
     *     The total_count
     */
    public void setTotalCount(Integer totalCount) {
        this.total_count = totalCount;
    }

    /**
     *
     * @return
     *     The incompleteResults
     */
    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    /**
     *
     * @param incompleteResults
     *     The incomplete_results
     */
    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    /**
     *
     * @return
     *     The items
     */
    public List<Item> getItems() {
        return items;
    }

}
