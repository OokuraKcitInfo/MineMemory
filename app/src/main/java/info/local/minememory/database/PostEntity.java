package info.local.minememory.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;

@Entity(tableName = "t_post")
public class PostEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "post_id")
    private int postId;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "place_name")
    private String placeName;
    @ColumnInfo(name = "comment")
    private String comment;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "picture")
    private byte[] byteArrayPicture;

    private Bitmap bitmapPicture;

    @Ignore
    public PostEntity() { }

    public PostEntity(double latitude, double longitude, String date, String placeName, Bitmap bitmapPicture) {
        setLatitude(latitude);
        setLongitude(longitude);
        setDate(date);
        setPlaceName(placeName);
        // Todo: Bitmap 設定時に byte 配列を作成する処理が動作する
        setBitmapPicture(bitmapPicture);
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getByteArrayPicture() {
        return byteArrayPicture;
    }

    public Bitmap getBitmapFromByteArray() {
        return BitmapFactory.decodeByteArray(getByteArrayPicture(), 0, getByteArrayPicture().length);
    }

    private boolean isConvert = false;
    public void setByteArrayPicture(byte[] byteArrayPicture) {
        this.byteArrayPicture = byteArrayPicture;
        if (!isConvert) {
            isConvert = true;
            setBitmapPicture(BitmapFactory.decodeByteArray(byteArrayPicture, 0, byteArrayPicture.length));
            isConvert = false;
        }
    }

    public Bitmap getBitmapPicture() {
        return bitmapPicture;
    }

    public void setBitmapPicture(Bitmap bitmapPicture) {
        this.bitmapPicture = bitmapPicture;
        if (!isConvert) {
            isConvert = true;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            setByteArrayPicture(byteArrayOutputStream.toByteArray());
            isConvert = false;
        }
    }

    @Override
    public String toString() {
        return "PostEntity{" +
                "postId=" + postId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", placeName='" + placeName + '\'' +
                ", comment='" + comment + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
