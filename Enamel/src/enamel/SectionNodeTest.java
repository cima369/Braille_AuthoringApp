package enamel;

import static org.junit.Assert.*;

import org.junit.Test;

public class SectionNodeTest {

	SectionNode root = new SectionNode("Test");
	SectionNode nodeOne = new SectionNode("Testing");
	SectionNode nodeTwo = new SectionNode("Variable");
	SectionNode nodeThree = new SectionNode("Random");
	SectionNode nodeFour = new SectionNode("Identifier");
	SectionNode nodeFive = new SectionNode("EECS2311");

	@Test
	public void testGetChild() {

		root.addChild(nodeOne);
		root.addChild(nodeTwo);
		root.addChild(nodeThree);
		root.addChild(nodeFour);
		root.addChild(nodeFive);

		assertEquals(root.getChild(0), nodeOne);
		assertEquals(root.getChild(1), nodeTwo);
		assertEquals(root.getChild(2), nodeThree);
		assertEquals(root.getChild(3), nodeFour);
		assertEquals(root.getChild(4), nodeFive);

	}

	@Test
	public void testGetIdentity() {
		
		assertEquals(root.getIdentity(), "/~Test");
		assertEquals(nodeOne.getIdentity(), "/~Testing");
		assertEquals(nodeTwo.getIdentity(), "/~Variable");
		assertEquals(nodeThree.getIdentity(), "/~Random");
		assertEquals(nodeFour.getIdentity(), "/~Identifier");
		assertEquals(nodeFive.getIdentity(), "/~EECS2311");

	}

	@Test
	public void testGetNumChildren() {
		
		root.addChild(nodeOne);
		root.addChild(nodeTwo);
		assertEquals(root.getNumChildren(), 2);

		root.addChild(nodeThree);
		root.addChild(nodeFour);
		assertEquals(root.getNumChildren(), 4);

		root.addChild(nodeFive);
		assertEquals(root.getNumChildren(), 5);
	}

	@Test
	public void testGetInfo() {
		
		nodeOne.addInfo("Sound");
		nodeTwo.addInfo("Children");
		nodeThree.addInfo("Third");
		nodeFour.addInfo("/~Sound");
		nodeFive.addInfo("Root");
		
		assertTrue(nodeOne.getInfo().get(0).equals("Sound"));
		assertTrue(nodeTwo.getInfo().get(0).equals("Children"));
		assertTrue(nodeThree.getInfo().get(0).equals("Third"));
		assertTrue(nodeFour.getInfo().get(0).equals("/~Sound"));
		assertTrue(nodeFour.getInfo().get(0).equals("Root"));
				
	}

}
