#include <stdio.h>
int any_odd_one(unsigned x){
    return(x & 0xAAAAAAAA) != 0;
}
int main() {
    printf("0x%X\n", any_odd_one(0x0) );
    printf("0x%X\n", any_odd_one(0x1) );
    printf("0x%X\n", any_odd_one(0x2) );
    printf("0x%X\n", any_odd_one(0x3) );
    printf("0x%X\n", any_odd_one(0x55555555) );
    printf("0x%X\n", any_odd_one(0xFFFFFFFF) );
}