package com.funny.appframework.utils


import com.funny.app.base.BuildConfig
import java.io.*
import java.util.*

object LogUtil{

    internal var TAG = "LogUtil"

    internal val DEBUG = 0// 调试版本
    internal val BETA = 1// 公开测试版
    internal val RELEASE = 2// 发布版本

    internal val LOG_FOLDER_NAME = "GLogs"// 文件夹名称

    private var CURRENT_VERSION = if (BuildConfig.DEBUG) 0 else 0// 当前版本

    fun setDebugMode(flag: Boolean) {
        if (flag) {
            CURRENT_VERSION = DEBUG
        } else {
            CURRENT_VERSION = RELEASE
        }
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg
     * The message you would like logged.
     */
    fun v(msg: String) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.v(TAG, buildMessage(msg))

            else -> {
            }
        }
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg
     * The message you would like logged.
     */
    fun v(tag: String, msg: String) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.v(tag, buildMessage(msg))

            else -> {
            }
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg
     * The message you would like logged.
     * @param thr
     * An exception to log
     */
    fun v(msg: String, thr: Throwable) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.v(msg, buildMessage(msg), thr)

            else -> {
            }
        }
    }

    /**
     * Send a DEBUG log message.
     *
     * @param msg
     */
    fun d(msg: String) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.d(TAG, buildMessage(msg))

            else -> {
            }
        }
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg
     * The message you would like logged.
     * @param thr
     * An exception to log
     */
    fun d(msg: String, thr: Throwable) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.d(msg, buildMessage(msg), thr)

            else -> {
            }
        }
    }

    fun d(tag: String, msg: String) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.d(tag, buildMessage(msg))

            else -> {
            }
        }
    }

    /**
     * Send an INFO log message.
     *
     * @param msg
     * The message you would like logged.
     */
    fun i(msg: String) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.i(TAG, buildMessage(msg))

            else -> {
            }
        }
    }

    fun i(tag: String, msg: String) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.i(tag, buildMessage(msg))

            else -> {
            }
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg
     * The message you would like logged.
     * @param thr
     * An exception to log
     */
    fun i(msg: String, thr: Throwable) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.i(TAG, buildMessage(msg), thr)

            else -> {
            }
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param msg
     * The message you would like logged.
     */
    fun e(msg: String) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.e(TAG, buildMessage(msg))

            else -> {
            }
        }
    }

    fun e(tag: String, msg: String) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.e(tag, buildMessage(msg))

            else -> {
            }
        }
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg
     * The message you would like logged.
     * @param thr
     * An exception to log
     */
    fun e(msg: String, thr: Throwable) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.e(TAG, buildMessage(msg), thr)

            else -> {
            }
        }
    }

    /**
     * Send a WARN log message
     *
     * @param msg
     * The message you would like logged.
     */
    fun w(msg: String) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.w(TAG, buildMessage(msg))

            else -> {
            }
        }
    }

    fun w(tag: String, msg: String) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.w(tag, buildMessage(msg))

            else -> {
            }
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg
     * The message you would like logged.
     * @param thr
     * An exception to log
     */
    fun w(msg: String, thr: Throwable) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.w(TAG, buildMessage(msg), thr)

            else -> {
            }
        }
    }

    /**
     * Send an empty WARN log message and log the exception.
     *
     * @param thr
     * An exception to log
     */
    fun w(thr: Throwable) {
        when (CURRENT_VERSION) {
            DEBUG -> android.util.Log.w(TAG, buildMessage(""), thr)

            else -> {
            }
        }
    }

    /**
     * Building Message
     *
     * @param msg
     * The message you would like logged.
     * @return Message String
     */
    internal fun buildMessage(msg: String?): String {
        var msg = msg
        if (msg == null) msg = ""
        // StackTraceElement caller = new
        // Throwable().fillInStackTrace().getStackTrace()[2];

        // return new
        // StringBuilder().append(caller.getClassName()).append(".").append(caller.getMethodName()).append("().").append(caller.getLineNumber()).append(": ").append(msg).toString();
        return msg
    }


    fun f(msg: String) {
        f(LOG_FOLDER_NAME, msg)
    }

    fun f(ex: Throwable) {
        f(LOG_FOLDER_NAME, ex)
    }

    fun f(folderName: String, ex: Throwable) {
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()

        val msg = writer.toString()
        f(folderName, msg)
    }

    fun f(folderName: String, msg: String) {
        var fileWriter: FileWriter? = null
        val logFile = getLogFile(folderName, "logs")

        try {
            fileWriter = FileWriter(logFile, true)
            fileWriter.append(toDateString(System.currentTimeMillis()))
            fileWriter.append("\r\n")
            fileWriter.append(msg)
            fileWriter.append("\r\n")
            fileWriter.flush()

            //CloseUtils.closeQuietly(fileWriter);
            fileWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun toDateString(timeMilli: Long): String {
        val calc = Calendar.getInstance()
        calc.timeInMillis = timeMilli
        return String.format(
            Locale.CHINESE,
            "%04d.%02d.%02d %02d:%02d:%02d:%03d",
            calc.get(Calendar.YEAR),
            calc.get(Calendar.MONTH) + 1,
            calc.get(Calendar.DAY_OF_MONTH),
            calc.get(Calendar.HOUR_OF_DAY),
            calc.get(Calendar.MINUTE),
            calc.get(Calendar.SECOND),
            calc.get(Calendar.MILLISECOND)
        )
    }

    private fun getLogFile(folderName: String, fileName: String): File {
        val folder = File(folderName)
        if (!folder.exists()) {
            folder.mkdirs()
        }

        var newFileCount = 0
        var newFile: File
        var existingFile: File? = null

        newFile = File(folder, String.format("%s_%s.log", fileName, newFileCount))
        while (newFile.exists()) {
            existingFile = newFile
            newFileCount++
            newFile = File(folder, String.format("%s_%s.log", fileName, newFileCount))
        }

        return if (existingFile != null) {
            if (existingFile.length() >= 2 * 1024 * 1024) {
                newFile
            } else existingFile
        } else newFile

    }

}