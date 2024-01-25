package com.springbooot.tutorials.springmongodbdemo.repository;

import com.springbooot.tutorials.springmongodbdemo.exception.NotFoundException;
import com.springbooot.tutorials.springmongodbdemo.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;


@DataMongoTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void if_injected_objects_are_not_null(){
        Assertions.assertNotNull(userRepository);
    }

    @Test
    void save_user_should_return_saved_user(){
        //given - user data
        User user = new User("oms17", "pass@123", "8668989457",  "oms@email.com", "USER");
        //when - user saved to DB
        User savedUser = userRepository.save(user);
        //then - it should return the saved user same as given user
        Assertions.assertEquals(user, savedUser);
    }

    @Test
    void find_all_users_returns_all_the_users_saved(){
        //given - few users saved to db
        User user1 = new User("oms17", "pass@123", "8668989457",  "oms@email.com", "USER");
        User user2 = new User("oms178", "pass@123", "8668988457",  "oms1@email.com", "USER");
        userRepository.save(user1);
        userRepository.save(user2);
        //when - fetched list of all users from repo
        List<User> userList = userRepository.findAll();

        //then - should return same users as saved previously
        Assertions.assertNotNull(userList);
        Assertions.assertEquals(2, userList.size());
    }

    @Test
    void update_details_should_update_user_data(){

        //given - existing user with some value of mobile number
        User user1 = new User("oms17", "pass@123", "8668989457",  "oms@email.com", "USER");
        userRepository.save(user1);
        String updateMobileNumber = "8898989898";

        //when - user with same id saved again with updated mobile Number
        user1.setMobileNo(updateMobileNumber);
        userRepository.save(user1);

        //then - should reflect updated Mobile Number when fetched
        User updatedUser = userRepository.findByUsername(user1.getUsername()).get();
        Assertions.assertEquals(updateMobileNumber, updatedUser.getMobileNo());

    }
    @Test
    void delete_user_should_return_not_found_expetion_on_fetch(){

        //given - existing user with some id  (e.g. oms17)
        User user1 = new User("oms17", "pass@123", "8668989457",  "oms@email.com", "USER");
        userRepository.save(user1);

        //when - user is deleted
        userRepository.deleteById(user1.getUsername());

        //then - should throw NotFoundException when fetched.
        Assertions.assertThrows(NotFoundException.class, () -> {
            Optional<User> optionalUser = userRepository.findUserByUsername(user1.getUsername());
            if(optionalUser.isEmpty()){
                throw new NotFoundException("User Not Found");
            }
        });
    }
}