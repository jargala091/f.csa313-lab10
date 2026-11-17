package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    // ---------------- 2-р шат: ALL-DU-PAIRS (AD1, AD2 дээр нэмэх) ----------------
    // AD1, AD2-оор хамрагдаагүй хосууд: P1, P5, P10, P11, P24, P30, P31.
    // P22 ба P23 биелэшгүй (README-д үндэслэл).

    // DU1: attendance31 -> 32 (== null) T.
    @Test
    @DisplayName("DU1 bonus(null,0) throws: attendance31->32T (P1)")
    void du1_nullAttendanceThrows() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(null, 0));
    }

    // DU2: attendance != null, threshold31 -> 32 (< 0) T.
    @Test
    @DisplayName("DU2 bonus([1],-1) throws: attendance31->32F, threshold31->32T (P2, P5)")
    void du2_negativeThresholdThrows() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(new int[]{1}, -1));
    }

    // DU3: хоосон массив, threshold = 0. present = 0 >= 0, best = 0.
    @Test
    @DisplayName("DU3 bonus([],0)=5: attendance31->38F, present35->50T, best37->53F, score51->56 (P4, P10, P24, P32)")
    void du3_emptyArrayThresholdZero() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{}, 0));
    }

    // DU4: бүгд тасалсан. present = 0 < 1, best = 0, score 49 өөрчлөгдөлгүй буцна.
    @Test
    @DisplayName("DU4 bonus([0,0],1)=0: present35->50F, best37->53F, score49->56, a38->39F (P11, P24, P30, P35)")
    void du4_allAbsent() {
        assertEquals(0, DataFlowTargets.bonus(new int[]{0, 0}, 1));
    }

    // DU5: яг threshold (present = 5 = threshold) ба яг 5 өдрийн цуваа -> 5 + 3.
    @Test
    @DisplayName("DU5 bonus([1,1,1,1,1],5)=8: present40->50T, best43->53T, score51->54, score54->56 (P13, P27, P31, P33)")
    void du5_exactThresholdAndStreakFive() {
        assertEquals(8, DataFlowTargets.bonus(new int[]{1, 1, 1, 1, 1}, 5));
    }

    // DU6: урт цуваа дунд нь тасарна, дараа нь 5-аас урт (6) цуваа. present = 8 >= 3.
    @Test
    @DisplayName("DU6 bonus([1,1,0,1,1,1,1,1,1],3)=8: streak46->41, best43->42F, best43->42T (P18, P20, P25, P26)")
    void du6_streakBrokenThenLonger() {
        assertEquals(8, DataFlowTargets.bonus(new int[]{1, 1, 0, 1, 1, 1, 1, 1, 1}, 3));
    }
}
