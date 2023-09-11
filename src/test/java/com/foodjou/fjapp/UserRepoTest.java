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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepoTest {
    @Mock
    private UserRepository userRepo;
    @Mock
    private User expected;
    @Mock
    private User notExpected;

    @Test
    void findByEmail() {
        when(userRepo.findByEmail(expected.getEmail()))
                .thenReturn(Optional.of(expected));
        assertEquals(userRepo.findByEmail(expected.getEmail()),Optional.of(expected));
        assertNotEquals(userRepo.findByEmail(expected.getEmail()),Optional.of(notExpected));
    }
    @Test
    void delete() {
        userRepo.delete(expected);
        verify(userRepo).delete(expected);
    }
}
