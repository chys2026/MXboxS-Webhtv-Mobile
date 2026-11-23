package com.silent.android.mxboxs.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.silent.android.mxboxs.bean.Site;

import java.util.List;

@Dao
public abstract class SiteDao extends BaseDao<Site> {

    @Query("SELECT * FROM Site")
    public abstract List<Site> findAll();

    @Query("DELETE FROM Site")
    public abstract void delete();
}
