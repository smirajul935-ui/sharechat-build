package com.example.sharesocial.model

data class Post(
    val id: String,
    val authorName: String,
    val authorAvatar: String,
    val timeAgo: String,
    val category: String,
    val contentText: String,
    val imageUrl: String? = null,
    var likesCount: Int,
    var commentsCount: Int,
    var sharesCount: Int,
    var isLiked: Boolean = false,
    val comments: MutableList<Comment> = mutableListOf()
)

data class Comment(
    val id: String,
    val authorName: String,
    val text: String,
    val timeAgo: String
)

data class VideoClip(
    val id: String,
    val creatorName: String,
    val creatorAvatar: String,
    val description: String,
    val soundName: String,
    val videoUrl: String,
    var likesCount: Int,
    var commentsCount: Int,
    var isLiked: Boolean = false
)

data class ChatConversation(
    val id: String,
    val name: String,
    val avatar: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int,
    val messages: MutableList<ChatMessage> = mutableListOf()
)

data class ChatMessage(
    val id: String,
    val senderId: String,
    val text: String,
    val timestamp: String,
    val isMe: Boolean
)
