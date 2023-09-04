package com.foodjou.fjapp.services.cache;

import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantCacheService {

    public List<RestaurantDTO> getCache(String filterRestaurantName, String filterAddress){
        return RestaurantCacheInitializer.restaurantSet.stream()
                .filter(cacheData -> {
                    if (filterRestaurantName != null && !filterRestaurantName.isEmpty()) {
                        return cacheData.getRestaurantName().contains(filterRestaurantName);
                    }
                    if (filterAddress != null && !filterAddress.isEmpty()) {
                        return cacheData.getAddress().contains(filterAddress);
                    }
                    return true;
                })
                .collect(Collectors.toList());

    }
}

