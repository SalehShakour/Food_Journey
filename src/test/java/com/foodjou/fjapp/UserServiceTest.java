package com.foodjou.fjapp;

import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.mapper.entityMapper.MapStructUser;
import com.foodjou.fjapp.repositories.UserRepository;
import com.foodjou.fjapp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {
    @Mock
    private UserRepository userRepo;
    @Mock
    private MapStructUser mapStructUser;

    private UserService userService;
    private User expected;
    private User notExpected;


    @BeforeEach
    void setUp() {
        userService = new UserService(userRepo, mapStructUser);
        expected = new User(1L, "test@email", new HashSet<>(), "saleh",
                "shakour", "0000", "0", "home", new ArrayList<>(), new ArrayList<>());
        notExpected = new User(2L, "test2@email", new HashSet<>(), "saleh",
                "shakour", "0000", "0", "home", new ArrayList<>(), new ArrayList<>());

    }


    @Test
    void restaurantByIdTest() {
        Restaurant exRestaurant = new Restaurant();
        Restaurant notExRestaurant = new Restaurant();
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

        assertEquals(userService.getRestaurantById(expected, 1L), exRestaurant);
        assertThrows(CustomException.class, () -> userService.getRestaurantById(expected, 2L));
        assertEquals(userService.getRestaurantById(notExpected, 2L), notExRestaurant);
    }


}
