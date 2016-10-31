package soc;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TSP {

	private static Scanner in;

	public static void main(String[] args) {

		in = new Scanner(System.in);

		// Get count of shops
		System.out.println("Enter number of type of shops = (say 4) ");
		int n = in.nextInt();

		// Save a list of all shop names
		List<String> shopCategory = new LinkedList<String>();
		System.out.println("Enter shops categories = (say Groceries, Furniture, Electronics, Automobile)");
		for (int i = 0; i < n; i++) {
			String name = in.next();
			shopCategory.add(name);
		}

		List<LinkedList<String>> shopList = new LinkedList<LinkedList<String>>();
		List<LinkedList<Integer>> shopListDistHome = new LinkedList<LinkedList<Integer>>();
		LinkedList<String> indShopsClosestHome = new LinkedList<String>();

		System.out.println("Enter count of individiual shops for the following categories: ");
		for (String category : shopCategory) {
			System.out.println("count of shops for " + category + " = ");
			int m = in.nextInt();

			int minDist = Integer.MAX_VALUE;

			LinkedList<String> individualShops = new LinkedList<String>();
			LinkedList<Integer> individualShopsDist = new LinkedList<Integer>();

			System.out.println("Enter " + m + " shops for " + category);

			String closestHomeShop = "";
			int j = 0;
			while (j < m) {
				System.out.println("Enter shop " + (j + 1));
				String shop = in.next();
				individualShops.add(shop);

				System.out.println("Enter its distance from Home");
				int distH = in.nextInt();
				individualShopsDist.add(distH);

				if (distH < minDist) {
					minDist = distH;
					closestHomeShop = shop;
				}

				j++;
			}

			indShopsClosestHome.add(closestHomeShop);

			shopList.add(individualShops);
			shopListDistHome.add(individualShopsDist);
		}

		System.out.println(shopList);
		System.out.println(shopListDistHome);
		System.out.println(indShopsClosestHome);

		// Form a matrix
		int matSize = indShopsClosestHome.size() + 1;
		int mat[][] = new int[matSize][matSize];

		// initialize diagonal (same indices) elements to 0
		for (int i = 0; i < matSize; i++) {
			mat[i][i] = 0;
		}

		// [NOTE] : we are not randomly selecting values from home to all
		// selected stores.
		// fill rest of the matrix
		for (int i = 0; i < matSize; i++) {
			for (int j = i + 1; j < matSize; j++) {
				int rand = generateRandom(1, 10);
				mat[i][j] = mat[j][i] = rand;
			}
		}

		// get max sum row
		int maxRow = -1;
		int rowIndex = 0;
		for (int i = 0; i < matSize; i++) {
			int sumRow = 0;
			for (int j = 0; j < matSize; j++) {
				sumRow += mat[i][j];
			}

			if (sumRow > maxRow) {
				maxRow = sumRow;
				rowIndex = i;
			}
		}

		// printDistMatrix
		printDistMatrix(mat);

		// save mat[rowIndex] to a new array and get it's indices.
		int arr[] = new int[mat[rowIndex].length - 1]; // ignore first column
														// value for Home
		for (int i = 1; i < mat[rowIndex].length; i++) {
			arr[i - 1] = mat[rowIndex][i];
		}

		// set default indices
		int newIndices[] = new int[arr.length];
		for (int i = 0; i < newIndices.length; i++) {
			newIndices[i] = i;
		}

		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {

				if (arr[i] > arr[j]) {
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;

					// swap indices
					temp = newIndices[i];
					newIndices[i] = newIndices[j];
					newIndices[j] = temp;
				}
			}
		}

		// iterate newIndices and
		StringBuilder sb = new StringBuilder("H->");
		for (int s : newIndices) {
			String shopName = indShopsClosestHome.get(s);
			sb.append(shopName + "->");
		}
		sb.append("H");

		System.out.println("TSP path : " + sb.toString());

	}

	private static int generateRandom(int min, int max) {
		int range = Math.abs(max - min) + 1;
		int x = (int) (Math.random() * range) + min;

		return x;
	}

	private static void printDistMatrix(int mat[][]) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
	}
}
