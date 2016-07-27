#include<iostream>
using namespace std;
int main()
{
	int a, b, c;
	cout<<"Enter the two numbers (between 1-100): ";
	cin>>a>>b;
	c=0;
	if((a>0)&&(a<101)&&(b>0)&&(b<101)) {
		if(a<b) {
			c=a;
		}
		else {   
		   c=b;
		}	
			cout<<"\nSmaller in "<<a<<" and "<<b<<" is "<<c<<endl;
	}
	else {
		cout<<"Inputs out of range."<<endl;
	}
	return 0;
}
