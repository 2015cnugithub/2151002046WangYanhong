#include <graphics.h>
#include <conio.h>
#include <dos.h>
#include <iostream>
#include<stdio.h>
#include<Windows.h>
using namespace std;

enum TYPE {CIRCLE, RECTANGLE, TRIANGLE};

class FigureLink;				// 超前声明
class Figure
{
	friend class FigureLink;	// FigureLink是连接不同图形绘制类对象的链表类
protected:
	TYPE type;				
	int cx, cy;					// 绘制图形的中心位置坐标
	static Figure* ptr;			// 指向将要插入链表的图形绘制对象
	Figure* next;				// 指向链表中下一个图形绘制对象	
public:
	Figure(int x, int y,TYPE tp)
	{
		cx = x;					
		cy = y;	
		type = tp;		
		next=0;
	}
	virtual void Draw()=0;		// 图形绘制操作的接口规则
	void Hide();
	void Move();
	virtual void Insert()=0;	// 图形绘制类对象插入链表操作的接口规则

};
class myRectangle:public Figure
{
	int width;
	int length;
public:
	myRectangle(int w,int l,int x,int y):Figure(x,y,RECTANGLE)
	{
		width=w;
		length=l;
	}
	void Draw();	
	void Insert();
	
};
class Triangle:public Figure
{
	int rightAngleFlag;
	int bottomSide;
	int height;
public:
	Triangle(int b,int h,int flag,int x,int y):Figure(x,y,TRIANGLE)
	{
		bottomSide=b;
		height=h;
		rightAngleFlag=flag;
	}
	void Draw();	
	void Insert();
};
class Circles:public Figure
{
	int radius;
public:
	Circles(int r,int x,int y):Figure(x,y,CIRCLE)
	{
		radius=r;
	}
	void Draw();	
	void Insert();
};
class FigureLink
{
	Figure* head;
public:
	FigureLink();
	~FigureLink();
	void Empty();
	void Insert(Figure* figureNode);
	void show();
	void hide();
	void move();	
};

class vehicle
{
protected:
	FigureLink contents;
	int wSize;
	int cx;
	int cy;
public:
	void show();
	void hide();
	void move();
};
class car:public vehicle
{	
public:
	car(int wheel_size, int xstart, int ystart);
};

class truck:public vehicle
{	
public:
	truck(int wheel_size,int xstart,int ystart);
};
FigureLink::FigureLink()
{
	head=0;
}

void FigureLink::Empty()
{
	Figure *current;
	Figure *next;

	current=head;
	while(current)
	{
		next=current->next;
		delete current;
		current=next;
	}
}
void FigureLink::Insert(Figure* figureNode)
{
	figureNode->Insert();
	if(head)
	{		
		figureNode->ptr->next=head;
		head=figureNode->ptr;
	}
	else
	{		
		head=figureNode->ptr;
	}
}

void FigureLink::show()
{
	Figure *current;	
	current=head;
	
	while(current)
	{
		current->Draw();
		current=current->next;		
	}
}
void FigureLink::hide()
{
	Figure *current;	
	current=head;
	
	while(current)
	{
		current->Hide();
		current=current->next;		
	}
}

void FigureLink::move()
{
	Figure *current;	
	current=head;
	
	while(current)
	{
		current->Move();
		current=current->next;		
	}
}

FigureLink::~FigureLink()
{
	Empty();
}

void myRectangle::Insert()
{
	Figure::ptr=new myRectangle(width,length,cx,cy);
}

void Triangle::Insert()
{
	Figure::ptr=new Triangle(bottomSide,height,rightAngleFlag,cx,cy);
}

void Circles::Insert()
{
	Figure::ptr=new Circles(radius,cx,cy);
}

void myRectangle::Draw()
{
	rectangle(cx-width/2,cy-length/2,cx+width/2,cy+length/2);
}

void Triangle::Draw()
{
	
	if (rightAngleFlag==1) 
	{
		int points[8]={cx-bottomSide/2,cy+height/2,cx+bottomSide/2,cy+height/2,
				cx+bottomSide/2,cy-height/2,cx-bottomSide/2,cy+height/2};
		drawpoly(4,points);
	}	
	else
	{
		int points[8]={cx-bottomSide/2,cy+height/2,cx+bottomSide/2,cy+height/2,
				cx-bottomSide/2,cy-height/2,cx-bottomSide/2,cy+height/2};
		drawpoly(4,points);
	}

	

}

void Circles::Draw()
{
	circle(cx,cy,radius);
}

void Figure::Hide()
{
    unsigned int sc = getcolor();
    setcolor(getbkcolor());
    Draw();
    setcolor(sc);
}

void Figure::Move()
{
    unsigned int sc=getcolor();	
	setcolor(getbkcolor());
	Draw();
	setcolor(sc);
	cx=cx+1;
	Draw();    
}

