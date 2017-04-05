package enamel;

import static org.junit.Assert.*;

import org.junit.Test;

public class AuthoringDisplayTest {

	RevisedApp ra = new RevisedApp();

	@Test
	public void testCheckEdit() {

		ra.disp.frame.setVisible(false);
		assertEquals(ra.disp.checkEdit("4"), true);
		assertEquals(ra.disp.checkEdit("34"), true);
		assertEquals(ra.disp.checkEdit("22"), true);
		assertEquals(ra.disp.checkEdit("2"), true);
		assertEquals(ra.disp.checkEdit("9"), true);

		// assertEquals(ra.disp.checkEdit("-1"), false);
		// assertEquals(ra.disp.checkEdit("0"), false);
		// assertEquals(ra.disp.checkEdit("-7"), false);
		// assertEquals(ra.disp.checkEdit("-34"), false);
		// assertEquals(ra.disp.checkEdit("-4444"), false);
		// assertEquals(ra.disp.checkEdit("-)(_)_+"), false);

	}

	@Test
	public void testCheckRaiseLower() {
		assertEquals(ra.disp.checkRaiseLower("0 1"), true);
		assertEquals(ra.disp.checkRaiseLower("0 2"), true);
		assertEquals(ra.disp.checkRaiseLower("0 3"), true);
		assertEquals(ra.disp.checkRaiseLower("0 4"), true);
		assertEquals(ra.disp.checkRaiseLower("0 5"), true);
		assertEquals(ra.disp.checkRaiseLower("0 6"), true);
		assertEquals(ra.disp.checkRaiseLower("0 7"), true);
		assertEquals(ra.disp.checkRaiseLower("0 8"), true);

		// assertEquals(ra.disp.checkRaiseLower("0 90"), false);
		// assertEquals(ra.disp.checkRaiseLower("0 -23"), false);
		// assertEquals(ra.disp.checkRaiseLower("0 0"), false);
		// assertEquals(ra.disp.checkRaiseLower("0"), false);
		// assertEquals(ra.disp.checkRaiseLower("0000"), false);
		// assertEquals(ra.disp.checkRaiseLower("0+_"), false);
		// assertEquals(ra.disp.checkRaiseLower("0zzz"), false);

	}

	@Test
	public void testCheckDispCellPins() {
		assertEquals(ra.disp.checkDispCellPins("0 00110000"), true);
		assertEquals(ra.disp.checkDispCellPins("0 11111111"), true);
		assertEquals(ra.disp.checkDispCellPins("0 10110110"), true);
		assertEquals(ra.disp.checkDispCellPins("0 00000000"), true);
		assertEquals(ra.disp.checkDispCellPins("0 11100000"), true);
		assertEquals(ra.disp.checkDispCellPins("0 00110011"), true);

		// assertEquals(ra.disp.checkDispCellPins("-1 00110000"), false);
		// assertEquals(ra.disp.checkDispCellPins("00110000"), false);
		// assertEquals(ra.disp.checkDispCellPins("-00-8 00110000"), false);
		// assertEquals(ra.disp.checkDispCellPins("0 12340000"), false);
		// assertEquals(ra.disp.checkDispCellPins("-1 001100000000000000000"),
		// false);
	}

	@Test
	public void testCheckRepeatButton() {
		assertEquals(ra.disp.checkRepeatButton("0"), true);

		// assertEquals(ra.disp.checkRepeatButton("90"), false);
		// assertEquals(ra.disp.checkRepeatButton("-9"), false);
		// assertEquals(ra.disp.checkRepeatButton("3"), false);
		// assertEquals(ra.disp.checkRepeatButton("-1"), false);
		// assertEquals(ra.disp.checkRepeatButton("-034-d"), false);

	}

