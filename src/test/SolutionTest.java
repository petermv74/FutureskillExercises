import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

class SolutionTest {

    @Mock
    APICaller apiCallerMock;

    @BeforeEach
    void setUp() {
        when(apiCallerMock.getNumDays()).thenReturn(6);
        when(apiCallerMock.getPriceOnDay(0)).thenReturn(getPriceForDay(0));
    }

    private Integer getPriceForDay(int i) {
        return null;
    }

    @Test
    void getBuyDayLevel1() {

    }

    @Test
    void getSellDay() {
    }
}