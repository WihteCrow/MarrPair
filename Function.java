package org.inface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

public class Function {

  // 配偶选择的满意度，返回整数
	public static int satisfaction(int a, int b, int c, int a1, int b1, int c1) {
		/*
		 * a：表示期望样貌 b：表示期望品格 c：表示期望财富 a1：表示对方样貌 b1：表示对方品格 c1：表示对方财富
		 */
		int sum = a * a1 + b * b1 + c * c1;
		return sum;
	}

	// 自身三属性之和，返回一个整数
	public static int attribute(int[] a) {
		int sum;
		if (a != null) {
			sum = a[1] + a[2] + a[3];
		} else {
			sum = 0;
		}
		return sum;
	}

	// a 主动方 b 被动方 c 参照物，返回一个最优结点
	public static Node bestSatisfaction(Node a, Node b, Node c) {

		int ib = attribute(b.data); // 被动方(ib)自身三属性和
		int ic = attribute(c.data); // 参照物(ic)自身三属性和

		int ab = satisfaction(a.data[4], a.data[5], a.data[6], b.data[1],
				b.data[2], b.data[3]); // 主动方对被动方的满意度
		int ac;
		if (c.data != null) {
			ac = satisfaction(a.data[4], a.data[5], a.data[6], c.data[1],
					c.data[2], c.data[3]); // 主动方对参照物的满意度
		} else {
			ac = 0;
		}

		// 判断比较
		if (ab > ac) { // 主动方对被动方的满意度 > 主动方对参照物的满意度
			c = b;
		} else if (ab == ac) {
			if (ib > ic) { // 被动方(ib)自身三属性和 > 参照物(ic)自身三属性和
				c = b;
			} else if (ib == ic) {
				if (b.data[0] < c.data[0]) { // 判断ID大小，小的优先
					c = b;
				}
			}
		}
		return c;
	}

	// 字符串转化为整数，返回数组
	public static int[] trimData(String lineTxt) {
		// sDA代表String Data Array
		// iDA代表Int Data Array
		String[] sDA = null;
		
		sDA = lineTxt.split(","); // 分割每行字符串
		int[] iDA = new int[sDA.length];

		for (int i = 0; i < sDA.length; i++) {
			iDA[i] = Integer.parseInt(sDA[i]); // 字符串格式转化为int
		}
		return iDA;
	}

	// 读取文本文件内容，返回一个链表
	public static List readTxtFile(String filePath, List list) {
		try {
			String encoding = "GBK";
			File file = new File(filePath);

			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding); // 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					Node node = new Node(null);
					node.data = trimData(lineTxt);
					list.addLast(node);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return list;
	}

	// 男-女，将投票男加入被投票女的子结点上
	public static void doPair(List boyData, List girlData) throws MyException {

		Node head = girlData.getHead();
		while (head != null) {
			head.subLength = 0;
			head.subNext = null;
			head = head.next;
		}

		Node temp = new Node(null); // 初始化一个结点用来存储temp
		Node nodeB = boyData.getHead(); // 获得boyData链表的头结点nodeB

		while (nodeB != null) {
			Node nodeG = girlData.getHead(); // 获得girlData链表的头结点nodeG
			while (nodeG != null) {
				temp = bestSatisfaction(nodeB, nodeG, temp); // boyData链表的头结点nodeB，girlData链表的头结点nodeG，存储结点temp
				nodeG = girlData.getNext(nodeG); // nodeG下一个结点
			}
			girlData.addFristSub(nodeB, temp); // 把选nodeG的nodeB加入到对应的girlData链表的nodeG结点上另外一条subNext上
			nodeB = boyData.getNext(nodeB); // nodeB下一个节点
		}
	}

	// 女-男，在最多的投票结点上，选出最优的一个子结点，返回男生结点
	public static Node doGB(Node node) { // node最多投票的女生的头结点

		Node temp = new Node(null);
		Node G = new Node(null);
		Node N = new Node(null); // 初始化三个空结点

		G = node; // G女生节点
		N = node.subNext; // G的下一个子结点

		// node.subLength：投票人数
		for (int i = 0; i < node.subLength; i++) {
			temp = bestSatisfaction(G, N, temp);
			N = N.subNext; // 下一个子结点
		}
		return temp; // 最优的男生
	}

	// 删除男生链表的结点，满足ID = n
	public static void findDeletNode(List boyData, int n) throws MyException {

		Node temp = boyData.getHead(); // 男生链表头结点

		while (temp != null) {
			if (temp.data[0] == n) {
				boyData.deleteN(boyData.getPrev(temp), temp);
				break;
			}
			temp = temp.next;
		}
	}

	// 判断哪个女生的投票多,返回最多的那个女生
	public static Node moreNodeSub(List data) throws MyException {

		Node a = new Node(null);

		for (Node temp = data.getHead(); temp != null; temp = data
				.getNext(temp)) {
			if (a.subLength < temp.subLength) {
				a = temp;
			} else if (a.subLength == temp.subLength) {
				if (attribute(a.data) < attribute(temp.data)) {
					a = temp;
				} else if (attribute(a.data) == attribute(temp.data)) {
					if (a.data[0] > temp.data[0]) {
						a = temp;
					}
				}
			}
		}
		return a;
	}

	//插入主角，返回插入结点
	public static Node addPlayerData(List boyData, List girlData,List playerData,int n) throws MyException{
		Node playerNode = playerData.getNode(n); // 获得第n个主角结点
		if (playerNode.data[0] == 0) {
			playerNode.data[0] = -1;
			girlData.addFirst(playerNode);
		} // 修改ID为-1，放入女生链表头部
		if (playerNode.data[0] == 1) {
			playerNode.data[0] = -1;
			boyData.addFirst(playerNode);
		} // 修改ID为-1，放入男生链表头部
		return playerNode;
	}
	
