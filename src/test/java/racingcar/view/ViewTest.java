package racingcar.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ViewTest {

    @Test
    void splitByCommaAndTrim() {
        InputStream inputStream = new ByteArrayInputStream("ethan    ,\t\tverus  , pobi   \t\n".getBytes());
        View view = new View(System.out, inputStream);

        List<String> names = view.inputCarNames();

        Assertions.assertThat(names)
                .hasSize(3)
                .contains("ethan", "verus", "pobi");
    }

    @Test
    void inputUnderFiveLength() {
        InputStream inputStream = new ByteArrayInputStream("ethanasd    ,\t\tverusasd  , pobiasd   \t\n".getBytes());
        View view = new View(System.out, inputStream);

        Assertions.assertThatThrownBy(() -> view.inputCarNames())
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    void inputDistinctNames() {
        InputStream inputStream = new ByteArrayInputStream("verus, verus, pobi".getBytes());
        View view = new View(System.out, inputStream);

        Assertions.assertThatThrownBy(() -> view.inputCarNames())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void inputTryCount() {
        InputStream inputStream = new ByteArrayInputStream("012".getBytes());
        View view = new View(System.out, inputStream);

        int tryCount = view.inputMaxTryCount();

        Assertions.assertThat(tryCount).isEqualTo(12);
    }

    @Test
    void inputNotNumberTryCount() {
        InputStream inputStream = new ByteArrayInputStream("abc".getBytes());
        View view = new View(System.out, inputStream);

        Assertions.assertThatThrownBy(() -> view.inputMaxTryCount())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void inputOver100TryNumber() {
        InputStream inputStream = new ByteArrayInputStream("100".getBytes());
        View view = new View(System.out, inputStream);

        Assertions.assertThatThrownBy(() -> view.inputMaxTryCount())
                .isInstanceOf(IllegalArgumentException.class);
    }
}