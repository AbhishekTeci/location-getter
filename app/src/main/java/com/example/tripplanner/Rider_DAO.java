package com.example.tripplanner;

// TODO COMPLeTE DAO class for storage lati amd longitude

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface Rider_DAO {

    @Query("SELECT * FROM RiderLocationTable")
    List<dataHolder> getAllLocation();

    @Insert
    void addData(dataHolder data_holder);
    @Update
    void updateData(dataHolder data_holder);


}
