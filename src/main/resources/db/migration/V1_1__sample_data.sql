insert into TB_TINDER_USER(chat_id, name, header, description, preference, registered, sex, last_viewed_id,
                           last_found_id)
VALUES (1, 'Иванов Иван Иванович', 'Ищу свою вторую половинку', 'Люблю активный отдых и путешествия', 'м', '2022-01-01',
        'м', 1, 1),
       (2, 'Петрова Ольга Сергеевна', 'Ищу надежного партнера', 'Я - уверенная в себе женщина с чувством юмора', 'ж',
        '2022-02-14', 'ж', 1, 1),
       (3, 'Сидоров Андрей Владимирович', 'Хочу найти девушку для серьезных отношений',
        'Я - заботливый и ответственный парень, люблю заниматься спортом', 'м', '2022-03-30', 'м', 1, 1),
       (4, 'Михайлова Елена Игоревна', 'Ищу мужчину для романтических свиданий',
        'Я - романтическая натура, люблю читать книги и слушать музыку', 'ж', '2022-04-05', 'ж', 1, 1),
       (5, 'Кузнецов Денис Алексеевич', 'Ищу девушку для отношений без обязательств',
        'Я - люблю проводить время на природе и заниматься экстримальными видами спорта', 'м', '2022-05-12', 'м', 1, 1),
       (6, 'Новикова Марина Владимировна', 'Ищу мужчину для создания семьи',
        'Я - домашняя и заботливая женщина, люблю готовить и ухаживать за близкими', 'ж', '2022-06-20', 'ж', 1, 1),
       (7, 'Соколов Алексей Иванович', 'Хочу найти девушку для общения и дружбы',
        'Я - общительный и душевный парень, люблю слушать музыку и ходить в кино', 'м', '2022-07-15', 'м', 1, 1),
       (8, 'Захарова Алина Сергеевна', 'Ищу мужчину для романтических прогулок по городу',
        'Я - люблю гулять по городу, посещать музеи и выставки', 'ж', '2022-08-03', 'ж', 1, 1),
       (9, 'Козлова Екатерина Александровна', 'Ищу мужчину для серьезных отношений',
        'Я - умная и целеустремленная женщина, люблю заниматься спортом и читать книги', 'ж', '2022-10-10', 'ж', 1, 1),
       (10, 'Поляков Александр Игоревич', 'Хочу найти девушку для общения и дружбы',
        'Я - креативный и общительный парень, люблю заниматься фотографией и путешествовать', 'м', '2022-11-19', 'м', 1,
        1);


insert into TB_LIKE(USER_ID, LIKE_ID)
values (1, 2),
       (1, 3),
       (1, 4),
       (4, 1),
       (5, 1),
       (5, 2)