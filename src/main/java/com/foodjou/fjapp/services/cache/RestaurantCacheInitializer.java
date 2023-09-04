package com.foodjou.fjapp.services.cache;

import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.repositories.RestaurantRepository;
import com.foodjou.fjapp.services.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Component;


import jakarta.annotation.PostConstruct;


import java.util.List;


@Component
@AllArgsConstructor
@Getter
public class RestaurantCacheInitializer {
    private final RedissonClient redissonClient;
    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;
    public static RSet<CacheData> restaurantSet;


    @Getter
    public static class CacheData {

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        private String restaurantName;
        private String address;
        private String phoneNumber;
    }

    private CacheData convertRestaurantToCacheData(Restaurant restaurant) {
        CacheData cacheData = new CacheData();
        cacheData.setRestaurantName(restaurant.getRestaurantName());
        cacheData.setPhoneNumber(restaurant.getPhoneNumber());
        cacheData.setAddress(restaurant.getAddress());
        return cacheData;
    }

    @PostConstruct
    public void cacheAllRestaurants() {

        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<CacheData> cacheDataList = restaurants.stream()
                        .map(this::convertRestaurantToCacheData)
                        .toList();


        restaurantSet = redissonClient.getSet("restaurantsCache", new TypedJsonJacksonCodec(com.foodjou.fjapp.services.cache.RestaurantCacheInitializer.CacheData.class));
        restaurantSet.addAll(cacheDataList);
    }
}




