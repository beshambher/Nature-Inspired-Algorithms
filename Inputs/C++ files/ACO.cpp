#include<iostream>
using namespace std;
int main()
{
	int a, b, c, d;
	cin>>a>>b>>c;
  	//while(a<=b)
	//{
		d=(a+b)/2;
		if(d==c)
		{
			cout<<"Mid entered";
		}
		else
		{
			while(a<b)
			{
	  		  if(d<c)
			  {
				cout<<"Less than mid";
			  }
			  else
			  {
				cout<<"More than mid";
			  }
			}
		}
	//}	
	return 0;
}
