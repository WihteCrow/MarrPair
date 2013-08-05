//package org.inface;

public class List {
  // 链表的头指针和尾指针
	private Node head;

	// 单链表构造函数
	public List() {
		head = null;
	}

	// 获得链表头元素
	public Node getHead() throws MyException {
		if (head == null) {
			System.out.println("链表为空");
		}
		return head;
	}
	
	//查找结点上一个结点
	public Node getPrev(Node node) throws MyException{

		Node temp = this.getHead();
		Node TP = new Node(null);
		
		while(temp != null){
			if(temp == node){
				break;
			}
			TP = temp;
			temp = temp.next;
		}
		return TP;
	}
	
	// 获得结点的下一个结点
	public Node getNext(Node node) {
		return node.next;
	}

	// 判断该结点是否还有下一个结点
	public boolean hasNext(Node node) {
		if (node.next == null) {
			return false;
		}
		return true;
	}

	// 获得链表的最后一个元素
	public Node getLast() throws MyException {
		Node curNode = null;
		Node next = null;

		if (head == null) {
			System.out.println("链表为空");
		} else {
			curNode = head;
			while (hasNext(curNode)) {
				next = curNode.next;
				curNode = next;
			}
		}
		return curNode;
	}

	// 根据索引获得元素
	public Node getNode(int index) throws MyException {
		Node node = null;
		Node curNode = null;
		Node next = null;

		if (head == null) {
			System.out.println("链表为空");
		} else {
			curNode = head;
			for (int i = 0; i < index; i++) {
				if (curNode == null) {
					throw new MyException("你要查找的元素索引超过了链表的长度");
				}
				node = curNode;
				if (hasNext(curNode)) {
					next = curNode.next;
					curNode = next;
				} else {
					curNode = null;
				}
			}
		}
		return node;
	}

	// 在链表头添加结点
	public void addFirst(Node node) {
		if (head == null) {
			head = node;
		} else {
			Node next = head;
			node.next = next;
			head = node;
		}
	}

	// 在链表尾部添加元素
	public void addLast(Node node) throws MyException {
		if (head == null) {
			head = node;
		} else {
			Node last = this.getLast();
			last.next = node;
		}
	}

	// 获得子结点的下一个结点
	public Node getNextSub(Node node) {
		return node.subNext;
	}

	// 判断该子结点是否还有下一个结点
	public boolean hasNextSub(Node node) {
		if (node.subNext == null) {
			return false;
		}
		return true;
	}

	// 在子链表头部添加元素
	public void addFristSub(Node node, Node temp){		//node 男，temp 女

		if (temp.subNext == null) {
			temp.subNext = node;
			temp.subLength++;
		} else {
			Node a = temp.subNext;
			temp.subNext = node;
			node.subNext = a;
			temp.subLength++;
		}
	}

	// 在链表中间插入元素
	public void insertNode(int index, Node node) throws MyException {
		Node prevNode = null;

		try {
			prevNode = getNode(index - 1);
		} catch (MyException rex) {
			rex.printStackTrace();
			System.out.println("插入结点的索引大于链表长度");
		}

		if (hasNext(prevNode)) {
			Node next = prevNode.next;
			prevNode.next = node;
			node.next = next;
		} else {
			prevNode.next = node;
		}
	}

	//删除节点
	public void deleteN(Node prev,Node node){
		prev.next = node.next;
		node.next = null;
	}
	
	// 删除链表的第一个元素
	public Node deleteFirst() {
		Node first = null;
		Node node = head.next;
		first = head;
		head = node;
		return first;
	}

	// 删除链表的最后一个元素
	public Node deleteLast() {
		Node last = null;
		Node curNode = head;
		Node next = null;
		boolean flag = true;

		if (!hasNext(head)) {
			head = null;
		} else {
			while (flag) {
				next = curNode.next;
				if (hasNext(next)) {
					curNode = next;
				} else {
					curNode.next = null;
					last = next;
					flag = false;
				}
			}
		}
		return last;
	}

	// 按照索引删除元素
	public Node deleteNode(int index) throws MyException {
		Node prevNode = null;
		try {
			prevNode = getNode(index - 1);
		} catch (MyException mex) {
			mex.printStackTrace();
			System.out.println("你要删除的结点索引大于链表的长度");
		}
		Node node = null;
		if (hasNext(prevNode)) {
			node = prevNode.next;
			if (hasNext(node)) {
				Node next = node.next;
				prevNode.next = next;
			} else {
				prevNode.next = null;
			}
		} else {
			System.out.println("你要删除的结点索引大于链表的长度");
		}
		return node;
	}

	// 输出所有结点

	public void printListNode() {
		int i = 0;
		for (Node temp = head; temp != null; temp = getNext(temp)) {
			temp.printNode();
			i++;
		}
		System.out.println(i);
	}
	
	public void printListl(){
		for (Node temp = head; temp != null; temp = getNext(temp)) {
			temp.printle();
		}
	}
}
