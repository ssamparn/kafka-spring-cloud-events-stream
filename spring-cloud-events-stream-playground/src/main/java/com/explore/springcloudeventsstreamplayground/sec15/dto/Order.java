package com.explore.springcloudeventsstreamplayground.sec15.dto;

public record Order(int id,
                    int customerId,
                    int amount,
                    ProductType productType) {
}
