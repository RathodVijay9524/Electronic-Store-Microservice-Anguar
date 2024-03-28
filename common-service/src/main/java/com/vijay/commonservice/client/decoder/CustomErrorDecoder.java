package com.vijay.commonservice.client.decoder;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.vijay.commonservice.client.exception.CustomException;
import com.vijay.commonservice.client.response.ErrorResponse;
import lombok.extern.log4j.Log4j2;

import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper
                = new ObjectMapper();

        log.info("::{}",response.request().url());
        log.info("::{}",response.request().headers());

        try {
            ErrorResponse errorResponse
                    = objectMapper.readValue(response.body().asInputStream(),
                    ErrorResponse.class);

            return new CustomException(errorResponse.getErrorMessage() ,
                    errorResponse.getErrorCode(),
                    response.status());

        } catch (IOException e) {
            throw  new CustomException("Internal Server Error",
                    "INTERNAL_SERVER_ERROR",
                    500);
        }
    }
}