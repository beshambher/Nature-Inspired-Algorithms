#include<iostream>
using namespace std;
int main()
{
	int a, b, c, d;
	cout<<"Enter the three numbers (between 1-100): ";
	cin>>a>>b>>d;
	c=0;
  	if((a>0)&&(a<101)&&(b>0)&&(b<101)&&(d>0)&&(d<101)) {
		if(a>b) {
			if(a>d) {
			   c=a;
			}
			else {
			   c=d;
			}	
		}
		else {   
		   if(b>d) {
		      c=b;
		   }
		   else {
			  c=d;
		   }
		}	
			cout<<"Greatest number is "<<c<<endl;	
	}
	else {
		cout<<"Inputs out of range."<<endl;
	}
	return 0;
}
