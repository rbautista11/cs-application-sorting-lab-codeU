/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        	List<T> sorted_list = new ArrayList<T>(list);
		List<T> helper = new ArrayList<T>(list.size()); 
		mergeSort(sorted_list, helper, 0, list.size() - 1, comparator);
		return sorted_list;
	}
	
	public void mergeSort(List<T> sorted_list, List<T> helper, int low, int high, Comparator<T> comparator){
		if(low < high){
			int middle = (low + high) / 2;
			mergeSort(sorted_list, helper, low, middle, comparator);
			mergeSort(sorted_list, helper, middle + 1, high, comparator);
			merge(sorted_list, helper, low, middle, high, comparator);
		}
	}
	
	  public void merge(List<T> sorted_list, List<T> helper, int low, int middle, int high, Comparator<T> comparator){
		for(int i = low; i <= high; i++){
			helper.add(i, sorted_list.get(i));
		}
		
		int helperLeft = low;
		int helperRight = middle + 1;
		int current = low;

		while(helperLeft <= middle && helperRight <= high){
			T left_element = helper.get(helperLeft);
			T right_element = helper.get(helperRight);
			if(comparator.compare(left_element, right_element) < 0){
				sorted_list.set(current, helper.get(helperLeft));
				helperLeft++;
			}
			else{
				sorted_list.set(current, helper.get(helperRight));
				helperRight++;
			}
			current++;

			int remaining = middle - helperLeft;
			for(int i = 0; i <= remaining; i++){
				sorted_list.set(current + i, helper.get(helperLeft + i));
			}
		}
	
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
		heap.addAll(list);
		list.clear();
		while(!heap.isEmpty())
			list.add(heap.poll());
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
       		PriorityQueue<T> heap = new PriorityQueue<T>(k, comparator);
		for(int i = 0; i < list.size(); i++){
			if(i < k){
				heap.add(list.get(i));
			}
			else if(comparator.compare(list.get(i), heap.peek()) < 0){
			
			}
			else{
				heap.poll();
				heap.add(list.get(i));
			}
		}
		List<T> k_list = new ArrayList<T>(k);
		while(!heap.isEmpty()){
			k_list.add(heap.poll());
		}
		return k_list;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
