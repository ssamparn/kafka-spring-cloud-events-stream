package com.explore.springcloudeventsstreamplayground.sec12.dto;

public record Order(int id,
                    int customerId,
                    int amount,
                    ProductType productType) {
}
