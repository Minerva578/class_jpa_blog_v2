package com.example.blog_v1.user;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

// Autowired & final
@RequiredArgsConstructor
@Repository //IoC
public class UserRepository {

    private final EntityManager em;

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
}
