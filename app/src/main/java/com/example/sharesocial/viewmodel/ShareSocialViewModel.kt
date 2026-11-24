package com.example.sharesocial.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sharesocial.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShareSocialViewModel : ViewModel() {

    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    private val _posts = MutableStateFlow(
        mutableListOf(
            Post(
                id = "p1",
                authorName = "Aarav Sharma",
                authorAvatar = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde",
                timeAgo = "2h ago",
                category = "Shayari",
                contentText = "Zindagi mein kuch pana ho toh tareeqe বদlo, iradhe nahi! ✨",
                imageUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23",
                likesCount = 1240,
                commentsCount = 88,
                sharesCount = 310,
                comments = mutableListOf(
                    Comment("c1", "Priya Singh", "Very true lines!", "1h ago"),
                    Comment("c2", "Rahul Verma", "Inspiring!", "30m ago")
                )
            ),
            Post(
                id = "p2",
                authorName = "Priya Tech",
                authorAvatar = "https://images.unsplash.com/photo-1494790108377-be9c29b29330",
                timeAgo = "4h ago",
                category = "Tech",
                contentText = "Jetpack Compose is making Android UI development so fast and delightful! What are you building today? 🚀",
                imageUrl = null,
                likesCount = 850,
                commentsCount = 45,
                sharesCount = 120,
                comments = mutableListOf(
                    Comment("c3", "Amit Kumar", "Compose is the future!", "2h ago")
                )
            ),
            Post(
                id = "p3",
                authorName = "Comedy Hub",
                authorAvatar = "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61",
                timeAgo = "6h ago",
                category = "Funny",
                contentText = "Me explaining to my mom why I need 5 more minutes of sleep after waking up for 2 hours 😂",
                imageUrl = "https://images.unsplash.com/photo-1533738363-b7f9aef128ce",
                likesCount = 3400,
                commentsCount = 210,
                sharesCount = 890,
                comments = mutableListOf()
            )
        )
    )
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _videos = MutableStateFlow(
        listOf(
            VideoClip(
                id = "v1",
                creatorName = "Neha Dance",
                creatorAvatar = "https://images.unsplash.com/photo-1534528741775-53994a69daeb",
                description = "New trending dance challenge! #dance #trending",
                soundName = "Original Sound - Neha Dance",
                videoUrl = "https://assets.mixkit.co/videos/preview/mixkit-girl-in-neon-lights-dancing-41585-large.mp4",
                likesCount = 15400,
                commentsCount = 420
            ),
            VideoClip(
                id = "v2",
                creatorName = "Chef Rakesh",
                creatorAvatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d",
                description = "Quick 5-minute street style noodles recipe 🍜",
                soundName = "Cooking Beats - Foodie",
                videoUrl = "https://assets.mixkit.co/videos/preview/mixkit-hands-preparing-food-in-a-kitchen-43093-large.mp4",
                likesCount = 8900,
                commentsCount = 190
            )
        )
    )
    val videos: StateFlow<List<VideoClip>> = _videos.asStateFlow()

    private val _conversations = MutableStateFlow(
        mutableListOf(
            ChatConversation(
                id = "ch1",
                name = "Priya Singh",
                avatar = "https://images.unsplash.com/photo-1494790108377-be9c29b29330",
                lastMessage = "Hey! Did you check out that new video?",
                time = "10:30 AM",
                unreadCount = 2,
                messages = mutableListOf(
                    ChatMessage("m1", "Priya", "Hey! Did you check out that new video?", "10:30 AM", false)
                )
            ),
            ChatConversation(
                id = "ch2",
                name = "Rahul Verma",
                avatar = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e",
                lastMessage = "Let's catch up tomorrow.",
                time = "Yesterday",
                unreadCount = 0,
                messages = mutableListOf(
                    ChatMessage("m2", "Rahul", "Let's catch up tomorrow.", "Yesterday", false)
                )
            )
        )
    )
    val conversations: StateFlow<List<ChatConversation>> = _conversations.asStateFlow()

    fun setLanguage(lang: String) {
        _selectedLanguage.value = lang
    }

    fun toggleLike(postId: String) {
        _posts.update { currentPosts ->
            currentPosts.map { post ->
                if (post.id == postId) {
                    val newLiked = !post.isLiked
                    post.copy(
                        isLiked = newLiked,
                        likesCount = if (newLiked) post.likesCount + 1 else post.likesCount - 1
                    )
                } else post
            }.toMutableList()
        }
    }

    fun addComment(postId: String, commentText: String) {
        if (commentText.isBlank()) return
        _posts.update { currentPosts ->
            currentPosts.map { post ->
                if (post.id == postId) {
                    val newComment = Comment(
                        id = "c_${System.currentTimeMillis()}",
                        authorName = "You",
                        text = commentText,
                        timeAgo = "Just now"
                    )
                    post.comments.add(newComment)
                    post.copy(commentsCount = post.commentsCount + 1)
                } else post
            }.toMutableList()
        }
    }

    fun createPost(contentText: String, category: String, imageUrl: String?) {
        if (contentText.isBlank()) return
        val newPost = Post(
            id = "p_${System.currentTimeMillis()}",
            authorName = "You",
            authorAvatar = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde",
            timeAgo = "Just now",
            category = category,
            contentText = contentText,
            imageUrl = if (imageUrl.isNullOrBlank()) null else imageUrl,
            likesCount = 0,
            commentsCount = 0,
            sharesCount = 0
        )
        _posts.update { current ->
            current.add(0, newPost)
            current.toMutableList()
        }
    }

    fun sendMessage(conversationId: String, text: String) {
        if (text.isBlank()) return
        _conversations.update { list ->
            list.map { conv ->
                if (conv.id == conversationId) {
                    val msg = ChatMessage(
                        id = "m_${System.currentTimeMillis()}",
                        senderId = "You",
                        text = text,
                        timestamp = "Just now",
                        isMe = true
                    )
                    conv.messages.add(msg)
                    conv.copy(lastMessage = text, time = "Just now")
                } else conv
            }.toMutableList()
        }
    }
}
