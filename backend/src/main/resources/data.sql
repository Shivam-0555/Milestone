-- Achievements
INSERT IGNORE INTO achievements (id, achievement_key, title, description, icon, trigger_type, trigger_value) VALUES 
(1, 'first_quest', 'First Quest', 'Complete your first quest', '⚔️', 'QUEST_COUNT', 1),
(2, 'streak_7', '7 Day Warrior', 'Maintain a 7 day streak', '🔥', 'STREAK_DAYS', 7),
(3, 'xp_1000', 'XP Hunter', 'Earn 1,000 XP', '⚡', 'XP_EARNED', 1000),
(4, 'level_10', 'Level 10', 'Reach level 10', '🏆', 'LEVEL_REACHED', 10),
(5, 'streak_30', 'Consistency King', '30 day streak', '👑', 'STREAK_DAYS', 30),
(6, 'quest_50', 'The Explorer', 'Complete 50 quests', '🧭', 'QUEST_COUNT', 50),
(7, 'level_25', 'Life Champion', 'Reach level 25', '🌟', 'LEVEL_REACHED', 25);

-- Shop Items
INSERT IGNORE INTO shop_items (id, name, description, cost, category, icon) VALUES
(1, 'Golden Frame', 'Premium profile frame', 500, 'Frames', '???'),
(2, 'Shadow Avatar', 'Dark mysterious avatar', 800, 'Avatars', '??'),
(3, 'Royal Title', 'King/Queen title badge', 1000, 'Titles', '??'),
(4, 'Dark Theme', 'Sleek dark interface', 700, 'Themes', '??'),
(5, 'XP Boost (3 Hour)', 'Double XP for 3 hours', 300, 'Boosts', '?'),
(6, 'Streak Shield', 'Protect streak for 1 day', 100, 'Boosts', '???');