car::car(int wheel_size,int xstart,int ystart)
{
	wSize=wheel_size;
	cx=xstart;
	cy=ystart;
	Figure *f;
	f=new Triangle(wSize,wSize,1,cx+1.75*wSize,cy-2.5*wSize);
	contents.Insert(f);
	f=new myRectangle(3.5*wSize,wSize,cx+4*wSize,cy-2.5*wSize);
	contents.Insert(f);
	f=new Triangle(wSize,wSize,2,cx+6.25*wSize,cy-2.5*wSize);
	contents.Insert(f);
	f=new myRectangle(8*wSize,wSize,cx+4*wSize,cy-1.5*wSize);
	contents.Insert(f);
	f=new Circles(0.5*wSize,cx+1.75*wSize,cy-0.5*wSize);
	contents.Insert(f);
	f=new Circles(0.5*wSize,cx+6.25*wSize,cy-0.5*wSize);
	contents.Insert(f);
}

void vehicle::show()
{
	contents.show();
}

void vehicle::hide()
{
	contents.hide();
}

void vehicle::move()
{
	int i=0;
	int choice;
	int seconds;
	seconds=100;

	HANDLE lock;         //########

	while(i<450)
	{
	
		contents.move();


		if(kbhit()) 
		{
			choice=getch();
			if(choice=='+')
				seconds=seconds-10;
			else if(choice=='-')
				seconds=seconds+10;
			else if(choice=='E')
				return;
			else if(choice=='P')
			{
				while(1)
				{
					choice=0;
					if(kbhit()) choice=getch();
					if (choice=='P') break;
				}
			}
		}
		if(seconds<0)  seconds=0;
		Sleep(seconds);
		i++;
	}
}



truck::truck(int wheel_size,int xstart,int ystart)
{
	wSize=wheel_size;
	cx=xstart;
	cy=ystart;
	Figure *f;
	f=new myRectangle(9*wSize,4*wSize,cx+4.5*wSize,cy-3*wSize);
	contents.Insert(f);
	f=new myRectangle(2*wSize,3*wSize,cx+2+10*wSize,cy-2.5*wSize);
	contents.Insert(f);

	f=new Circles(0.5*wSize,cx+wSize,cy-0.5*wSize);
	contents.Insert(f);

	f=new Circles(0.5*wSize,cx+2.25*wSize,cy-0.5*wSize);
	contents.Insert(f);
	
	f=new Circles(0.5*wSize,cx+6.75*wSize,cy-0.5*wSize);
	contents.Insert(f);

	f=new Circles(0.5*wSize,cx+8*wSize,cy-0.5*wSize);
	contents.Insert(f);

	f=new Circles(0.5*wSize,cx+2+10*wSize,cy-0.5*wSize);
	contents.Insert(f);
}

//################################

//##############################

Figure* Figure::ptr=0;
void main()
{

	char *s1 = "1 car   2 truck    3 exit";
	char *s2 = "Press <S> key to start moving";
	char *s3 = "Press <P> key to pause/continue moving";
	char *s4 = "Press <E> key to end moving";
	char *s5 = "Press <+> key to speed up";
	char *s6 = "Press <-> key to speed down";

	int gdriver=DETECT,gmode;
	initgraph(&gdriver,&gmode,"c:\\tc3\\bgi");
	

	vehicle * v1=0,* v2=0;
	car c1(10,30,400);
	truck t1(10,30,400);

	int choice;
	initgraph(640, 480);
	rectangle(20,30,600,460);
	line(20,405,600,405);
	outtextxy(5,5,s1);
	outtextxy(25,40,s2);
	outtextxy(25,60,s3);
	outtextxy(25,80,s4);
	outtextxy(25,100,s5);
	outtextxy(25,120,s6);
	
//	choice=getch();
/*
	cout<<"1 car   2 truck    3 exit\n\n";
	cout<<"     Press <S> key to start moving\n";
	cout<<"     Press <P> key to pause/continue moving\n";
	cout<<"     Press <E> key to end moving\n";
	cout<<"     Press <+> key to speed up\n";
	cout<<"     Press <-> key to speed down";
	cout<<endl;


	//################################

	if(choice == 50)
	{
			if(v1) v1->hide();
			v1=&t1;
			v1->show();
	}
	else 
	{
			if(v1)	v1->hide();
			v1=&c1;
			v1->show();
	}
*/	//###############################




	//########################
	//########################
	
	while(choice!=51)
	{
		if(choice==49)
		{
			if(v1)	v1->hide();
			v1=&c1;
			v1->show();
		}
		else if(choice==50)
		{
			if(v1) v1->hide();
			v1=&t1;
			v1->show();
		}
		else if(choice==83)
		{
			if(v1) v1->move();
		}
		choice=getch();		
	}  

	
/*		if(kbhit()) 
		{
			if(choice=='1')
			{
				if(v1)	v1->hide();
				v1=&c1;
				v1->show();
			}
			else if(choice=='2')
			{
				if(v1) v1->hide();
				v1=&t1;
				v1->show();
			}
			else if(choice=='E')
			{
				if(v1)
				{
					//v1->hide();
					v1->move();

				}
			}
		}
*/
	closegraph();

}
