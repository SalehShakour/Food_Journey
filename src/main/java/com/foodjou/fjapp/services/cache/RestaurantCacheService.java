package com.foodjou.fjapp.services.cache;

import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import com.foodjou.fjapp.mapper.entityMapper.MapStructRestaurant;
import com.foodjou.fjapp.repositories.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantCacheService {

    public RSet<RestaurantDTO> getCache(){
        return RestaurantCacheInitializer.restaurantSet;

    }
}

