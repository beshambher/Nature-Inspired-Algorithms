#include<iostream>
using namespace std;
int main()
{
	int a, b, c;
	cout<<"Enter the two numbers (between 1-100): ";
	cin>>a>>b;
	if((a>0)&&(a<101)&&(b>0)&&(b<101)) {
		cout<<"The product of "<<a<<" and "<<b<<" is ";
		c=0;	
		do {
			c+=a;
			b--;
		}while(b>0);	
		cout<<c<<endl;	
    }
    else {
    	cout<<" Inputs out of range."<<endl;
	}
	return 0;
}
