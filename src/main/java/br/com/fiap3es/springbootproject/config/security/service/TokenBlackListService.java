package br.com.fiap3es.springbootproject.config.security.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlackListService {
    private final Map<String, Instant> blackList = new ConcurrentHashMap<>();

    public void add(String token, Instant expiracao) {
        blackList.put(token, expiracao);
    }

    public boolean isBlackListed(String token) {
        Instant expiracao = blackList.get(token);
        if(expiracao == null) {
            return false;
        }
        if(Instant.now().isAfter(expiracao)) {
            blackList.remove(token);
            return false;
        }
        return true;
    }

    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void cleanUpExpiredTokens() {
        Instant now = Instant.now();
        blackList.entrySet()
                .removeIf(entry -> Instant.now()
                        .isAfter(entry.getValue())
                );
    }
}