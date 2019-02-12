#include <stdio.h>

int decode2(int x,int y,int z) {
    int temp1;
    int temp2;
    temp1 = y;
    /* movl 12(%ebp), %edx; stores y in a temporary storage edx */
    temp1 -= z;
    /* subl 16(%ebp), %edx; subtracts z from the value stored in edx(temp1) */
    temp2 = temp1;
    /* movl %edx, %eax; stores the value in edx(temp1) to another temporary storage eax(temp2)*/
    temp2 <<= 31;
    /* sall $31, %eax; shifts eax(temp2) by 31 to the left */
    temp2 >>= 31;
    /* sarl $31,%eax; shifts eax(temp2) by 31 to the right */
    temp1 *= x;
    /* imul 8(%ebp),%edx; multiplies edx(temp1) value by x, and stores the result in edx(temp1) */
    temp2 ^= temp1;
    /* xorl %edx,%eax; takes the or of edx(temp1) and eax and stores it in eax(temp2) */
    return temp2;
    /*returns eax(temp2)*/
}

int main() {
    printf("%d\n", decode2(8, 12, 16));
    printf("%d\n", decode2(1, 2, 4));
    printf("%d\n", decode2(-4, -8, -12));
}
