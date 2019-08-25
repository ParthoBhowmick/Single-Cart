package com.example.singlepagerivew.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {
    @SerializedName("option_id")
    @Expose
    private String optionId;
    @SerializedName("option_name")
    @Expose
    private String optionName;
    @SerializedName("value_id")
    @Expose
    private String valueId;
    @SerializedName("value_name")
    @Expose
    private String valueName;
    @SerializedName("image_url")
    @Expose
    private Object imageUrl;

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }
}
