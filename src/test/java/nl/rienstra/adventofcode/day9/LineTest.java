package nl.rienstra.adventofcode.day9;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LineTest {
    
    @Test
    void testCrosses() {
        assertThat(new Line(new Point(7, 3), new Point(7, 1)).crosses(new Line(new Point(2,3), new Point(9,3)))).isFalse();
        assertThat(new Line(new Point(2, 3), new Point(7, 3)).crosses(new Line(new Point(2,1), new Point(2,5)))).isTrue();
    }
}
