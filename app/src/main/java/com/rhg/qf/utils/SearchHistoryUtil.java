package com.rhg.qf.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rhg.qf.datebase.AccountDBHelper;
import com.rhg.qf.datebase.AccountDao;
import com.rhg.qf.datebase.DBProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:搜索历史
 * author：remember
 * time：2016/10/23 23:03
 * email：1013773046@qq.com
 */
public class SearchHistoryUtil {

    public static void insertSearchHistory(String content) {
        AccountDao.getInstance().saveSearchHistory(content);
    }

    /**
     * desc:插入一条历史记录
     * author：remember
     * time：2016/6/18 16:36
     * email：1013773046@qq.com
     */
    public static void insertSearchHistory(Context context, String content) {
        AccountDao.getInstance().saveSearchHistory(content);
        if (content == null) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put("searched", content);
        context.getContentResolver().insert(DBProvider.SEARCH_HISTORY_URI, values);
    }

    /**
     * desc:清空历史记录
     * author：remember
     * time：2016/6/18 16:37
     * email：1013773046@qq.com
     */
    public static void deleteAllHistory() {
        AccountDao.getInstance().deleteAllItemInTable(AccountDBHelper.Q_SEARCH_HISTORY_TABLE);
    }

    public static void deleteAllHistory(Context context) {
        context.getContentResolver().delete(DBProvider.SEARCH_HISTORY_URI, null, null);
    }

    /**
     * desc:获取历史记录
     * author：remember
     * time：2016/6/18 16:37
     * email：1013773046@qq.com
     */
    public static List<String> getAllHistory() {
        return AccountDao.getInstance().getSearchHistoryList();
    }

    public static List<String> getAllHistory(Context context) {
        List<String> mList = new ArrayList<>();
        Cursor cursor = /*(AccountDBHelper.Q_SEARCH_HISTORY_TABLE,
                new String[]{"searched"},
                null, null, null, null, null);*/context.getContentResolver().query(DBProvider.SEARCH_HISTORY_URI,
                null, null, null, null);
        if (cursor.moveToLast()) {
            do {
                String searchHistory = cursor.getString(1);
                Log.i("RHG", "INDEX1: " + cursor.getString(1) + " INDEX2: " + cursor.getInt(1));
                if (searchHistory != null && !"".equals(searchHistory)) {
                    mList.add(searchHistory);
                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        return mList;
    }

    public static List<String> getHistoryByName(String name) {
        return AccountDao.getInstance().queryHistory(name);
    }

    public static List<String> getHistoryByName(Context context, String name) {
        if (name == null) {
            return null;
        }
        List<String> mList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(DBProvider.SEARCH_HISTORY_URI, new String[]{"searched"}, "searched like ?",
                new String[]{"%" + name + "%"}, null);
        if (cursor.moveToLast()) {
            do {
                String historyContent = cursor.getString(0);
                if (historyContent != null && !"".equals(historyContent)) {
                    mList.add(historyContent);
                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        return mList;
    }
}
