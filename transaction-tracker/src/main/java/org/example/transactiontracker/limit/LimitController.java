package org.example.transactiontracker.limit;

import lombok.AllArgsConstructor;
import org.example.transactiontracker.limit.dto.LimitRequest;
import org.example.transactiontracker.limit.utils.LimitMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/limit")
@AllArgsConstructor
public class LimitController {

    private final LimitService limitService;

    private final LimitMapper limitMapper;

    @PostMapping
    public ResponseEntity<?> createLimit(@RequestBody LimitRequest limitDto){
        Limit limit = limitMapper.toEntity(limitDto);
        limitService.saveLimit(limit);
        return ResponseEntity.ok().build();
    }
}
