package com.explore.springcloudeventsstreamplayground.sec08.dto;

public record Order(int id,
                    int customerId,
                    int amount,
                    ProductType productType) {
}
