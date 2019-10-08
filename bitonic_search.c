/*****************************************************************
 * Search in a bitonic array. An array is bitonic if 
 * it is comprised of an increasing sequence of integers 
 * followed immediately by a decreasing sequence of integers. 
 * Given a bitonic array of n distinct integer values, this prog
 * determines whether a given integer is in the array.
 *
 * Compile: gcc bitonic_search.c
 * Run: ./a.out
 *
 * Currently, main() has a local test client and not input args
 *
 * Author: Pannagashri rao
 *
 ******************************************************************/

#include <stdio.h>

int binarySearch(int *a, int low, int high, int key)
{
    int mid = 0;
    if (low < high) {
        mid = (low + high) / 2;
        if (key == a[mid])
            return mid;
        // We know for sure key will not be in the left half
        if (key < a[0])
            return binarySearch(a, mid+1, high, key);
        return binarySearch(a, low, mid-1, key);
    }
}

/* Returns the index at which key is found */
int bitonicSearch(int *arr, int n, int key)
{
    if (n < 1 || !arr)
        return -1;
    return binarySearch(arr, 0, n-1, key);
}

void main()
{
    int arr[8] = {7, 8, 9, 10, 11, 3, 2, 1};
    printf("Bitonic search returns %d\n", bitonicSearch(arr, 8, 1)+1);
}
