package com.explore.springcloudeventsstreamplayground.sec07.dto;

import java.util.UUID;

public record Payment(int orderId,
                      int amount,
                      UUID paymentId) {
}
