INSERT INTO user (id, username, password, role, phone_number, balance, account_status)
VALUES (UUID(), 'admin1', 'password123', 'ADMIN', '+353830000001', 1000.0, TRUE),
       (UUID(), 'admin2', 'password123', 'ADMIN', '+353830000002', 1200.0, TRUE),
       (UUID(), 'manager1', 'password123', 'MANAGER', '+353830000003', 800.0, TRUE),
       (UUID(), 'manager2', 'password123', 'MANAGER', '+353830000004', 750.0, TRUE),
       (UUID(), 'user1', 'password123', 'USER', '+353830000005', 500.0, TRUE),
       (UUID(), 'user2', 'password123', 'USER', '+353830000006', 400.0, TRUE),
       (UUID(), 'user3', 'password123', 'USER', '+353830000007', 450.0, TRUE),
       (UUID(), 'user4', 'password123', 'USER', '+353830000008', 300.0, TRUE),
       (UUID(), 'user5', 'password123', 'USER', '+353830000009', 350.0, TRUE),
       (UUID(), 'user6', 'password123', 'USER', '+353830000010', 200.0, TRUE);

INSERT INTO voucher (id, title, description, price, tour_type, transfer_type, hotel_type, status, arrival_date,
                     eviction_date, user_id, is_hot)
VALUES (UUID(), 'Health Tour', 'Relax and rejuvenate', 300.0, 'HEALTH', 'BUS', 'THREE_STARS', 'REGISTERED',
        '2024-12-01', '2024-12-10', NULL, FALSE),
       (UUID(), 'Safari Adventure', 'Explore the wild', 500.0, 'SAFARI', 'JEEPS', 'FOUR_STARS', 'PAID', '2024-12-15',
        '2024-12-20', NULL, TRUE),
       (UUID(), 'Cultural Experience', 'Discover rich heritage', 450.0, 'CULTURAL', 'PLANE', 'THREE_STARS', 'CANCELED',
        '2024-11-25', '2024-11-30', NULL, FALSE),
       (UUID(), 'Sports Tour', 'Action-packed activities', 400.0, 'SPORTS', 'TRAIN', 'TWO_STARS', 'REGISTERED',
        '2024-12-05', '2024-12-12', NULL, TRUE),
       (UUID(), 'Wine Tasting', 'Exclusive wine experience', 350.0, 'WINE', 'BUS', 'FIVE_STARS', 'PAID', '2024-12-01',
        '2024-12-03', NULL, FALSE),
       (UUID(), 'Eco Tour', 'Sustainable travel', 250.0, 'ECO', 'ELECTRICAL_CARS', 'THREE_STARS', 'REGISTERED',
        '2024-12-08', '2024-12-14', NULL, FALSE),
       (UUID(), 'Adventure Expedition', 'Thrilling adventures', 700.0, 'ADVENTURE', 'MINIBUS', 'FOUR_STARS',
        'REGISTERED', '2024-11-28', '2024-12-05', NULL, TRUE),
       (UUID(), 'Leisure Getaway', 'Relax by the beach', 300.0, 'LEISURE', 'PRIVATE_CAR', 'TWO_STARS', 'PAID',
        '2024-12-04', '2024-12-09', NULL, FALSE),
       (UUID(), 'Health and Wellness', 'Holistic healing', 320.0, 'HEALTH', 'SHIP', 'THREE_STARS', 'REGISTERED',
        '2024-11-30', '2024-12-06', NULL, FALSE),
       (UUID(), 'Safari Expedition', 'Wildlife at its best', 600.0, 'SAFARI', 'JEEPS', 'FIVE_STARS', 'PAID',
        '2024-12-07', '2024-12-14', NULL, TRUE),
       (UUID(), 'Cultural Insight', 'Dive into traditions', 400.0, 'CULTURAL', 'BUS', 'FOUR_STARS', 'CANCELED',
        '2024-11-22', '2024-11-27', NULL, FALSE),
       (UUID(), 'Sports Retreat', 'Engaging activities', 280.0, 'SPORTS', 'TRAIN', 'TWO_STARS', 'REGISTERED',
        '2024-12-09', '2024-12-15', NULL, FALSE),
       (UUID(), 'Wine and Dine', 'Fine dining experiences', 500.0, 'WINE', 'PLANE', 'FIVE_STARS', 'REGISTERED',
        '2024-12-01', '2024-12-04', NULL, TRUE),
       (UUID(), 'Eco Adventure', 'Explore nature', 450.0, 'ECO', 'MINIBUS', 'FOUR_STARS', 'PAID', '2024-11-29',
        '2024-12-02', NULL, FALSE),
       (UUID(), 'Thrilling Adventure', 'Adrenaline rush', 800.0, 'ADVENTURE', 'JEEPS', 'FIVE_STARS', 'PAID',
        '2024-12-02', '2024-12-09', NULL, TRUE),
       (UUID(), 'Relaxing Leisure', 'Serenity by the lake', 280.0, 'LEISURE', 'PRIVATE_CAR', 'TWO_STARS', 'REGISTERED',
        '2024-12-05', '2024-12-10', NULL, FALSE),
       (UUID(), 'Health Retreat', 'Recharge your body', 320.0, 'HEALTH', 'SHIP', 'THREE_STARS', 'REGISTERED',
        '2024-12-10', '2024-12-17', NULL, TRUE),
       (UUID(), 'Safari Escape', 'Wild encounters', 500.0, 'SAFARI', 'MINIBUS', 'FOUR_STARS', 'CANCELED', '2024-12-06',
        '2024-12-13', NULL, FALSE),
       (UUID(), 'Cultural Fusion', 'Explore new cultures', 360.0, 'CULTURAL', 'PLANE', 'THREE_STARS', 'PAID',
        '2024-11-30', '2024-12-07', NULL, TRUE),
       (UUID(), 'Sports Festival', 'Competitions and fun', 420.0, 'SPORTS', 'BUS', 'FOUR_STARS', 'REGISTERED',
        '2024-12-03', '2024-12-08', NULL, FALSE);
