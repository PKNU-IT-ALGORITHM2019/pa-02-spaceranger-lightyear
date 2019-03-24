import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class assign02 {
	public static int n;
	public static Scanner inFile;
	public static offset [] set = new offset[50];
	public static int [] set_counter = new int[50];	
	public static int dist = 2147483647;

	public static void main(String[] args) {
		readFile();

		long start = System.currentTimeMillis();
		TSP(1, 0);
		long end = System.currentTimeMillis();
		
		System.out.println("answer : " + dist);
		System.out.print("[");
		
		for(int i = 0; i < n; i++) {
			if(i == n-1) {
				System.out.print(set_counter[i]);
				break;
			}
			System.out.print(set_counter[i] + ", ");
		}		
		
		System.out.println("]");
		System.out.println("\nRunning Time : " + (end - start)/1000.00 + "sec");		// 걸린 시간 체크용
	}

	public static void readFile() {
		int i = 0;

		try {
			inFile = new Scanner(new File("data07.txt"));
			n = inFile.nextInt();

			while(inFile.hasNext() && i<n) {
				set[i] = new offset(inFile.nextInt(), inFile.nextInt(), i);
				i++;
			}
			inFile.close();
			System.out.println("Read Successfully.");

		} catch (FileNotFoundException e) {
			System.out.println("No Such File.");
			System.exit(0);
		}
	}

	public static double calc(int k, int i) {
		return Math.sqrt(Math.pow(set[k].x-set[i].x, 2) + Math.pow(set[k].y-set[i].y, 2));		 
	}

	public static void TSP(int k, double tmp_dist) {
		if (dist < tmp_dist)
			return;
		
		if (k == n) {
			tmp_dist = tmp_dist + calc(k-1, 0);
			if(dist > tmp_dist) {
				dist = (int) tmp_dist;
				for(int i = 0; i < n; i++)
					set_counter[i] = set[i].cnt;
			}				
		}
		else {
			for(int i = k; i < n; i++) {
				swap(set, k, i);
				TSP(k+1, tmp_dist + calc(k-1,k));
				swap(set, k, i);
			}
		}
	}

	public static void swap(offset[] set, int k, int i) {
		offset tmp = set[k];
		set[k]  = set[i];
		set[i] = tmp;
	}
}