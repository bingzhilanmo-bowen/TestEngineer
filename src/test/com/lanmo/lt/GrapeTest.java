package com.lanmo.lt;

import org.junit.Test;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

public class GrapeTest {


	@Test
	public void test1(){

		Stack<Integer> sts = new Stack<>();

		sts.add(1);
		sts.add(2);
		sts.add(3);
		sts.add(4);
		sts.add(5);

		while (!sts.isEmpty()){
			System.out.println(sts.pop());
		}

	}

	@Test
	public void test2(){

		Queue<Integer> queue = new PriorityQueue<>();
		queue.add(1);
		queue.add(2);
		queue.add(3);
		queue.add(4);
		queue.add(5);

		while (!queue.isEmpty()){
			System.out.println(queue.poll());
		}

		//TreeMap

	}

}
