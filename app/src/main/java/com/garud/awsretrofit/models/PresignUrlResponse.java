package com.garud.awsretrofit.models;

import java.util.HashMap;
import java.util.Map;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PresignUrlResponse implements Parcelable
{

    @SerializedName("uploadURL")
    @Expose
    private String uploadURL;
    @SerializedName("getURL")
    @Expose
    private String getURL;
    @SerializedName("Key")
    @Expose
    private String key;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<PresignUrlResponse> CREATOR = new Creator<PresignUrlResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PresignUrlResponse createFromParcel(android.os.Parcel in) {
            return new PresignUrlResponse(in);
        }

        public PresignUrlResponse[] newArray(int size) {
            return (new PresignUrlResponse[size]);
        }

    }
            ;

    protected PresignUrlResponse(android.os.Parcel in) {
        this.uploadURL = ((String) in.readValue((String.class.getClassLoader())));
        this.getURL = ((String) in.readValue((String.class.getClassLoader())));
        this.key = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public PresignUrlResponse() {
    }

    /**
     *
     * @param getURL
     * @param uploadURL
     * @param key
     */
    public PresignUrlResponse(String uploadURL, String getURL, String key) {
        super();
        this.uploadURL = uploadURL;
        this.getURL = getURL;
        this.key = key;
    }

    public String getUploadURL() {
        return uploadURL;
    }

    public void setUploadURL(String uploadURL) {
        this.uploadURL = uploadURL;
    }

    public String getGetURL() {
        return getURL;
    }

    public void setGetURL(String getURL) {
        this.getURL = getURL;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PresignUrlResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("uploadURL");
        sb.append('=');
        sb.append(((this.uploadURL == null)?"<null>":this.uploadURL));
        sb.append(',');
        sb.append("getURL");
        sb.append('=');
        sb.append(((this.getURL == null)?"<null>":this.getURL));
        sb.append(',');
        sb.append("key");
        sb.append('=');
        sb.append(((this.key == null)?"<null>":this.key));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(uploadURL);
        dest.writeValue(getURL);
        dest.writeValue(key);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return 0;
    }

}
