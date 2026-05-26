package com.explore.springcloudeventsstreamplayground.sec08.dto;

import java.util.UUID;

public record Payment(int orderId,
                      int amount,
                      UUID paymentId) {
}
