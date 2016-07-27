#include<stdio.h>
int main(void)
{
	int a, b, d;
	scanf("%d %d %d",&a,&b,&d);
	if(a==b) {
		if(a==d)
		{
			printf("%d, %d, and %d are equal",a,b,d);
		} else {
			printf("%d and %d are equal",a,b);
		}	
	} else {   
	   if(b==d) {
			printf("%d and %d are equal",b,d);
	   } else if(a==d) {
			printf("%d and %d are equal",a,d);
		  }
		  else {
			printf("%d, %d, and %d are not equal",a,b,d);
		  }
	}	
	return 0;
}