	// 输出与主角完美配对的对方
	public static void resultPari(List boyData, List girlData, Node node) throws MyException {
		/*
		 * boyData：男生链表 girlData：女生链表 playerData：主角链表 n：选择第n个主角
		 */
		Node BD = boyData.getHead(); // 获得男生链表头结点BD

		while (BD != null) {
			doPair(boyData, girlData); // 男-女，将投票男加入被投票女的子结点上
			Node moreNode = moreNodeSub(girlData); // 选择最多票的女生moreNode
			Node bestB = doGB(moreNode); // 女-男，在最多的投票结点上，选出最优的一个子结点，返回男生结点

			Node a = girlData.getPrev(moreNode); // 获得这个女生的上一个结点a
			Node b = boyData.getPrev(bestB); // 获得这个男生的上一个结点b
			if (moreNode.data[0] == -1 || bestB.data[0] == -1) {
				System.out.print("男:\t");
				for (int i = 0; i < 7; i++) {
					System.out.print(bestB.data[i] + "\t");
				}
				System.out.println(); // 输出这个男生
				System.out.print("女:\t");
				for (int i = 0; i < 7; i++) {
					System.out.print(moreNode.data[i] + "\t");
				}
				System.out.println(); // 输出这个女生
			}

			if (a.data == null) {
				girlData.deleteFirst(); // 直接删除头结点
			} else {
				girlData.deleteN(a, moreNode); // 删除女结点
			}
			if (b.data == null) {
				boyData.deleteFirst(); // 直接删除头结点
			} else {
				findDeletNode(boyData, bestB.data[0]); // 删除男结点
			}
			if (moreNode == node || bestB == node) { // 如果主角匹配完成，则推出循环
				break;
			}
		}

	}
	
	//生成随机数据，返回数组
	public static int[] randomInt() {
		int[] data = new int[7];
		Random a = new Random();
		for(int i=1; i<4; i++){
			data[i] = a.nextInt(99) + 1;
		}
			data[4] = a.nextInt(97) + 1;
			data[5] = a.nextInt(98 - data[4]) + 1;
			data[6] = 100 - data[4] -data[5];
		return data;
	}

	// 随机链表生成
	public static void randomList(Node head, List boyData, List girlData) throws MyException {

		if (head.data[0] == 1) {
			head.data[0] = -1;
			boyData.addFirst(head);
			for(int i=1; i<100; i++){
				Node temp = new Node(null);
				temp.data = randomInt();
				temp.data[0] = i;
				boyData.addLast(temp);
			}
			for(int i=1; i<101; i++){
				Node temp = new Node(null);
				temp.data = randomInt();
				temp.data[0] = i;
				girlData.addLast(temp);
			}
		}
		else if(head.data[0] == 0){
			head.data[0] = -1;
			girlData.addFirst(head);
			for(int i=1; i<100; i++){
				Node temp = new Node(null);
				temp.data = randomInt();
				temp.data[0] = i;
				girlData.addLast(temp);
			}
			for(int i=1; i<101; i++){
				Node temp = new Node(null);
				temp.data = randomInt();
				temp.data[0] = i;
				boyData.addLast(temp);
			}
		}
		else{
			System.out.println("性别不正确");
		}
	}

	public static void main(String agr[]) throws MyException, IOException{

		System.out.println("文本匹配模式输入：1");
		System.out.println("随机匹配模式输入：2");

		int mo = 0;
		try{
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			 mo = in.nextInt();
		}catch(Exception e){
			System.out.println("请输入1或2，选择模式");
		}
		
		if (mo == 1) {

			List boyData = new List(); // 男生链表
			List girlData = new List(); // 女生链表
			List playerData = new List(); // 主角链表

			String filePath_01 = "./TXT/male.txt";
			String filePath_02 = "./TXT/female.txt";
			String filePath_03 = "./TXT/players.txt";

			boyData = readTxtFile(filePath_01, boyData);
			girlData = readTxtFile(filePath_02, girlData);
			playerData = readTxtFile(filePath_03, playerData);

			System.out.println("文本匹配模式：");
			System.out.println("输入主角号：");
			int num =0;
			try{
				@SuppressWarnings("resource")
				Scanner nu = new Scanner(System.in);
				num = nu.nextInt();
			}catch(Exception e){
				System.out.println("请输入数字（1-100）");
			}
			Node playerNode = addPlayerData(boyData, girlData, playerData,num);
			resultPari(boyData, girlData, playerNode);
		}
		else if (mo == 2) {
			System.out.println("随机匹配模式：");
			System.out.println("\t条件：");
			System.out.println("\t\t1、英文逗号”,“");
			System.out.println("\t\t1、所有属性在1和100之间");
			System.out.println("\t\t2、期望样貌、期望品格、期望财富必须等于100");
			System.out.println("请输入属性值按照这样顺寻：性别（1男、0女）,样貌,财富 ,期望样貌,期望品格,期望财富");
			
			Node head = new Node(null);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String str = br.readLine();
			try{
				head.data = trimData(str);
			}catch(Exception e){
				System.out.println("请输入正确格式");
			}
			
			List boyData = new List();
			List girlData = new List();
			
			randomList(head,boyData,girlData);

			resultPari(boyData, girlData,head );
		}else{
			System.out.println("请输入1或2，选择模式");
		}

	}
}
