package com.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDto {
    @NotNull
    private Long sellerId;
    @NotNull
    private Long productId;
    @NotNull
    @Min(1)
    private int quantity;
}
