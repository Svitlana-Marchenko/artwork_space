package com.system.artworkspace;

import com.system.artworkspace.user.UserEntity;
import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserRepository;
import com.system.artworkspace.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCheckOnSave() {
        UserEntity user = new UserEntity("aaa", "ddd", "ddd", "qqq@ghjklhjk", "qqq");
        userRepository.save(user);

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUser(new User(12L,"aa","cc","dd","kk","kk",12L));

        //List<UserEntity> foundUser = userRepository.findAll();
        //assertThat(foundUser).isNotEmpty();
        //System.out.println(foundUser.get(0).getEmail());


    }
}
