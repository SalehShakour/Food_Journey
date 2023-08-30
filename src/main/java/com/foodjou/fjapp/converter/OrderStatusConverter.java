package com.foodjou.fjapp.converter;

import com.foodjou.fjapp.myEnum.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus status) {
        return status.name();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String statusValue) {
        return OrderStatus.valueOf(statusValue);
    }
}

