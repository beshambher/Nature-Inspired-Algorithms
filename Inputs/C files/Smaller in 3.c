#include<stdio.h>
int main(void)
{
	int a, b, c, d;
	scanf("%d",&a);
	scanf("%d %d",&b, &d);
	c=0;  
  	if(a<b)
	{
		if(a<d)
		{
			c=a;
		}
		else
		{
		   c=d;
		}	
	}
	else
	{   
	   if(b<d)
	   {
	      c=b;
	   }
	   else
	   {
		  c=d;
	   }
	}	
	   printf("The smallest number is %d",c);
	return 0;
}
