package com.lhf.test.lhfandroidutils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Spannable的工具类
 *
 * @author zhangrq
 */
public class SpannableStringUtils {
    /**
     * 获取字体内容和颜色
     *
     * @param map 键为字符串内容，值为color的int值（--例如：getResources().getColor(R.color.black)--），
     *            color值为null则为默认颜色
     * @return
     */
    public static SpannableString getSpannableString(LinkedHashMap<String, Integer> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        // 创建spannable
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key != null)
                stringBuilder.append(key);
//			else
//				stringBuilder.append("");
        }
        SpannableString msp = new SpannableString(stringBuilder.toString());
        // 设置颜色
        int startIndex = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (key != null) {
                int endIndex = startIndex + key.length();
                if (value != null) {//设置字体颜色
                    msp.setSpan(new ForegroundColorSpan(value), startIndex,
                            endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                startIndex = endIndex;
            }
        }
        return msp;
    }

    /**
     * 获取字体内容、字体大小(单位sp)、字体颜色
     *
     * @param textSettingList 封装了字体设置信息的list
     * @return SpannableString 返回设置好的SpannableString
     */
    public static SpannableString getSpannableStr(ArrayList<TextSetting> textSettingList) {
        if (textSettingList == null || textSettingList.size() == 0) {
            return null;
        }
        // 创建spannable
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < textSettingList.size(); i++) {
            TextSetting textSetting = textSettingList.get(i);
            String text = textSetting.getText();
            if (text != null)
                stringBuilder.append(text);
        }
        SpannableString msp = new SpannableString(stringBuilder.toString());
        // 设置字体大小、颜色
        int startIndex = 0;
        for (int i = 0; i < textSettingList.size(); i++) {
            TextSetting textSetting = textSettingList.get(i);
            String text = textSetting.getText();
            int textColor = textSetting.getTextColor();
            int textSize = textSetting.getTextSize();

            if (text != null) {//文本为空不设置，不为空后再进行设置
                int endIndex = startIndex + text.length();
                //字体大小
                if (textSize != 0) {//字体大小有设置，设置字体大小
                    msp.setSpan(new AbsoluteSizeSpan(textSize, true), startIndex,
                            endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                //字体颜色
                if (textColor != 0) {//字体颜色有设置，设置字体颜色
                    msp.setSpan(new ForegroundColorSpan(textColor), startIndex,
                            endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                startIndex = endIndex;
            }
        }

        return msp;
    }

    public static class TextSetting {
        /**
         * 字体内容
         */
        public String text;
        /**
         * 字体大小
         */
        public int textSize;
        /**
         * 字体颜色
         */
        public int textColor;

        public TextSetting() {
            super();
        }

        /**
         * 字体设置
         *
         * @param text      字体内容
         * @param textSize  字体大小  单位sp
         * @param textColor 字体颜色
         */
        public TextSetting(String text, int textSize, int textColor) {
            super();
            this.text = text;
            this.textSize = textSize;
            this.textColor = textColor;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getTextSize() {
            return textSize;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }
    }


    /**
     * 设置字体不同大小
     *
     * @param context
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static SpannableStringBuilder changeTextSize(Context context, String str, int start, int end, int sp) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new AbsoluteSizeSpan(PixelUtil.dip2px(context, sp)), start,
                end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return ssb;
    }

    /**
     * 设置字体不同颜色
     *
     * @param str
     * @param start
     * @param end
     * @param color 十六进制颜色值
     * @return
     */
    public static SpannableStringBuilder changeTextColor(String str, int start, int end, int color) {
        ForegroundColorSpan colorSpanRed = new ForegroundColorSpan(color);
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(colorSpanRed, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        return ssb;
    }

    /**
     * @param str
     * @param colorStr like "#fc871e"
     * @param start
     * @param end
     * @return
     */
    public static SpannableStringBuilder getSpanableColorString(CharSequence str,
                                                                String colorStr, int start, int end) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor(colorStr)), start,
                end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return ssb;
    }

    public static SpannableStringBuilder getSpanableTextSizeAndColor(Context context, CharSequence str,
                                                                     int color, int colorStart, int colorEnd, int textStart, int textEnd, int sp) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        ssb.setSpan(new AbsoluteSizeSpan(PixelUtil.dip2px(context, sp)), textStart,
                textEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ForegroundColorSpan colorSpanRed = new ForegroundColorSpan(color);
        ssb.setSpan(colorSpanRed, colorStart, colorEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return ssb;
    }
}
