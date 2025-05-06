package com.daw2.fct_bbdd.common.utils;

import lombok.Data;
import org.springframework.stereotype.Service;

import static com.daw2.fct_bbdd.FctBbddApplication.dotenv;

@Service
@Data
public class EnvironmentData {

    private final String JWT_SECRET_TOKEN = dotenv.get("jwt.jwtSecret");
    private final String JWT_EXP_TIME = dotenv.get("jwt.jwtExpirationMs");

}
