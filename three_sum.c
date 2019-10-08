/**
 * This program computes the standard 3 sum 
 * problem and returns success or failure
 *
 * Compile: gcc three_sum.c
 * Run: ./a.out
 *
 * main() has a local test client and it 
 * does not accept input args currently
 *
 * Author: Pannagashri Rao
 **/

#include <stdio.h>

/**
 * Returns true(1) if there are 3 elements in the
 * array such that sum of the 3 elements is 0
 * Assume the input array is in sorted order
 **/
int threeSum(int *a, int n)
{
    int i = 0, j = 0, x = 0, k = 0;
    if (n < 3 || !a)
        return -1;
    j = n-1;
    while (i < n && j >= 0) {
        x = a[i] + a[j];
        while (k < n) {
            if (a[k] == -x)
                return 1;
            k++;
        }
        i++; j--;
    }
    return -1;
}

void main()
{
    int arr[5] = {-3, 0, 1, 2, 3};
    printf("\n3 sum returned: %d\n", threeSum(arr, 5));
}

