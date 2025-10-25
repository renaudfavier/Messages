package com.renaudfavier.messages.chat.presentation.conversation.component

import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationListItemUiModel
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationListItemUiModel.MessageUiModel.EmojiMessage
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationListItemUiModel.MessageUiModel.TextMessage
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationListItemUiModel.TimeUiModel
import kotlinx.collections.immutable.persistentListOf

val sampleMessages = persistentListOf<ConversationListItemUiModel>(
    TimeUiModel(id = "time_1", text = "Yesterday"),
    TextMessage(
        id = "msg_1",
        isMine = false,
        text = "Hey! Are you free for coffee tomorrow?"
    ),
    TextMessage(
        id = "msg_2",
        isMine = true,
        text = "Yeah, sounds great! What time works for you?"
    ),
    TextMessage(
        id = "msg_3",
        isMine = false,
        text = "How about 10am at the usual place?"
    ),
    TextMessage(
        id = "msg_4",
        isMine = true,
        text = "Perfect! See you then"
    ),
    EmojiMessage(
        id = "msg_5",
        isMine = false,
        emoji = "👍"
    ),

    TimeUiModel(id = "time_2", text = "Today, 9:45 AM"),
    TextMessage(
        id = "msg_6",
        isMine = false,
        text = "Morning! Running about 15 mins late, sorry!"
    ),
    TextMessage(
        id = "msg_7",
        isMine = true,
        text = "No worries! I'll grab us a table"
    ),
    EmojiMessage(
        id = "msg_8",
        isMine = false,
        emoji = "🙏"
    ),

    TimeUiModel(id = "time_3", text = "10:05 AM"),
    TextMessage(
        id = "msg_9",
        isMine = false,
        text = "Here! Where are you sitting?"
    ),
    TextMessage(
        id = "msg_10",
        isMine = true,
        text = "By the window on the left side"
    ),

    TimeUiModel(id = "time_4", text = "11:30 AM"),
    TextMessage(
        id = "msg_11",
        isMine = true,
        text = "That was really nice! Thanks for catching up"
    ),
    TextMessage(
        id = "msg_12",
        isMine = false,
        text = "Absolutely! We should do this more often"
    ),
    EmojiMessage(
        id = "msg_13",
        isMine = true,
        emoji = "☕"
    ),
    EmojiMessage(
        id = "msg_14",
        isMine = false,
        emoji = "😊"
    ),

    TimeUiModel(id = "time_5", text = "2:15 PM"),
    TextMessage(
        id = "msg_15",
        isMine = false,
        text = "Did you leave your scarf at the cafe? They just posted on their story"
    ),
    TextMessage(
        id = "msg_16",
        isMine = true,
        text = "Oh no! I think I did"
    ),
    TextMessage(
        id = "msg_17",
        isMine = true,
        text = "Can you grab it? I'm already home"
    ),
    TextMessage(
        id = "msg_18",
        isMine = false,
        text = "Sure thing! I'm nearby, I'll pick it up for you"
    ),
    EmojiMessage(
        id = "msg_19",
        isMine = true,
        emoji = "🙌"
    ),
    TextMessage(
        id = "msg_20",
        isMine = true,
        text = "You're the best!"
    )
)