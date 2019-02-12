//Parsa Bagheri; CIS 314; Assignment_2 2-72;

// A: When either of operand of a comparison is unsigned , the other operand
// is implicitly cast to unsigned, unsigned number is always greater or
// equal zero.
// B:

#include <stdio.h>
#include <string.h>

#define LENGTH 6
int x;
char y[LENGTH];

void copy_int(int val, void *buf, int maxbytes) {
    if (maxbytes > 0 && maxbytes >= sizeof(val))
        memcpy(buf, (void *) &val, sizeof(val));
}

int main(void)
{
    int i,j;
    /* set x to obvious byte pattern */
    x = 0x01234567;
    for (j = 0; j < LENGTH; j++)
    {
        /* reset all of y before test */
        for (i = 0; i < LENGTH; i++)
            y[i] = 0x00;
        /* do test: call copy_int() */
        copy_int(x, &y[j], LENGTH-j);
        /* output all of y to see what happened */
        printf("iteration %d\ny: ",j);
        for (i = 0; i < LENGTH; i++)
            printf("%.2x ", (int) y[i]);
        printf("\n\n");
    }
}