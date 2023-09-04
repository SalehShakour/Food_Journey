package com.foodjou.fjapp.services.cache;

import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import com.foodjou.fjapp.repositories.RestaurantRepository;
import com.foodjou.fjapp.services.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Component;


import jakarta.annotation.PostConstruct;


import java.util.List;


@Component
@AllArgsConstructor
@Getter
@ShellComponent
public class RestaurantCacheInitializer {
    private final RedissonClient redissonClient;
    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;
    public static RSet<RestaurantDTO> restaurantSet;


    @PostConstruct
    public void init() {
        restaurantSet = redissonClient.getSet("restaurantsCache", new TypedJsonJacksonCodec(RestaurantDTO.class));
    }

    @Scheduled(cron = "0 4 13 * * ?")
    @ShellMethod("Manually update the restaurant cache")
    public void scheduledCacheUpdate() {
        cacheAllRestaurants();
    }

    public void cacheAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantDTO> restaurantDTOList = restaurants.stream()
                .map(restaurant ->
                    RestaurantDTO.builder()
                            .restaurantName(restaurant.getRestaurantName())
                            .address(restaurant.getAddress())
                            .phoneNumber(restaurant.getPhoneNumber())
                            .build()
                )
                .toList();

        restaurantSet.clear();
        restaurantSet.addAll(restaurantDTOList);
    }
}




