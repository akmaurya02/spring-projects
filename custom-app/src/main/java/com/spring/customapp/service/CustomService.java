package com.spring.customapp.service;

import com.spring.customapp.constants.AppConstants;
import com.spring.customapp.dto.CustomAppRequestDTO;
import com.spring.customapp.config.RedisConf;
import com.spring.customapp.util.ObjectMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Objects;

@Service
public class CustomService {

    @Autowired
    private ObjectMapperService objectMapperService;

    @Autowired
    private RedisConf redisConf;

    public void processCustomAppRequest(CustomAppRequestDTO customAppRequestDTO) {
        Jedis jedis = null;
        try {
            jedis = redisConf.getResource();
            jedis.lpush(AppConstants.DATA_QUEUE, objectMapperService.objectToJsonMapper(customAppRequestDTO));
            System.out.println("DATA PUSHED IN REDIS DATA QUEUE:: DATA:: " + customAppRequestDTO);
        } catch (Exception e) {
            System.out.println("ERROR OCCURRED WHILE PROCESSING REQUEST:: " + e.getMessage() + ", DATA :: " + customAppRequestDTO);
        } finally {
            if (Objects.nonNull(jedis)) {
                jedis.close();
            }
        }
    }

    public void processQueueData(String data) {
        CustomAppRequestDTO customAppRequestDTO = objectMapperService.jsonToObjectMapper(data, CustomAppRequestDTO.class);
        System.out.println("PROCESSING DATA FORM REDIS DATA QUEUE:: DATA:: " + customAppRequestDTO);
    }
}
