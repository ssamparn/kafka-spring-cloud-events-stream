package com.explore.springcloudeventsstreamplayground.sec16.dto;

public record Order(int id,
                    int customerId,
                    int amount,
                    ProductType productType) {
}
