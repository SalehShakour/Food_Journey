package com.foodjou.fjapp.shell;


import com.foodjou.fjapp.services.cache.RestaurantCacheInitializer;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class MyShellCommands {
    private final RestaurantCacheInitializer restaurantCacheInitializer;

    @ShellMethod("Manually update the restaurant cache")
    public String updateRestaurantCache() {
        restaurantCacheInitializer.cacheAllRestaurants(); // Invoke the cache update method
        return "Restaurant cache has been updated.";
    }
}