	@Test
	public void testCheckDispChar() {
		assertEquals(ra.disp.checkDispChar("0 A"), true);
		assertEquals(ra.disp.checkDispChar("0 b"), true);
		assertEquals(ra.disp.checkDispChar("0 C"), true);
		assertEquals(ra.disp.checkDispChar("0 d"), true);
		assertEquals(ra.disp.checkDispChar("0 E"), true);
		assertEquals(ra.disp.checkDispChar("0 f"), true);
		assertEquals(ra.disp.checkDispChar("0 G"), true);
		assertEquals(ra.disp.checkDispChar("0 h"), true);
		assertEquals(ra.disp.checkDispChar("0 I"), true);
		assertEquals(ra.disp.checkDispChar("0 j"), true);
		assertEquals(ra.disp.checkDispChar("0 K"), true);
		assertEquals(ra.disp.checkDispChar("0 l"), true);
		assertEquals(ra.disp.checkDispChar("0 M"), true);
		assertEquals(ra.disp.checkDispChar("0 n"), true);
		assertEquals(ra.disp.checkDispChar("0 O"), true);
		assertEquals(ra.disp.checkDispChar("0 p"), true);
		assertEquals(ra.disp.checkDispChar("0 Q"), true);
		assertEquals(ra.disp.checkDispChar("0 r"), true);
		assertEquals(ra.disp.checkDispChar("0 S"), true);
		assertEquals(ra.disp.checkDispChar("0 t"), true);
		assertEquals(ra.disp.checkDispChar("0 U"), true);
		assertEquals(ra.disp.checkDispChar("0 v"), true);
		assertEquals(ra.disp.checkDispChar("0 W"), true);
		assertEquals(ra.disp.checkDispChar("0 x"), true);
		assertEquals(ra.disp.checkDispChar("0 Y"), true);
		assertEquals(ra.disp.checkDispChar("0 z"), true);

		// assertEquals(ra.disp.checkDispChar("0 4"), false);
		// assertEquals(ra.disp.checkDispChar("0 +"), false);
		// assertEquals(ra.disp.checkDispChar("0 !!"), false);
		// assertEquals(ra.disp.checkDispChar("0 EE"), false);
		// assertEquals(ra.disp.checkDispChar("-2 E"), false);
		// assertEquals(ra.disp.checkDispChar("-3343 =_+"), false);
		// assertEquals(ra.disp.checkDispChar("5 ;;"), false);
		// assertEquals(ra.disp.checkDispChar("3 ()"), false);
	}

	@Test
	public void testCheckVoice() {
		assertEquals(ra.disp.checkVoice("1"), true);
		assertEquals(ra.disp.checkVoice("2"), true);
		assertEquals(ra.disp.checkVoice("3"), true);
		assertEquals(ra.disp.checkVoice("4"), true);

		// assertEquals(ra.disp.checkVoice("5"), false);
		// assertEquals(ra.disp.checkVoice("-1"), false);
		// assertEquals(ra.disp.checkVoice("0"), false);
		// assertEquals(ra.disp.checkVoice("78"), false);
	}

	@Test
	public void testCheckSound() {
		assertEquals(ra.disp.checkSound("wrong.wav"), true);
		assertEquals(ra.disp.checkSound("correct.wav"), true);
		assertEquals(ra.disp.checkSound("beep1.wav"), true);
		assertEquals(ra.disp.checkSound("beep13.wav"), true);
		assertEquals(ra.disp.checkSound("beep2.wav"), true);
		assertEquals(ra.disp.checkSound("beep9.wav"), true);

		// assertEquals(ra.disp.checkSound("hello.wav"), false);
		// assertEquals(ra.disp.checkSound("answer"), false);
		// assertEquals(ra.disp.checkSound("nothere"), false);
		// assertEquals(ra.disp.checkSound("beep9.mp4"), false);
		// assertEquals(ra.disp.checkSound("correct.mp3"), false);

	}

	@Test
	public void testCheckAnswerButton() {
		assertEquals(ra.disp.checkAnswerButton("0"), true);

		// assertEquals(ra.disp.checkAnswerButton("1"), false);
		// assertEquals(ra.disp.checkAnswerButton("77"), false);
		// assertEquals(ra.disp.checkAnswerButton("89"), false);
		// assertEquals(ra.disp.checkAnswerButton("-8"), false);
		// assertEquals(ra.disp.checkAnswerButton("-1"), false);

	}

	@Test
	public void testCheckDispCellClear() {
		assertEquals(ra.disp.checkDispCellClear("0"), true);

		// assertEquals(ra.disp.checkDispCellClear("2"), false);
		// assertEquals(ra.disp.checkDispCellClear("8"), false);
		// assertEquals(ra.disp.checkDispCellClear("0988"), false);
		// assertEquals(ra.disp.checkDispCellClear("-34"), false);
		// assertEquals(ra.disp.checkDispCellClear("-1"), false);

	}
}
