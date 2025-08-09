package com.luruoyang.utils;

import com.luruoyang.context.ThreadLocalContext;
import com.luruoyang.enums.EmpError;
import com.luruoyang.exception.BusinessException;
import com.luruoyang.properties.JJwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@Data
public class JJwtUtils {
//  @Value("${jjwt.secret}")
//  private String secret;
//  @Value("${jjwt.expiration}")
//  private Long expiration;

/*  public String gen(Map<String, Object> claims) {
    Date expireAt = new Date(Instant.now().getEpochSecond() + expiration);
    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
        .setExpiration(expireAt)
        .compact();
  }*/

  public String gen(Map<String, Object> claims, String secret, Long durationMills) {
    if (claims.isEmpty()) {
      log.error("no jwt claims");
      throw new RuntimeException("no jwt claims");
    }

    if (secret == null || secret.isBlank() || secret.isEmpty()) {
      log.error("no jwt secret");
      throw new RuntimeException("no jwt secret");
    }

    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    Date expireAt = new Date(Instant.now().toEpochMilli() + durationMills);
    log.warn("---> jwt expireAt: {}", expireAt);
    return Jwts.builder()
        .header()
        .add("typ", "JWT")
        .add("alg", "HS256")
        .and()
        .claims()
        .add(claims)
        .and()
        .expiration(expireAt)
        .signWith(secretKey, Jwts.SIG.HS256)
        .compact();
  }

  public Claims parse(String token, String secret) {
    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public boolean check(String token, String secret) {
    if (Objects.isNull(token) || token.isBlank()) {
      log.error(" loggin without token: {}", token);
      return false;
    }

    Claims claims = null;
    try {
      claims = parse(token, secret);
      // log.info("parsed claims: {}", claims);
      /* 设置ThreadLocal */
      Object currentUser = claims.get("id");
      if (currentUser == null) {
        log.error("tl set userId error : {}", currentUser);
        throw new RuntimeException("tl set userId error");
      }

      /* 保存当前登录用户到Thread Local */
      ThreadLocalContext.set(Long.parseLong(currentUser.toString()));
    } catch (Exception e) {
      log.warn("jwt parse failed: {}", e.getMessage());
      throw e;
    }

    return true;
  }

}
