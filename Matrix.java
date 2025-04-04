package org.bharadwajraju.Matrix;

import java.io.*;

class Matrix {

	public double [][] matrix;
	public int row, col;

	public Matrix(){
	matrix=null;
	row=0;
	col=0;
	}

	public Matrix(int i,int j){

		row=i;
		col=j;
		matrix = new double[row][col];
		for(int c1=0;c1<row;c1++)
		for(int c2=0;c2<col;c2++)
			matrix[c1][c2]=0;

	}

	public Matrix(int i,int j,double[][] a){

		row=i;
		col=j;
		matrix = new double[row][col];
		for(int c1=0;c1<row;c1++)
		for(int c2=0;c2<col;c2++)
			matrix[c1][c2]=a[c1][c2];

	}


	public Matrix(Matrix a){

	row=a.noOfRows();
	col=a.noOfCols();
	matrix = new double[row][col];
		for(int c1=0;c1<row;c1++)
		for(int c2=0;c2<col;c2++)
			matrix[c1][c2]=a.getElement(c1,c2);

	}

	public void read() {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(row+"   "+col);

		try
		{

			System.out.println("Enter number of rows\n");

			String str = br.readLine();
			while(str.length()<=0)
				str=br.readLine();

			row = Integer.parseInt(str);
			System.out.println("Enter number of columns\n");
			col = Integer.parseInt(br.readLine());

		}
		catch(NumberFormatException e)
		{
		System.out.println("Exception raised while converting input data to integer " + e);
		}
		catch(IOException e)
		{
		System.out.println("Exception raised while converting input data to integer " + e);
		}


		System.out.println(row+"   "+col);

		matrix = new double[row][col];
		for(int i = 0; i < row ; i++)
		for(int j = 0; j < col ; j++)
		{
			try
			{
				matrix[i][j] = Double.parseDouble(br.readLine());
				System.out.println(matrix[i][j]);
			}
			catch(NumberFormatException e)
			{
				System.out.println("Exception raised while converting input data to integer " + e);
				System.exit(0);
			}
			catch(IOException e)
			{
			System.out.println("Exception raised while converting input data to integer " + e);
			}

		}

	}

	public double getElement(int i,int j){

		return matrix[i][j];
	}

	public void setElement(int i,int j,double value){

		matrix[i][j]=value;
	}

	public int noOfRows(){

		return row;
	}

	public int noOfCols(){

		return col;
	}

	public Matrix add(Matrix a)  {

		Matrix m = new Matrix(row,col);
		
		if (a==null) {
			System.out.println("NULL matrix passed");
			return null;
		}
						
		if( a.noOfRows() != row || a.noOfCols() != col )
		{
			System.out.println("Addition is not possible here.");
			return null;
		}

		for(int i = 0; i < a.noOfRows(); i++)   
		{
			for( int j = 0; j < a.noOfCols() ;j++)
			{
			m.setElement(i,j,matrix[i][j]+a.getElement(i,j));
			}

		}
		return m;

	}

	public Matrix sub(Matrix a) {

		if (a==null) {
			System.out.println("NULL matrix passed");
			return null;
		}
		
		Matrix m = new Matrix(row,col);
		if( a.noOfRows() != row || a.noOfCols() != col )
		{
			System.out.println("Subtraction is not possible here.");
			return null;
		}

		for(int i = 0; i < a.noOfRows(); i++)   
		{
			for( int j = 0; j < a.noOfCols() ;j++)
			{
			m.setElement(i,j,matrix[i][j]-a.getElement(i,j));
			}

		}
		return m;
	}

	public Matrix mul(Matrix b){

	Matrix m=null;

		if( col != b.noOfRows())
		{
			System.out.println("Multiplication is not possible here.");
			return m;
		}

		m=new Matrix(row,b.noOfCols());

		for(int i = 0; i < row; i++)   
		{
			for( int j = 0; j < b.noOfCols();j++)
			{
			double sum=0;
			for( int k = 0; k < col;k++)
			sum+=(matrix[i][k]*b.getElement(k,j));			
			m.setElement(i,j,sum);
			}
		}

	return m;
	}

	public Matrix transpose() {
	
		int i, j;

		Matrix m = new Matrix(col,row);
			for( i = 0; i < row; i++)
			{
				for( j = 0; j < col; j++)
					m.setElement(j,i,matrix[i][j]);
			}
	return m;
		
	}

