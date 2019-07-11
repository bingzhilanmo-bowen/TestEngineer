package com.lanmo.lt;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;

public class DoubleKeyTest {


	@Test
	public void testSqrt(){

		Assert.assertFalse(!judgeSqrt(5));

		//Assert.assertFalse(!judgeSqrt(6));

		//Assert.assertFalse(!judgeSqrt(7));

		Assert.assertFalse(!judgeSqrt(8));



	}


	private boolean judgeSqrt(int target){
		int i = 0, j = (int)Math.sqrt(target);

		while (i<=j){

			int tmp = i*i + j*j;

			if(tmp == target){
				return true;
			} else if(tmp>target){
				j--;
			}else {
				i++;
			}
		}

		return false;
	}


}
