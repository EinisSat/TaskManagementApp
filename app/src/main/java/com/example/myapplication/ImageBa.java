package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "images")
public class ImageBa {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public ImageBa(int id, byte[] image)
    {
        this.id = id;
        this.image = image;
    }

    public void setId(int id) { this.id = id;}
    public int getId() { return this.id;}

    public void setImage(byte[] image) {this.image = image;}
    public byte[] getImage() {return this.image;}

}