	public Matrix minor(int a, int b) {

		if(a >row || b >=col)
		{
			return null;
		}
		else
		{
		Matrix result = new Matrix(row-1,col-1);

			for(int i=0;i < row;i++){
			
			if(a==i) continue;

				for(int j=0;j<col;j++){
					
					if(b == j) continue;

					if(i<a && j<b)
						result.setElement(i,j,matrix[i][j]);
					else
					if(i<a && j>b)
						result.setElement(i,j-1,matrix[i][j]);
					else
					if(i>a && j<b)
						result.setElement(i-1,j,matrix[i][j]);
					else
					if(i>a && j>b)
						result.setElement(i-1,j-1,matrix[i][j]);
					
				}
			}
			return result;
		}
	}
	
	public double determinant() {
	
	if(row!=col)
	{
		System.out.println("Not a square matrix determinant does not exist");
		return -1;
	}
	else
	{

		if(row==1) return matrix[0][0];
		if(row==2)
		{
		return matrix[0][0]*matrix[1][1]-matrix[0][1]*matrix[1][0];
		}
		else{
			double det = 0;
			for(int j=0;j<row;j++)
			{
			det+= (Math.pow(-1,2+j)*matrix[0][j]*(minor(0,j).determinant()));
			}
		return det;
		}
	}
	
	}
	
	public Matrix inverse() throws Exception	{
	
	if (determinant()==0) throw (new Exception());
	if(row!=col || row ==1 || determinant()==0)
	{
		System.out.println("Inverse does not exist");
		return null;
	}
	else
	{
	 double det = determinant();
 	 Matrix res = new Matrix(row, col);
	 Matrix minor;

		for(int i=0;i<row;i++){
		for(int j=0;j<col;j++){
			minor=minor(i,j);
			double val = minor.determinant()*Math.pow(-1,2+i+j)/det ;
			res.setElement(i,j,val);
		}
		}
	return res.transpose();
	}

	}

	public void print() {

		int i, j;
		System.out.println("\n");
		System.out.println("------ Matrix["+row+","+col+"]"+" -------");

		for( i = 0; i < row; i++)
		{
			for( j = 0; j < col ; j++)
				System.out.print("   "+ matrix[i][j]);
			System.out.println();
		}
	}

	public boolean equals(Matrix m)
	{
		if(row!=m.noOfRows() || col!=m.noOfCols())return false;

		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				if(matrix[i][j]!=m.getElement(i,j)) return false;
			}

		}
	return true;
	}
}

class Main
{
	public static void main(String[] args) { 
  
		Matrix a ,b ,c;
		char choice = 'h';

		a=new Matrix(1,1);
		b=new Matrix(1,1);
		c=new Matrix(1,1);

		while(true)
		{
			switch(choice) 
			{
			case 'h':
				System.out.println("1  - To Read first matrix");	
				System.out.println("2  - To read second matrix");	
				System.out.println("m  - To multiply the matrices");	
				System.out.println("a  - To add the two matrices");	
				System.out.println("s  - To subtract the two matrices");
				System.out.println("t  - To transpose the first matrix");	
				System.out.println("p  - To print the matrices");
				System.out.println("i  - To inverse first matrix");
				System.out.println("d  - To print determinant of first ");
				System.out.println("e  - To exit");
				System.out.println("h  - To print help information");
				break;
			case '1':
				a.read();
				a.print();
				break;
			case '2':
				b.read();
				b.print();
				break;
			case 'm':
				c=a.mul(b);
				c.print();
				break;
			case 'a':
				//c=a.add(b);
				c.print();
				break;
			case 's':
				c=a.sub(b);
				a.print();
				break;
			case 'd':
				a.read();
				System.out.println("Determinant of it is :"+a.determinant());
				break;

			case 't':
				a.read();
				a=a.transpose();
				a.print();
				break;
			case 'p':
				a.print();
				break;
			case 'i':
				a.read();
				//a=a.inverse();
				a.print();
				break;
			case 'e':
				System.out.println("Exiting...");
				System.exit(0);
			default:
			}
			try
			{			
				choice = (char)System.in.read();
			}
			catch(IOException e)
			{
		System.out.println("Exception raised while reading input from console" + e);
			e.printStackTrace();
			}
		}
	}
}
