package com.EXbank.loan_eligibility_engine.api.exception;

public class ProductCodeNotFoundException extends RuntimeException {

    public ProductCodeNotFoundException (String productCode) {
        super("Product code : " + productCode + " not found");
    }


    }