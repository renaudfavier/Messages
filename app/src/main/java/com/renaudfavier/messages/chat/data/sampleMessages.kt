package com.renaudfavier.messages.chat.data

import com.renaudfavier.messages.chat.domain.Message
import com.renaudfavier.messages.core.domain.toContactId
import java.time.Instant
import java.time.temporal.ChronoUnit

val sampleMessages = listOf(
    // Conversation with John (ContactId 1) - VERY ACTIVE - 40 messages over the past month
    // Started 3 weeks ago, very active conversation
    Message(1, 1.toContactId(), 0.toContactId(), Instant.now().minus(21, ChronoUnit.DAYS), "Hey! Long time no see!", false),
    Message(2, 0.toContactId(), 1.toContactId(), Instant.now().minus(21, ChronoUnit.DAYS).minus(2, ChronoUnit.HOURS), "John! How have you been?", false),
    Message(3, 1.toContactId(), 0.toContactId(), Instant.now().minus(21, ChronoUnit.DAYS).minus(2, ChronoUnit.HOURS).minus(15, ChronoUnit.MINUTES), "Great! Just got back from a trip", false),
    Message(4, 1.toContactId(), 0.toContactId(), Instant.now().minus(21, ChronoUnit.DAYS).minus(2, ChronoUnit.HOURS).minus(16, ChronoUnit.MINUTES), "Want to grab coffee sometime?", false),
    Message(5, 0.toContactId(), 1.toContactId(), Instant.now().minus(21, ChronoUnit.DAYS).minus(3, ChronoUnit.HOURS), "Absolutely! When are you free?", false),
    Message(6, 1.toContactId(), 0.toContactId(), Instant.now().minus(20, ChronoUnit.DAYS), "How about this weekend?", false),
    Message(7, 0.toContactId(), 1.toContactId(), Instant.now().minus(20, ChronoUnit.DAYS).minus(30, ChronoUnit.MINUTES), "Saturday works!", false),
    Message(8, 1.toContactId(), 0.toContactId(), Instant.now().minus(20, ChronoUnit.DAYS).minus(1, ChronoUnit.HOURS), "Perfect", false),
    Message(9, 1.toContactId(), 0.toContactId(), Instant.now().minus(20, ChronoUnit.DAYS).minus(1, ChronoUnit.HOURS).minus(1, ChronoUnit.MINUTES), "👍", false),
    Message(10, 0.toContactId(), 1.toContactId(), Instant.now().minus(20, ChronoUnit.DAYS).minus(2, ChronoUnit.HOURS), "See you at 10am?", false),
    Message(11, 1.toContactId(), 0.toContactId(), Instant.now().minus(20, ChronoUnit.DAYS).minus(2, ChronoUnit.HOURS).minus(5, ChronoUnit.MINUTES), "👌", false),

    // Weekend meetup
    Message(12, 1.toContactId(), 0.toContactId(), Instant.now().minus(14, ChronoUnit.DAYS).minus(30, ChronoUnit.MINUTES), "On my way", false),
    Message(13, 0.toContactId(), 1.toContactId(), Instant.now().minus(14, ChronoUnit.DAYS).minus(25, ChronoUnit.MINUTES), "Already here, got us a table", false),
    Message(14, 1.toContactId(), 0.toContactId(), Instant.now().minus(14, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS), "That was great! We should do this more often", false),
    Message(15, 0.toContactId(), 1.toContactId(), Instant.now().minus(14, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS).plus(10, ChronoUnit.MINUTES), "Definitely! Had so much fun", false),
    Message(16, 0.toContactId(), 1.toContactId(), Instant.now().minus(14, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS).plus(11, ChronoUnit.MINUTES), "😊", false),

    // Planning another activity
    Message(17, 1.toContactId(), 0.toContactId(), Instant.now().minus(10, ChronoUnit.DAYS), "Hey, you free this Friday?", false),
    Message(18, 1.toContactId(), 0.toContactId(), Instant.now().minus(10, ChronoUnit.DAYS).plus(5, ChronoUnit.MINUTES), "Thinking about checking out that new restaurant", false),
    Message(19, 0.toContactId(), 1.toContactId(), Instant.now().minus(10, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS), "Sorry, just saw this", false),
    Message(20, 0.toContactId(), 1.toContactId(), Instant.now().minus(10, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(1, ChronoUnit.MINUTES), "Yes! I've been wanting to try it", false),
    Message(21, 1.toContactId(), 0.toContactId(), Instant.now().minus(10, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(3, ChronoUnit.MINUTES), "Great! I'll make a reservation", false),
    Message(22, 0.toContactId(), 1.toContactId(), Instant.now().minus(10, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(5, ChronoUnit.MINUTES), "🙏", false),

    // Day before dinner
    Message(23, 1.toContactId(), 0.toContactId(), Instant.now().minus(6, ChronoUnit.DAYS), "Reservation confirmed for tomorrow at 7pm", false),
    Message(24, 0.toContactId(), 1.toContactId(), Instant.now().minus(6, ChronoUnit.DAYS).plus(20, ChronoUnit.MINUTES), "Perfect! Can't wait", false),
    Message(25, 1.toContactId(), 0.toContactId(), Instant.now().minus(6, ChronoUnit.DAYS).plus(21, ChronoUnit.MINUTES), "🎉", false),

    // Dinner day
    Message(26, 1.toContactId(), 0.toContactId(), Instant.now().minus(5, ChronoUnit.DAYS).minus(2, ChronoUnit.HOURS), "Heading out now", false),
    Message(27, 0.toContactId(), 1.toContactId(), Instant.now().minus(5, ChronoUnit.DAYS).minus(1, ChronoUnit.HOURS).minus(45, ChronoUnit.MINUTES), "Me too, see you there", false),
    Message(28, 1.toContactId(), 0.toContactId(), Instant.now().minus(5, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS), "The food was amazing!", false),
    Message(29, 1.toContactId(), 0.toContactId(), Instant.now().minus(5, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS).plus(1, ChronoUnit.MINUTES), "That dessert though", false),
    Message(30, 1.toContactId(), 0.toContactId(), Instant.now().minus(5, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS).plus(2, ChronoUnit.MINUTES), "😋", false),
    Message(31, 0.toContactId(), 1.toContactId(), Instant.now().minus(5, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS).plus(10, ChronoUnit.MINUTES), "Right?? We're definitely going back", false),
    Message(32, 1.toContactId(), 0.toContactId(), Instant.now().minus(5, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS).plus(12, ChronoUnit.MINUTES), "Absolutely", false),

    // Recent messages
    Message(33, 0.toContactId(), 1.toContactId(), Instant.now().minus(3, ChronoUnit.DAYS), "Did you finish that project you mentioned?", false),
    Message(34, 1.toContactId(), 0.toContactId(), Instant.now().minus(3, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS), "Almost! Should be done by end of week", false),
    Message(35, 0.toContactId(), 1.toContactId(), Instant.now().minus(3, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS).plus(5, ChronoUnit.MINUTES), "Nice! Let me know how it goes", false),
    Message(36, 1.toContactId(), 0.toContactId(), Instant.now().minus(3, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS).plus(6, ChronoUnit.MINUTES), "Will do!", false),

    // Today's messages
    Message(37, 1.toContactId(), 0.toContactId(), Instant.now().minus(4, ChronoUnit.HOURS), "Just finished the project!", false),
    Message(38, 1.toContactId(), 0.toContactId(), Instant.now().minus(4, ChronoUnit.HOURS).plus(1, ChronoUnit.MINUTES), "🎊", false),
    Message(39, 0.toContactId(), 1.toContactId(), Instant.now().minus(3, ChronoUnit.HOURS).minus(30, ChronoUnit.MINUTES), "Congratulations!! That's awesome", false),
    Message(40, 0.toContactId(), 1.toContactId(), Instant.now().minus(3, ChronoUnit.HOURS).minus(29, ChronoUnit.MINUTES), "We should celebrate", false),
    Message(41, 1.toContactId(), 0.toContactId(), Instant.now().minus(2, ChronoUnit.HOURS), "Sounds great! When?", false),
    Message(42, 0.toContactId(), 1.toContactId(), Instant.now().minus(1, ChronoUnit.HOURS).minus(45, ChronoUnit.MINUTES), "This weekend?", false),
    Message(43, 1.toContactId(), 0.toContactId(), Instant.now().minus(25, ChronoUnit.MINUTES), "Perfect! Let's do it", true),
    Message(44, 1.toContactId(), 0.toContactId(), Instant.now().minus(24, ChronoUnit.MINUTES), "🍻", true),

    // Conversation with Peter (ContactId 2) - MODERATE - 15 messages spread over 2 months
    Message(45, 2.toContactId(), 0.toContactId(), Instant.now().minus(60, ChronoUnit.DAYS), "Hey! How's it going?", false),
    Message(46, 0.toContactId(), 2.toContactId(), Instant.now().minus(59, ChronoUnit.DAYS).minus(3, ChronoUnit.HOURS), "Good! Been busy with work", false),
    Message(47, 2.toContactId(), 0.toContactId(), Instant.now().minus(59, ChronoUnit.DAYS).minus(2, ChronoUnit.HOURS), "I hear you. Same here", false),
    Message(48, 2.toContactId(), 0.toContactId(), Instant.now().minus(59, ChronoUnit.DAYS).minus(2, ChronoUnit.HOURS).plus(1, ChronoUnit.MINUTES), "😅", false),

    Message(49, 0.toContactId(), 2.toContactId(), Instant.now().minus(45, ChronoUnit.DAYS), "Did you see the game last night?", false),
    Message(50, 2.toContactId(), 0.toContactId(), Instant.now().minus(44, ChronoUnit.DAYS).minus(12, ChronoUnit.HOURS), "No! Who won?", false),
    Message(51, 0.toContactId(), 2.toContactId(), Instant.now().minus(44, ChronoUnit.DAYS).minus(10, ChronoUnit.HOURS), "Our team! Incredible finish", false),
    Message(52, 2.toContactId(), 0.toContactId(), Instant.now().minus(44, ChronoUnit.DAYS).minus(9, ChronoUnit.HOURS), "🔥", false),

    Message(53, 2.toContactId(), 0.toContactId(), Instant.now().minus(30, ChronoUnit.DAYS), "Want to catch the next game together?", false),
    Message(54, 0.toContactId(), 2.toContactId(), Instant.now().minus(29, ChronoUnit.DAYS).minus(5, ChronoUnit.HOURS), "Definitely! When is it?", false),
    Message(55, 2.toContactId(), 0.toContactId(), Instant.now().minus(29, ChronoUnit.DAYS).minus(4, ChronoUnit.HOURS), "Next Friday. My place?", false),
    Message(56, 0.toContactId(), 2.toContactId(), Instant.now().minus(29, ChronoUnit.DAYS).minus(2, ChronoUnit.HOURS), "Sounds good!", false),

    Message(57, 2.toContactId(), 0.toContactId(), Instant.now().minus(7, ChronoUnit.DAYS), "Still on for the game?", false),
    Message(58, 0.toContactId(), 2.toContactId(), Instant.now().minus(6, ChronoUnit.DAYS).minus(18, ChronoUnit.HOURS), "Sorry man, have to work late that day", false),
    Message(59, 0.toContactId(), 2.toContactId(), Instant.now().minus(6, ChronoUnit.DAYS).minus(18, ChronoUnit.HOURS).plus(1, ChronoUnit.MINUTES), "Rain check?", false),
    Message(60, 2.toContactId(), 0.toContactId(), Instant.now().minus(6, ChronoUnit.DAYS).minus(15, ChronoUnit.HOURS), "No worries! Next time", false),
    Message(61, 2.toContactId(), 0.toContactId(), Instant.now().minus(6, ChronoUnit.DAYS).minus(15, ChronoUnit.HOURS).plus(1, ChronoUnit.MINUTES), "👍", false),

    // Conversation with Sarah (ContactId 3) - VERY DETAILED - 45 messages, lots of back and forth
    // Started planning something weeks ago
    Message(62, 3.toContactId(), 0.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS), "Hey! I have an idea", false),
    Message(63, 0.toContactId(), 3.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS).plus(30, ChronoUnit.MINUTES), "What's up?", false),
    Message(64, 3.toContactId(), 0.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS).plus(35, ChronoUnit.MINUTES), "What if we organize a surprise party for Alex's birthday?", false),
    Message(65, 0.toContactId(), 3.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS).plus(40, ChronoUnit.MINUTES), "Oh that's a great idea!", false),
    Message(66, 0.toContactId(), 3.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS).plus(41, ChronoUnit.MINUTES), "When is it?", false),
    Message(67, 3.toContactId(), 0.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS).plus(45, ChronoUnit.MINUTES), "In 3 weeks", false),
    Message(68, 3.toContactId(), 0.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS).plus(46, ChronoUnit.MINUTES), "Plenty of time to plan", false),
    Message(69, 0.toContactId(), 3.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS).plus(50, ChronoUnit.MINUTES), "Perfect! What do we need to do?", false),
    Message(70, 3.toContactId(), 0.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS).plus(55, ChronoUnit.MINUTES), "Let me make a list", false),
    Message(71, 3.toContactId(), 0.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS), "Venue, decorations, cake, invitations", false),
    Message(72, 3.toContactId(), 0.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(1, ChronoUnit.MINUTES), "Food and drinks too", false),
    Message(73, 0.toContactId(), 3.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(10, ChronoUnit.MINUTES), "That's a lot!", false),
    Message(74, 0.toContactId(), 3.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(11, ChronoUnit.MINUTES), "But we can do it", false),
    Message(75, 0.toContactId(), 3.toContactId(), Instant.now().minus(28, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(12, ChronoUnit.MINUTES), "💪", false),

    // Planning continues
    Message(76, 3.toContactId(), 0.toContactId(), Instant.now().minus(25, ChronoUnit.DAYS), "I found a great venue!", false),
    Message(77, 3.toContactId(), 0.toContactId(), Instant.now().minus(25, ChronoUnit.DAYS).plus(2, ChronoUnit.MINUTES), "It's available on the day we need", false),
    Message(78, 0.toContactId(), 3.toContactId(), Instant.now().minus(25, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS), "Amazing! How much?", false),
    Message(79, 3.toContactId(), 0.toContactId(), Instant.now().minus(25, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(15, ChronoUnit.MINUTES), "Pretty reasonable, I'll send you the details", false),
    Message(80, 0.toContactId(), 3.toContactId(), Instant.now().minus(25, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS), "Looks perfect! Let's book it", false),
    Message(81, 3.toContactId(), 0.toContactId(), Instant.now().minus(25, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS).plus(5, ChronoUnit.MINUTES), "Done!", false),
    Message(82, 3.toContactId(), 0.toContactId(), Instant.now().minus(25, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS).plus(6, ChronoUnit.MINUTES), "✅", false),

    Message(83, 0.toContactId(), 3.toContactId(), Instant.now().minus(20, ChronoUnit.DAYS), "Should we make a guest list?", false),
    Message(84, 3.toContactId(), 0.toContactId(), Instant.now().minus(20, ChronoUnit.DAYS).plus(30, ChronoUnit.MINUTES), "Good idea", false),
    Message(85, 3.toContactId(), 0.toContactId(), Instant.now().minus(20, ChronoUnit.DAYS).plus(31, ChronoUnit.MINUTES), "I'll start and share it with you", false),
    Message(86, 0.toContactId(), 3.toContactId(), Instant.now().minus(20, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS), "👍", false),

    Message(87, 3.toContactId(), 0.toContactId(), Instant.now().minus(18, ChronoUnit.DAYS), "Sent you the list!", false),
    Message(88, 0.toContactId(), 3.toContactId(), Instant.now().minus(18, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS), "Got it! Adding a few more people", false),
    Message(89, 3.toContactId(), 0.toContactId(), Instant.now().minus(18, ChronoUnit.DAYS).plus(2, ChronoUnit.HOURS).plus(30, ChronoUnit.MINUTES), "Perfect", false),

    // Decoration planning
    Message(90, 0.toContactId(), 3.toContactId(), Instant.now().minus(15, ChronoUnit.DAYS), "What theme are we going for?", false),
    Message(91, 3.toContactId(), 0.toContactId(), Instant.now().minus(15, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS), "What about blue and gold? Those are Alex's favorite colors", false),
    Message(92, 0.toContactId(), 3.toContactId(), Instant.now().minus(15, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(10, ChronoUnit.MINUTES), "Perfect!", false),
    Message(93, 0.toContactId(), 3.toContactId(), Instant.now().minus(15, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(11, ChronoUnit.MINUTES), "I'll handle decorations", false),
    Message(94, 3.toContactId(), 0.toContactId(), Instant.now().minus(15, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(15, ChronoUnit.MINUTES), "You're the best!", false),
    Message(95, 3.toContactId(), 0.toContactId(), Instant.now().minus(15, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(16, ChronoUnit.MINUTES), "❤️", false),

    // Cake discussion
    Message(96, 3.toContactId(), 0.toContactId(), Instant.now().minus(12, ChronoUnit.DAYS), "Found an amazing bakery for the cake", false),
    Message(97, 0.toContactId(), 3.toContactId(), Instant.now().minus(12, ChronoUnit.DAYS).plus(45, ChronoUnit.MINUTES), "Show me!", false),
    Message(98, 3.toContactId(), 0.toContactId(), Instant.now().minus(12, ChronoUnit.DAYS).plus(50, ChronoUnit.MINUTES), "Sending you their portfolio", false),
    Message(99, 0.toContactId(), 3.toContactId(), Instant.now().minus(12, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS), "Wow these look incredible!", false),
    Message(100, 0.toContactId(), 3.toContactId(), Instant.now().minus(12, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(1, ChronoUnit.MINUTES), "🎂", false),
    Message(101, 3.toContactId(), 0.toContactId(), Instant.now().minus(12, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(5, ChronoUnit.MINUTES), "I'll order one for Saturday", false),
    Message(102, 0.toContactId(), 3.toContactId(), Instant.now().minus(12, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(10, ChronoUnit.MINUTES), "Can't wait to see it!", false),

    // Final preparations
    Message(103, 0.toContactId(), 3.toContactId(), Instant.now().minus(8, ChronoUnit.DAYS), "Party is in one week!", false),
    Message(104, 3.toContactId(), 0.toContactId(), Instant.now().minus(8, ChronoUnit.DAYS).plus(30, ChronoUnit.MINUTES), "I know! Getting excited", false),
    Message(105, 3.toContactId(), 0.toContactId(), Instant.now().minus(8, ChronoUnit.DAYS).plus(31, ChronoUnit.MINUTES), "Everything is coming together nicely", false),
    Message(106, 0.toContactId(), 3.toContactId(), Instant.now().minus(8, ChronoUnit.DAYS).plus(45, ChronoUnit.MINUTES), "You've done such an amazing job organizing", false),
    Message(107, 3.toContactId(), 0.toContactId(), Instant.now().minus(8, ChronoUnit.DAYS).plus(50, ChronoUnit.MINUTES), "We're a great team!", false),
    Message(108, 3.toContactId(), 0.toContactId(), Instant.now().minus(8, ChronoUnit.DAYS).plus(51, ChronoUnit.MINUTES), "🤝", false),

    // Day before party
    Message(109, 3.toContactId(), 0.toContactId(), Instant.now().minus(2, ChronoUnit.DAYS), "Tomorrow's the big day!", false),
    Message(110, 0.toContactId(), 3.toContactId(), Instant.now().minus(2, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS), "So excited! What time should I be there?", false),
    Message(111, 3.toContactId(), 0.toContactId(), Instant.now().minus(2, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(15, ChronoUnit.MINUTES), "Come at 5pm to help set up", false),
    Message(112, 3.toContactId(), 0.toContactId(), Instant.now().minus(2, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(16, ChronoUnit.MINUTES), "Party starts at 7", false),
    Message(113, 0.toContactId(), 3.toContactId(), Instant.now().minus(2, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(20, ChronoUnit.MINUTES), "I'll be there!", false),

    // After the party
    Message(114, 0.toContactId(), 3.toContactId(), Instant.now().minus(1, ChronoUnit.DAYS), "That was such an amazing party!", false),
    Message(115, 0.toContactId(), 3.toContactId(), Instant.now().minus(1, ChronoUnit.DAYS).plus(2, ChronoUnit.MINUTES), "Alex was completely surprised", false),
    Message(116, 3.toContactId(), 0.toContactId(), Instant.now().minus(1, ChronoUnit.DAYS).plus(30, ChronoUnit.MINUTES), "I know! The look on their face was priceless", false),
    Message(117, 3.toContactId(), 0.toContactId(), Instant.now().minus(1, ChronoUnit.DAYS).plus(31, ChronoUnit.MINUTES), "😊", false),
    Message(118, 0.toContactId(), 3.toContactId(), Instant.now().minus(1, ChronoUnit.DAYS).plus(45, ChronoUnit.MINUTES), "We should totally do this again", false),
    Message(119, 3.toContactId(), 0.toContactId(), Instant.now().minus(1, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS), "Absolutely! Best party planning team", false),
    Message(120, 3.toContactId(), 0.toContactId(), Instant.now().minus(1, ChronoUnit.DAYS).plus(1, ChronoUnit.HOURS).plus(1, ChronoUnit.MINUTES), "🎉", false),

    // Conversation with Léa (ContactId 4) - SPARSE - Only 8 messages over 3 months
    Message(121, 4.toContactId(), 0.toContactId(), Instant.now().minus(90, ChronoUnit.DAYS), "Salut! Ça fait longtemps!", false),
    Message(122, 0.toContactId(), 4.toContactId(), Instant.now().minus(89, ChronoUnit.DAYS).minus(12, ChronoUnit.HOURS), "Léa! Comment vas-tu?", false),
    Message(123, 4.toContactId(), 0.toContactId(), Instant.now().minus(89, ChronoUnit.DAYS).minus(8, ChronoUnit.HOURS), "Très bien! I'm visiting next month", false),
    Message(124, 0.toContactId(), 4.toContactId(), Instant.now().minus(88, ChronoUnit.DAYS), "Really? That's great! We should meet up", false),
    Message(125, 4.toContactId(), 0.toContactId(), Instant.now().minus(87, ChronoUnit.DAYS).minus(6, ChronoUnit.HOURS), "Definitely! I'll let you know when I have the exact dates", false),
    Message(126, 0.toContactId(), 4.toContactId(), Instant.now().minus(87, ChronoUnit.DAYS).minus(4, ChronoUnit.HOURS), "Perfect!", false),

    Message(127, 4.toContactId(), 0.toContactId(), Instant.now().minus(35, ChronoUnit.DAYS), "I'll be there from the 15th to the 20th", false),
    Message(128, 0.toContactId(), 4.toContactId(), Instant.now().minus(34, ChronoUnit.DAYS).minus(18, ChronoUnit.HOURS), "Noted! Looking forward to it", false),
    Message(129, 4.toContactId(), 0.toContactId(), Instant.now().minus(34, ChronoUnit.DAYS).minus(16, ChronoUnit.HOURS), "Me too!", false),
    Message(130, 4.toContactId(), 0.toContactId(), Instant.now().minus(34, ChronoUnit.DAYS).minus(16, ChronoUnit.HOURS).plus(1, ChronoUnit.MINUTES), "😊", false),
)
