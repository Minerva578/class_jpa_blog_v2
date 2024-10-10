package com.example.blog_v1.user;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// Autowired & final
@RequiredArgsConstructor
@Repository //IoC
public class UserRepository {

    private final EntityManager em;
    
    /*
    * 유저 아이디를 찾아 반환하는 메서드
    * @param id
    * @return 유저 아이디
    * */
    @Transactional
    public User findById(int id) {
        String jpql = "select u from User u where u.id = :id";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    /*
     * 사용자 저장 메서드 (JPA API 사용)
     * @param user
     * @return 저장된 사용자 엔티티
     */
    @Transactional
    public User save(User user) {
        // JPQL 은 INSERT 구문을 직접 지원하지 않습니다.
        em.persist(user); // 영속화
        return  user;
    }

    /*
    * 사용자 이름과 비밀번호
    * @param username
    * @param password
    * @return 조회된 User 엔티티, null
    * */
    public User findByUsernameAndPassword(String username, String password) {
        // 제네릭을 사용하면 다운캐스팅 필요x
        TypedQuery<User> jpql = em.createQuery("select u from User u where u.username = :username and u.password = :password", User.class
        );
        jpql.setParameter("username", username);
        jpql.setParameter("password", password);
        return jpql.getSingleResult();
    }

    // 사용자 정보 수정 기능 만들기 (더티 체킹 사용)
    @Transactional // 더티 체킹 반영
    public User updateById(int id, String password, String email) {
        // id 값으로 영속성 컨텍스테 정보가 있는가? 확인
        User userEntity = findById(id);
        userEntity.setPassword(password);
        userEntity.setEmail(email);
        return userEntity; // 수정
    }
}
