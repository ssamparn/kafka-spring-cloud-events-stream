package com.explore.springcloudeventsstreamplayground.sec10.dto;

public record Order(int id,
                    int customerId,
                    int amount,
                    ProductType productType) {
}
