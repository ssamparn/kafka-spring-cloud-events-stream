package com.explore.springcloudeventsstreamplayground.sec11.dto;

public record Order(int id,
                    int customerId,
                    int amount,
                    ProductType productType) {
}
