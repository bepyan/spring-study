package com.kit.dorm.IocContainerTest;

public class DeliveryService {

    // 공유되는 필드 static 해도 마찬가지
    private String address;

    public void deliveryStart(String address){
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
