package org.inface;

public class Node {
  public int[] data = null; // 结点数据
	public int subLength = 0;
	public Node subNext = null;
	public Node next = null; // 结点的下一个结点
	
	// 结点构造函数
	Node(int[] value) {
		this.data = value;
	}
	
	public void printNode(){
		for (int i = 0; i < 7; i++) {
			System.out.print(this.data[i] + "\t");
		}
		System.out.println();
	}
	
	public void printle(){
		System.out.println(this.subLength);
	}
}
