package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * gcd() функцийн all-DU-pairs хамралт.
 * Мөрийн дугаар нь DataFlowTargets.java файлын мөр: 13 (def x, y), 15 (while y != 0),
 * 16 (tmp = x % y), 17 (x = y), 18 (y = tmp), 20 (return x).
 * Хос бүрийн тэмдэглэгээ: хувьсагч + def мөр -> use мөр (p-use бол T/F салаа).
 */
class GcdTest {

    // G1: давталт огт ажиллахгүй. Зөвхөн энэ тест x13->20 ба y13->15(F) хосыг хамарна.
    @Test
    @DisplayName("G1 gcd(7,0)=7, 0 iterations: x13->20, y13->15F")
    void g1_zeroIterations() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    // G2: яг 1 давталт. 8 % 4 = 0, тиймээс y=0 болж гарна.
    @Test
    @DisplayName("G2 gcd(8,4)=4, 1 iteration: y13->15T, x13->16, y13->16, y13->17, tmp16->18, y18->15F, x17->20")
    void g2_oneIteration() {
        assertEquals(4, DataFlowTargets.gcd(8, 4));
    }

    // G3: 2 давталт. 12 % 8 = 4 (x=8, y=4), дараа нь 8 % 4 = 0 (x=4, y=0).
    // 17 ба 18 мөрийн def хоёр дахь давталтад use-д хүрнэ.
    @Test
    @DisplayName("G3 gcd(12,8)=4, 2 iterations: x17->16, y18->16, y18->17, y18->15T")
    void g3_twoIterations() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }
}