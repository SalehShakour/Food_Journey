package com.foodjou.fjapp;

import com.foodjou.fjapp.domain.Order;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.domain.Role;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.mapper.entityMapper.MapStructUser;
import com.foodjou.fjapp.repositories.FoodRepository;
import com.foodjou.fjapp.repositories.UserRepository;
import com.foodjou.fjapp.services.FoodService;
import com.foodjou.fjapp.services.UserService;
import org.hibernate.jdbc.Expectation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserRepoTest {
    @Mock
    private UserRepository userRepo;
    @Mock
    private MapStructUser mapStructUser;

    private UserService userService;
    private User expected;
    private User notExpected;
    private Restaurant exRestaurant;
    private Restaurant notExRestaurant;


    @BeforeEach
    void setUp() {
        userService = new UserService(userRepo,mapStructUser);
        expected = new User(1L,"test@email", new HashSet<>(),"saleh",
                "shakour","0000","0","home",new ArrayList<>(),new ArrayList<>());
        notExpected = new User(2L,"test2@email", new HashSet<>(),"saleh",
                "shakour","0000","0","home",new ArrayList<>(),new ArrayList<>());

    }

    @Test
    void findByEmail() {
        when(userRepo.findByEmail(expected.getEmail()))
                .thenReturn(Optional.ofNullable(expected));

        assertEquals(userRepo.findByEmail(expected.getEmail()),Optional.ofNullable(expected));
        assertNotEquals(userRepo.findByEmail(expected.getEmail()),Optional.ofNullable(notExpected));
    }
    @Test
    void delete() {
        userRepo.delete(expected);
        verify(userRepo).delete(expected);
    }
    @Test
    void restaurantByIdTest() {
        exRestaurant = new Restaurant();
        notExRestaurant = new Restaurant();
        exRestaurant.setId(1L);
        exRestaurant.setOwner(expected);
        notExRestaurant.setId(2L);
        notExRestaurant.setOwner(expected);
        List<Restaurant> temp = expected.getOwnedRestaurants();
        temp.add(exRestaurant);
        expected.setOwnedRestaurants(temp);
        temp = notExpected.getOwnedRestaurants();
        temp.add(notExRestaurant);
        notExpected.setOwnedRestaurants(temp);

        assertEquals(userService.getRestaurantById(expected,1L),exRestaurant);
        assertThrows(CustomException.class, () -> userService.getRestaurantById(expected,2L));
        assertEquals(userService.getRestaurantById(notExpected,2L),notExRestaurant);
    }

}
