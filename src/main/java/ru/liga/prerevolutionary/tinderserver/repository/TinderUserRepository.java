package ru.liga.prerevolutionary.tinderserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prerevolutionary.tinderserver.exception.NotFoundException;
import ru.liga.prerevolutionary.tinderserver.model.TinderUser;

import java.util.Optional;

@Transactional(readOnly = true)
public interface TinderUserRepository extends JpaRepository<TinderUser, Integer> {
    Optional<TinderUser> findUserByChatId(String chatId);

    //todo в репозитории должны быть только методы работы с бд, надо перенести этот метод выше, в сервис
    default TinderUser getUserByChatId(String chatId) {
        return findUserByChatId(chatId)
                .orElseThrow(() -> new NotFoundException("User with chatId %s not found".formatted(chatId)));
    }

    //todo выглядит очень сложно и непонятно, думаю лучше сделать селект попроще, а потом уже найти нужную запись используя джаву

    /**todo чтобы сделать здесь более читаемо, можно вынести селект ниже передавать его результат сюда как параметр
     * select * from (select * from ( " +
     *             "                              (select USER_ID as id from TB_LIKE where LIKE_ID = :#{#user.chatId}) " +
     *             "                               union all " +
     *             "                              (select LIKE_ID as id from TB_LIKE where USER_ID = :#{#user.chatId})) " +
     *             "               group by id)
    */
    //todo какие то логические методы должны находиться в сервисном слое, а в репозитории просто обращения к бд
    // предлагал я это переписать примерно так
    //    Optional<TinderUser> findTopByIdGreaterThanAndIdNotAndIdIn(Integer lastViewedUser, Integer userId, List<Integer> relatedIds); + метод для вложенного селекта(можно даже просто вырезать эту часть)
    // а уже в сервисе будет метод findNextRelatedUser(), в котором сначала метод с вызовом вложенного селекта(названный по бизнесу), потом этот, и return
    // разраб, который будет смотреть такой код, быстрее поймет реализацию и, допустим, внесет в него правку
    // А когда видишь такой большой селект теряешься: видишь селект который через union собран с другим селектом, из которого берется селект, из которого тоже берется селект, и уже после этого используется в условии
    // понятно, что может это никто и не будет никогда трогать, но лучше сразу писать понятно

    /**
     * Метод возвращает следующего пользователя имеющего связь с пользователем переданным как параметр.
     * Сущность user хранит в себе id последнего просмотренного пользователя.
     * Два пользователя являются связанными если первый поставил лайк второму или второй поставил лайк первому или оба поставили лайк друг другу
     * Таблица TB_LIKE содержит два столбца с id пользователей первый кто поставил лайк USER_ID второй кому LIKE_ID
     * Самым глубоким selectом выбираем все id связанные с нашим пользователем из входного параметра.
     * Для этого берем всех user_id которые лайкнули пользователя из входного параметра и объединяем их
     * с всеми id которых лайкнули мы LIKE_ID.
     * Мы не можем воспользоваться distinct даже с учетом, что казалось бы у нас один столбец as id (возможно это особенность H2)
     * И поэтому для отброса дублей используем qroup by id после этого у нас получается селект с одним столбцом без дублей
     * Дальше через IN получаем по ID пользователей и через <> исключаем из списка себя
     * и берем только те ID которые больше чем последний просмотренный
     * сортируем всех полученных пользователей по ID и берем первый - то и есть следующий пользователь
     *
     * @param user пользователь для которого ище следующего связанного пользователя
     * @return следующий связанный пользователь
     */
    @Query(value = "SELECT top 1 * FROM TB_TINDER_USER WHERE ID > :#{#user.lastViewedUser.id} AND ID <> :#{#user.id} " +
            "AND ID IN (select * from (select * from ( " +
            "                              (select USER_ID as id from TB_LIKE where LIKE_ID = :#{#user.chatId}) " +
            "                               union all " +
            "                              (select LIKE_ID as id from TB_LIKE where USER_ID = :#{#user.chatId})) " +
            "               group by id))" +
            "ORDER BY ID", nativeQuery = true)
    Optional<TinderUser> findNextRelatedUser(TinderUser user);

    @Query(value = "SELECT top 1 * FROM TB_TINDER_USER WHERE ID < :#{#user.lastViewedUser.id} AND ID <> :#{#user.id} " +
            "AND ID IN (select * from (select * from ( " +
            "                              (select USER_ID as id from TB_LIKE where LIKE_ID = :#{#user.id}) " +
            "                               union all " +
            "                              (select LIKE_ID as id from TB_LIKE where USER_ID = :#{#user.id})) " +
            "               group by id))" +
            "ORDER BY ID DESC", nativeQuery = true)
    Optional<TinderUser> findPreviousRelatedUser(TinderUser user);


    @Query(value = "SELECT top 1 * FROM TB_TINDER_USER " +
            "WHERE ID <> :#{#user.id} AND (PREFERENCE = :#{#user.sex} OR PREFERENCE = 'Все') AND (SEX = :#{#user.preference} OR 'Все'= :#{#user.preference}) AND ID > :#{#user.lastFoundUser.id} " +
            "ORDER BY ID", nativeQuery = true)
    Optional<TinderUser> findNextSearchedUser(TinderUser user);

    @Query(value = "SELECT top 1 * FROM TB_TINDER_USER " +
            "WHERE ID <> :#{#user.id} AND (PREFERENCE = :#{#user.sex} OR PREFERENCE = 'Все') AND (SEX = :#{#user.preference} OR 'Все'= :#{#user.preference}) AND ID < :#{#user.lastFoundUser.id} " +
            "ORDER BY ID DESC", nativeQuery = true)
    Optional<TinderUser> findPreviousSearchedUser(TinderUser user);
}
