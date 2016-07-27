#include<iostream>
using namespace std;
int main()
{
	int a, b, c, avg;
	cout<<"Enter marks of 3 subjects (between 0-100)"<<endl;
	cout<<"Enter marks of first subject: ";
	cin>>a;
	cout<<"Enter marks of second subject: ";
	cin>>b;
	cout<<"Enter marks of third subject: ";
	cin>>c;
	if(a>100||a<0||b>100||b<0||c>100||c<0) {
		cout<<"Invalid Marks!"<<endl;
	}
	else {
		avg=(a+b+c)/3;
		if(avg<40) {
			cout<<"Fail."<<endl;
		}
		else if(avg>=40 && avg<50) {
			cout<<"Third Division."<<endl;
		}
		else if(avg>=50 && avg<60) {
			cout<<"Second Division."<<endl;
		}
		else if(avg>=60 && avg<75) {
			cout<<"First Division."<<endl;
		}
		else {
			cout<<"First Division with Distinction."<<endl;
		}
	}
	return 0;
}
