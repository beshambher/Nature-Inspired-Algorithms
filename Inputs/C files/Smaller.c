#include<stdio.h>
int main(void)
{
	int a, b, c;
	scanf("%d %d",&a, &b);
	c=0;	
	if(a<b)
	{
		c=a;
	}
	else
	{   
	   c=b;
	}	
	printf("\nSmaller in %d and %d is %d",a,b,c);	
	return 0;
}
