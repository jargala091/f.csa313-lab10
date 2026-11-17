package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * bonus() функцийн өгөгдлийн урсгалын тестүүд.
 * Мөрийн дугаар нь DataFlowTargets.java файлын мөр (31-56).
 * Хосын нэр (P1..P35) нь README.md-ийн B3 хүснэгтийн дугаар.
 */
class BonusTest {

    // ---------------- 1-р шат: ALL-DEFS (2 тест) ----------------
    // score-ийн 49 дэх def зөвхөн 50 F үед use-д хүрнэ, 51 дэх def зөвхөн 50 T үед үүснэ.
    // Нэг гүйцэтгэлд хоёуланг нь хамрах боломжгүй тул доод тал нь 2 тест.

    // AD1: 5 өдрийн цуваа, тасалсан, дахин ирсэн. present = 6 < 10 (50 F), best = 5 (53 T).
    // Def: attendance31, threshold31, present35/40, streak36/41/46, best37/43, a38, score49, score54.
    @Test
    @DisplayName("AD1 bonus([1,1,1,1,1,0,1],10)=3: score49->54, score54->56, streak46->41, best43->53T")
    void ad1_streakOfFiveBelowThreshold() {
        assertEquals(3, DataFlowTargets.bonus(new int[]{1, 1, 1, 1, 1, 0, 1}, 10));
    }

    // AD2: нэг ирсэн өдөр, threshold = 0. present = 1 >= 0 (50 T), best = 1 (53 F).
    // Def: score51 (-> 56).
    @Test
    @DisplayName("AD2 bonus([1],0)=5: score51->56, present40->50T, best43->53F")
    void ad2_singleDayThresholdZero() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{1}, 0));
    }
}
