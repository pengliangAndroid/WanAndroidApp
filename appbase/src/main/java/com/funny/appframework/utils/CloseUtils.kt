package com.funny.appframework.utils

import android.database.Cursor

import java.io.Closeable
import java.io.IOException


object CloseUtils {

    /**
     * If the argument is non-null, close the Closeable ignoring any [IOException].
     */
    fun closeQuietly(closeable: Closeable?) {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (e: IOException) {
                // Ignore.
            }

        }
    }

    /** If the argument is non-null, close the cursor.  */
    fun closeQuietly(cursor: Cursor?) {
        cursor?.close()
    }
}