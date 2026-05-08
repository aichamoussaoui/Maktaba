package com.ElOuedUniv.maktaba.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    @SerialName("isbn")
    val isbn: String,
    @SerialName("title")
    val title: String,
    @SerialName("nb_pages") // تم التغيير ليطابق اسم العمود الذي سنضيفه
    val nbPages: Int,
    @SerialName("image_url") // تم التغيير ليطابق اسم العمود في الصورة
    val imageUrl: String? = null,
    @SerialName("is_finished") // تم التغيير ليطابق اسم العمود الذي سنضيفه
    val isFinished: Boolean = false
)
