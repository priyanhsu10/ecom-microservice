package com.pro.order_service.constants;

public class KafkaConstants {

    // Kafka topic names for order and payment status events
    //order status event ,
    public static final String ORDER_STATUS_TOPIC = "order-status-topic";
    public static final String ORDER_GROUP_ID = "order-status-group";
    public static final String PAYMENT_STATUS_TOPIC = "payment-status-topic";
    public  static  final String ORDER_PAYMENT_STATUS_TOPIC = "order-payment-status-topic";
    public static final String PAYMENT_GROUP_ID = "payment-status-group";

    private KafkaConstants() {
        // Private constructor to prevent instantiation
    }
}
