package library;

import java.util.ArrayList;

public class ArrayUtil {
	public int[] addPos(int[] a, int pos, int num) {
		int[] result = new int[a.length];
		for(int i = 0; i < pos; i++)
			result[i] = a[i];
		result[pos] = num;
		for(int i = pos + 1; i < a.length; i++)
			result[i] = a[i - 1];
		return result;
	}
	public void sort(ArrayList<String> x){
		int j;
		boolean flag = true;  // will determine when the sort is finished
		String temp;
		while (flag){
			flag = false;
			for (j = 0; j < x.size() - 1; j++){
				if (x.get(j).compareToIgnoreCase(x.get(j+1)) > 0){
					temp = x.get(j);
					x.set(j, x.get(j+1));
					x.set(j+1, temp); 
					flag = true;
				} 
			} 
		} 
	} 
}
