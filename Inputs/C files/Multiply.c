#include<stdio.h>
int main(void)
{
	int a, b, c, i;
	scanf("%d", &a);
	scanf("%d", &b);
	c=0;
	i=0;
	do {
		c+=a;
		i++;
	}while(i<b);	
		printf("The product of %d and %d is %d", a, b ,c);
	return 0;
}
