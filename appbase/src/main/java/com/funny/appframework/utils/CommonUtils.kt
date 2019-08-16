package com.funny.appframework.utils

import android.text.TextUtils


object CommonUtils {

    /**
     * 是否空集合
     * @param collection
     * @return
     */
    fun isEmptyArray(collection: Collection<*>?): Boolean {
        return collection == null || collection.size == 0
    }

    /**
     * 是否空字符串
     * @param str
     * @return
     */
    fun isEmpty(str: String): Boolean {
        return TextUtils.isEmpty(str) || str == "null"
    }

    /**
     * 高亮部分文字
     * @param html "<font color='#FF0000'>%s</font>"
     * @param name
     * @param text
     * @return
     */
    fun highlightText(html: String, name: String, text: String): String {
        var name = name
        if (TextUtils.isEmpty(text)) {
            return name
        }

        if (name.contains(text)) {
            val replaceHtml = html.replace("%s", text)
            name = name.replace(text.toRegex(), replaceHtml)
        }

        return name
    }

    /**
     * 二位数的格式化，若只有一位，前面添个0
     * @param value
     * @return
     */
    fun formatOct(value: Int): String {
        return if (value < 10) {
            "0$value"
        } else Integer.toString(value)
    }


    /**
     * 字符串格式化
     * @param str
     * @return
     */
    fun format(str: String): String {
        return if (isEmpty(str)) {
            ""
        } else str

    }


}
