package com.example.tripplanner;
// Entity class

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// todo make a table for storing our data
@Entity(tableName = "RiderLocationTable")
public class dataHolder {
    @PrimaryKey(autoGenerate = true)
    private int locationId;
    @ColumnInfo(name = "latitude")
    private String lttd;

    @ColumnInfo(name = "Longitude")
    private String lngtd;


    public dataHolder(int locationId, String lttd, String lngtd) {
        this.lttd = lttd;
        this.lngtd = lngtd;
        this.locationId = locationId;
    }

    @Ignore
    public dataHolder(String lttd, String lngtd) {
        this.lngtd = lngtd;
        this.lttd = lttd;
    }


    public String getLttd() {
        return lttd;
    }

    public void setLttd(String lttd) {
        this.lttd = lttd;
    }

    public String getLngtd() {
        return lngtd;
    }

    public void setLngtd(String lngtd) {
        this.lngtd = lngtd;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}



