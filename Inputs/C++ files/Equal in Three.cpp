#include<iostream>
using namespace std;
int main()
{
	int a, b, d;
	cout<<"Enter the three numbers (between 1-100): ";
	cin>>a>>b>>d;
  	if((a>0)&&(a<101)&&(b>0)&&(b<101)&&(d>0)&&(d<101)) {
	  	if(a==b) {
			if(a==d) {
				cout<<a<<" "<<b<<" "<<d<<" are equal"<<endl;
			}
			else {
			   cout<<a<<" "<<b<<" are equal"<<endl;
			}	
		}
		else {   
		   if(b==d) {
		      cout<<b<<" "<<d<<" are equal"<<endl;
		   }
		   else {
			  if(a==d) {
			  	cout<<a<<" "<<d<<" are equal"<<endl;
			  }
			  else {
			  	cout<<a<<" "<<b<<" "<<d<<" are not equal"<<endl;
			  }
		   }
		}
	}
	else {
		cout<<"Inputs out of range."<<endl;
	}	
	return 0;
}
