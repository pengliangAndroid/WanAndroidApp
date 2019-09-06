package com.funny.appframework.data.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @author pengl
 */


data class ProjectInfo(
    val apkLink: String,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Tag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

data class Tag(
    val name: String,
    val url: String
)

data class ArticleInfo(
    val apkLink: String?,
    val author: String?,
    val chapterId: Int,
    val chapterName: String?,
    val collect: Boolean,
    val courseId: Int,
    val desc: String?,
    val envelopePic: String?,
    val fresh: Boolean,
    val id: Int,
    val link: String?,
    val niceDate: String?,
    val origin: String?,
    val prefix: String?,
    val projectLink: String?,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String?,
    val tags: List<TagInfo>?,
    val title: String?,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int,
    var isTop: Boolean
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString(),
        1 == source.readInt(),
        source.readInt(),
        source.readString(),
        source.readString(),
        1 == source.readInt(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readLong(),
        source.readInt(),
        source.readString(),
        source.createTypedArrayList(TagInfo.CREATOR),
        source.readString(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(apkLink)
        writeString(author)
        writeInt(chapterId)
        writeString(chapterName)
        writeInt((if (collect) 1 else 0))
        writeInt(courseId)
        writeString(desc)
        writeString(envelopePic)
        writeInt((if (fresh) 1 else 0))
        writeInt(id)
        writeString(link)
        writeString(niceDate)
        writeString(origin)
        writeString(prefix)
        writeString(projectLink)
        writeLong(publishTime)
        writeInt(superChapterId)
        writeString(superChapterName)
        writeTypedList(tags)
        writeString(title)
        writeInt(type)
        writeInt(userId)
        writeInt(visible)
        writeInt(zan)
        writeInt((if (isTop) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ArticleInfo> = object : Parcelable.Creator<ArticleInfo> {
            override fun createFromParcel(source: Parcel): ArticleInfo = ArticleInfo(source)
            override fun newArray(size: Int): Array<ArticleInfo?> = arrayOfNulls(size)
        }
    }
}


data class TagInfo(val name: String?, val url: String?) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TagInfo> = object : Parcelable.Creator<TagInfo> {
            override fun createFromParcel(source: Parcel): TagInfo = TagInfo(source)
            override fun newArray(size: Int): Array<TagInfo?> = arrayOfNulls(size)
        }
    }
}

data class PageBean<T>(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    var datas: MutableList<T>
)

data class BannerInfo(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)

data class FriendLinkInfo(
    val icon: String,
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)

data class HotSearchKey(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)

data class SystemNodeInfo(
    val children: List<SystemNodeInfo>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class NavInfo(
    val apkLink: String,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Any>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

data class ProjectNodeInfo(
    val children: List<ProjectNodeInfo>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)