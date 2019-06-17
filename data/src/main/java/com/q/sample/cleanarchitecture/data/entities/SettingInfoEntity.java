package com.q.sample.cleanarchitecture.data.entities;

import com.q.sample.cleanarchitecture.domain.models.SettingInfoModel;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingInfoEntity
{
    public static final float MIN_TEXT_SIZE = 12f;
    public static final float MAX_TEXT_SIZE = 50f;
    public static final float DEFAULT_TEXT_SIZE = (MAX_TEXT_SIZE - MIN_TEXT_SIZE) / 2;
    public static final String DEFAULT_TEXT_COLOR = "#FFFFFFFF";
    public static final String DEFAULT_BACKGROUND_COLOR = "FF000000";
    public static final String COLOR_STRING_FORMAT = "#%08x";

    public static final String _TEXT_SIZE = "text_size";
    public static final String _TEXT_COLOR = "text_color";
    public static final String _BACKGROUND_COLOR = "background_color";

    private float textSize;
    private String textColor;
    private String backgroundColor;

    public SettingInfoEntity() {
        textSize = DEFAULT_TEXT_SIZE;
        textColor = DEFAULT_TEXT_COLOR;
        backgroundColor = DEFAULT_BACKGROUND_COLOR;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(_TEXT_SIZE, textSize);
            jsonObject.put(_TEXT_COLOR, textColor);
            jsonObject.put(_BACKGROUND_COLOR, backgroundColor);
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
