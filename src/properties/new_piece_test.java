package properties;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import board.pieces.Queen;

public class new_piece_test {

	@Test
	public void test_BBc8() {
		assertEquals('B', board.pieces.Pieces.create_new_piece("B", 0, "c8").name);
	}

	@Test
	public void test_WQh3() {
		board.pieces.Queen q = (Queen) board.pieces.Pieces.create_new_piece("Q", 1, "h3");
		assertEquals('Q', q.name);
		assertEquals("h3", q.location);
		assertEquals(1, q.color);

	}

	@Test
	public void test_Bishop() {
		assertEquals('B', board.pieces.Bishop.name);
	}

}