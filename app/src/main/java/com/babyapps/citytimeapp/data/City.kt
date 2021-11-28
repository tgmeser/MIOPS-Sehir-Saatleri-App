package com.babyapps.citytimeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "city_table")
data class City(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("colorName")
    @Expose
    val colorName: String,
    @SerializedName("colorCode")
    @Expose
    val colorCode: String,
    @SerializedName("timeDifference")
    @Expose
    val timeDifference: String,
):Serializable