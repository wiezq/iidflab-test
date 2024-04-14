package org.example.transactiontracker.limit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LimitServiceUnitTest {

    @Mock
    private LimitRepository limitRepository;

    @InjectMocks
    private LimitService limitService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrCreateLimitWhenNotFoundLimit() {
        // Given
        Limit expectedLimit = new Limit();
        expectedLimit.setAccountId(1L);
        expectedLimit.setExpenseCategory("service");
        when(limitRepository.findTopByExpenseCategoryAndAccountId(anyLong(), anyString())).thenReturn(Optional.empty());
        when(limitRepository.save(any(Limit.class))).thenReturn(expectedLimit);

        // When
        Limit actualLimit = limitService.getOrCreateLimit(1L, "service");

        // Then
        assertNotNull(actualLimit);
        assertEquals(expectedLimit, actualLimit);
        assertEquals(actualLimit.getAccountId(), 1L);
        assertEquals(actualLimit.getExpenseCategory(), "service");
        verify(limitRepository, times(1)).save(any(Limit.class));
    }

    @Test
    public void testGetOrCreateLimitWhenFoundLimit() {
        // Given
        Limit expectedLimit = new Limit();
        expectedLimit.setAccountId(1L);
        expectedLimit.setExpenseCategory("service");
        when(limitRepository.findTopByExpenseCategoryAndAccountId(anyLong(), anyString())).thenReturn(Optional.of(expectedLimit));

        // When
        Limit actualLimit = limitService.getOrCreateLimit(1L, "service");

        // Then
        assertNotNull(actualLimit);
        assertEquals(expectedLimit, actualLimit);
        assertEquals(actualLimit.getAccountId(), 1L);
        assertEquals(actualLimit.getExpenseCategory(), "service");
        verify(limitRepository, never()).save(any(Limit.class));
    }

    @Test
    public void testSaveLimitWhenNoPreviousLimit() {
        // Given
        Limit limit = new Limit();
        limit.setAccountId(1L);
        limit.setExpenseCategory("service");
        limit.setTotalAmountOfTransactions(BigDecimal.ZERO);
        when(limitRepository.findLatestLimitBeforeDate(anyLong(), anyString(), any(LocalDateTime.class))).thenReturn(Optional.empty());
        when(limitRepository.save(any(Limit.class))).thenReturn(limit);

        // When
        limitService.saveLimit(limit);

        // Then
        verify(limitRepository, times(1)).save(limit);
        assertEquals(BigDecimal.ZERO, limit.getTotalAmountOfTransactions());
    }

    @Test
    public void testSaveLimitWhenPreviousLimitExists() {
        // Given
        Limit limit = new Limit();
        limit.setAccountId(1L);
        limit.setExpenseCategory("service");
        limit.setTotalAmountOfTransactions(BigDecimal.ZERO);

        Limit previousLimit = new Limit();
        previousLimit.setTotalAmountOfTransactions(new BigDecimal(200));

        when(limitRepository.findLatestLimitBeforeDate(anyLong(), anyString(), any(LocalDateTime.class))).thenReturn(Optional.of(previousLimit));
        when(limitRepository.save(any(Limit.class))).thenReturn(limit);

        // When
        limitService.saveLimit(limit);

        // Then
        verify(limitRepository, times(1)).save(limit);
        assertEquals(new BigDecimal(200), limit.getTotalAmountOfTransactions());
    }

}
