#include <stdio.h>

int lower_one_mask(int n){
    int mask = (2 << (n-1))-1;
    return mask;
}
int main() {
    printf("0x%X\n", lower_one_mask(6));
    printf("0x%X\n", lower_one_mask(17));
    printf("0x%X\n", lower_one_mask(1));
    printf("0x%X\n", lower_one_mask(31));
    printf("0x%X\n", lower_one_mask(32));
}