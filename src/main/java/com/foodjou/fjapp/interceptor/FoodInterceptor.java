package com.foodjou.fjapp.interceptor;


import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.services.FoodService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
public class FoodInterceptor implements HandlerInterceptor {
    private final FoodService foodService;

    @Override
    public boolean preHandle(HttpServletRequest request, @Nullable HttpServletResponse response,@Nullable Object handler) throws Exception {

        String foodId = request.getParameter("id");
        Food food = foodService.getFoodById(foodId);
        request.setAttribute("food", food);

        return true;
    }
}

